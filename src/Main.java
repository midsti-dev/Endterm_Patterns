import java.util.*;

public class Main {
    static ClearConsole console = new ClearConsole();
    static Scanner sc = new Scanner(System.in);
    static Menu menu = new Menu(Arrays.asList  (new MargheritaPizza(),
                                                new PepperoniPizza(),
                                                new FourCheesePizza(),
                                                new FourSeasonsPizza(),
                                                new CaesarPizza()));  
    static MenuDopIng podMenu = new MenuDopIng(Arrays.asList("Грибы", "Халапеньо", "Пепперони"));
    static Order order;
    public static void main(String[] args) throws AdminAuthenticationException, InterruptedException {
        console.clear();
        System.out.print("1:Клиент \n2:Админ \nВаш выбор: ");
        switch (sc.nextInt()) {
            case 1:
                console.clear();
                System.out.println("Добро пожаловать в Pizzeria(BDB)!");
                Thread.sleep(2000);
                CustomerMenu();
                break;
            case 2:
                console.clear();
                System.out.println("");
                AdminMenu();
                break;
        }
        
    }


    /**
     *              Displays the customer menu and handles customer actions such as viewing the menu,
     *                               ordering pizzas, and managing their current order.
     */
    static void CustomerMenu(){
        console.clear();
        while(true){
            System.out.println("1)Показать меню\n2)Заказать пиццу\n3)Показать заказ\n4)Удалить из заказа\n5)Завершить заказ\n6)Подвердить и выйти");
            switch (sc.nextInt()) {
                case 1:
                    ShowMenuPizza();
                    break;
                case 2:
                    OrderPizza();
                    break;
                case 3:
                    ShowOrder();
                    break;
                case 4:
                    DeleteFromOrder();
                    break;
                case 5:
                    FinishOrder();
                    break;
                case 6:
                    console.clear();
                    System.out.println("Спасибо за визит! До свидания!");
                    return;
                default:
                    System.out.println("Неверный выбор! Попробуйте снова.");
            }
        }
    }
    /**
     *                  Handles admin functionalities by authenticating and allowing management of the menu.
     */
    static void AdminMenu() throws AdminAuthenticationException, InterruptedException{
        console.clear();
        Admin admin = Admin.getInstance();
        Admin.adminMenu(menu, podMenu);
    }
    /**
     *                                  Displays the list of pizzas in the menu.
     */
    static void ShowMenuPizza(){
        console.clear();
        MenuIterator iterator = new PizzaMenuIterator(menu);
        System.out.println("Пиццы :");
        int i = 1;
        while(iterator.hasNext()){
            System.out.println(i++ + "." + iterator.current().getName());
            iterator.next();
        }
        return;
    }
    /**
     *                      Displays the list of additional ingredients.
     */
    static void ShowDopIngMenu(){
        int i = 1;
        for(String doping : podMenu.getMenu()){
            System.out.println(i++ + "." + doping);
        }
    }
    /**
     *                 Allows the customer to order a pizza, with the option to add additional ingredients.
     */
    static void OrderPizza() {
        console.clear();
        ShowMenuPizza();
        System.out.println("Введите номер пиццы, которую хотите заказать: ");
        int selectPiz = sc.nextInt();
        console.clear();
    
        if (selectPiz <= 0 || selectPiz > menu.getSize()) {
            throw new IllegalArgumentException("Неправильный выбор номера пиццы!");
        }
    
        System.out.println("Вы выбрали: " + menu.getMenu().get(selectPiz - 1).getName());
        System.out.println("Хотите добавить дополнительные ингредиенты?\n1) Да\n2) Нет");
        int addIngChoice = sc.nextInt();
        console.clear();
    
        Pizza orderedPizza;
    
        if (addIngChoice == 1) {
            System.out.println("Выберите дополнительный ингредиент из списка:");
            ShowDopIngMenu();
            System.out.println((podMenu.getSize() + 1) + ".Не надо");
            System.out.println("Введите номер ингредиента: ");
            int dopIngIndex = sc.nextInt();
            console.clear();
    
            if (dopIngIndex > 0 && dopIngIndex <= podMenu.getSize()) {
                String additionalIngredient = podMenu.getMenu().get(dopIngIndex - 1);
                orderedPizza = PizzaFactory.createPizzaWithDopIng(
                        menu.getMenu().get(selectPiz - 1).getName(), additionalIngredient);
            } else if (dopIngIndex == podMenu.getSize() + 1) {
                orderedPizza = menu.getMenu().get(selectPiz - 1);
            } else {
                throw new IllegalArgumentException("Неправильный выбор ингредиента!");
            }
        } else if (addIngChoice == 2) {
            orderedPizza = menu.getMenu().get(selectPiz - 1);
        } else {
            throw new IllegalArgumentException("Неправильный выбор!");
        }
    
        if (order == null) {
            order = new OrderBuilder().addPizza(orderedPizza).build();
        } else {
            order.addPizza(orderedPizza);
        }
        console.clear();
        System.out.println("Пицца добавлена в заказ!");
    }
    /**
     *                                      Displays the current order.
     */
    static void ShowOrder(){
        console.clear();
        if(order == null){
            System.out.println("Ваш заказ пуст!");
            return;
        }
        else{
            order.showOrder();
        }
    }


    /**
     *                  Finalizes the order, allowing the customer to choose between pickup or delivery.
     */
    static void FinishOrder(){
        console.clear();
        int select;
        System.out.print("Выбор получения:\n1.Самовывоз\n2.Доставка\nВыбор:");
        select = sc.nextInt();
        if(select == 1){
            String delivery;
            delivery = "Самовывоз: ";
            System.out.print("Выберите адреса наших пиццерий:\n1.Проспет Достык\n2.Проспет Абылайхана\nВыбор:");
            select = sc.nextInt();
            if(select == 1){delivery += "Проспект Достык";}
            else if(select == 2){delivery += "Проспект Абылайхана";}
            else{throw new IllegalArgumentException("Неправильный ввод выбора!");}
            order = new OrderDecorator(order)
                        .setDeliveryType(delivery).decorate();
        }
        else if(select == 2){
            String delivery;
            delivery = "Доставка: ";
            System.out.print("Адрес доставки: ");
            delivery += sc.nextLine();
            order = new OrderDecorator(order)
                        .setDeliveryType(delivery).decorate();
        }
        else{
            throw new IllegalArgumentException("Неправильный ввод выбора!");
        }
        console.clear();
        ShowOrder();
    }
    /**
     *                              Removes a selected pizza from the current order.
     */
    static void DeleteFromOrder() {
        try {
            console.clear();
            
            if (order == null || order.getPizzas().isEmpty()) {
                System.out.println("Ваш заказ пуст! Нечего удалять.");
                return;
            }
    
            System.out.println("Ваш текущий заказ:");
            List<Pizza> pizzas = order.getPizzas();
            for (int i = 0; i < pizzas.size(); i++) {
                System.out.println((i + 1) + ") " + pizzas.get(i).toString());
            }
    
            System.out.println("Введите номер пиццы, которую хотите удалить (или 0 для выхода):");
            int choice = sc.nextInt();
    
            if (choice == 0) {
                return;
            }
    
            if (choice > 0 && choice <= pizzas.size()) {
                pizzas.remove(choice - 1);
                console.clear();
                System.out.println("Пицца успешно удалена из заказа!");
            } else {
                System.out.println("Неверный выбор! Попробуйте снова.");
            }
    
        } catch (InputMismatchException e) {
            System.out.println("Ошибка ввода! Введите числовое значение.");
            sc.nextLine(); // Очищаем буфер ввода
        }
    }
    

}
