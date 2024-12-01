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
        System.out.print("1:Customer\n2:Admin\nWhat log in type: ");
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
    static void CustomerMenu(){
        console.clear();
        while(true){
            System.out.println("1)Показать меню пицц\n2)Заказать пиццу\n3)Показать заказ\n4)Удалить из заказа\n5)Завершить заказ");
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
                    //DeleteFromOrder()
                    break;
                case 5:
                    FinishOrder();
                    break;
            }
        }
    }
    
    static void AdminMenu() throws AdminAuthenticationException, InterruptedException{
        console.clear();
        Admin admin = Admin.getInstance();
        Admin.adminMenu(menu, podMenu);
    }

    static void ShowMenuPizza(){
        console.clear();
        MenuIterator iterator = new PizzaMenuIterator(menu);
        System.out.println("Pizzas:");
        int i = 1;
        while(iterator.hasNext()){
            System.out.println(i++ + "." + iterator.current().getName());
            iterator.next();
        }
        return;
    }
    static void ShowDopIngMenu(){
        int i = 1;
        for(String doping : podMenu.getMenu()){
            System.out.println(i++ + "." + doping);
        }
        System.out.println(i + ".Не надо");
    }
    static void OrderPizza(){
        console.clear();
        ShowMenuPizza();
        System.out.println("Какую пиццу заказать: ");
        int selectPiz = sc.nextInt();
        ShowDopIngMenu();
        System.out.println("Добавить доп. ингридиент:");
        int selectDop = sc.nextInt();
        if(selectPiz > 0 && selectPiz <= menu.getSize()){
            if(selectDop > 0 && selectDop < podMenu.getSize()){
                order = new OrderBuilder()  .addPizza(
                                            PizzaFactory.createPizzaWithDopIng
                                            (menu.getMenu().get(selectPiz).getName(),
                                             podMenu.getMenu().get(selectDop)))
                                            .build();
            }
            else{
                throw new IllegalArgumentException("Не правильный выбор!");
            }
        }
        else{
            order = new OrderBuilder()
                        .addPizza(menu.getMenu().get(selectPiz))
                        .build();
        }
        console.clear();
        return;
    }
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

}
