import java.util.Scanner;

class Admin {
    static Scanner sc = new Scanner(System.in);
    private static Admin instance;
    private static final String log = "admin", pass = "admin";

    private static void authenticate() throws AdminAuthenticationException {

        System.out.print("Введите логин: ");
        String login = sc.nextLine();
        System.out.print("Введите пароль: ");
        String password = sc.nextLine();
        if (!log.equals(login) || !pass.equals(password)) {
            throw new AdminAuthenticationException("Неверный логин или пароль!");
        }
    }
    public static Admin getInstance() throws AdminAuthenticationException {
        if (instance == null) {
            authenticate();
            instance = new Admin();
        }
        return instance;
    }
    public static void adminMenu(Menu menu, MenuDopIng podMenu) throws InterruptedException{
        Main.console.clear();
        boolean isExit = false;
        System.out.print("1.Добавить пиццу\n2.Удалить пиццу\n3.Добавить доп. ингридиент\n4.Удалить доп. ингридиент\n5.Выйти\nВыбор:");
        while (!isExit) {
            switch (sc.nextInt()) {
                case 1:
                    addPizzaToMenu(menu);
                    break;
                case 2:
                    deletePizzaFromMenu(menu);
                    break;
                case 3:
                    addDopIngToMenu(podMenu);
                    break;
                case 4:
                    deleteDopIngToMenu(podMenu);
                    break;
                default:
                    Main.console.clear();
                    System.out.println("Выход из меню админа...");
                    Thread.sleep(1500);
                    isExit = true;
                    break;
            }
        }
    }
    public static void addPizzaToMenu(Menu menu){
        Main.console.clear();
        System.out.println("Имя пиццы: ");
        String PizzaName = sc.nextLine();
        menu.getMenu().add(PizzaFactory.PizzaForAdmin(PizzaName));
    }
    public static void deletePizzaFromMenu(Menu menu){
        Main.console.clear();
        Main.ShowMenuPizza();
        for(int i = 0; i < menu.getMenu().size(); i++){
            System.out.println(i+1 + ". " + menu.getMenu().get(i));
        }
        System.out.println("Выбор:");
        menu.getMenu().remove(sc.nextInt()-1);
        return;
    }
    public static void addDopIngToMenu(MenuDopIng menu){
        Main.console.clear();
        System.out.println("Имя ингридиента: ");
        String DopName = sc.nextLine();
        menu.getMenu().add(DopName);
    }
    public static void deleteDopIngToMenu(MenuDopIng menu){
        Main.console.clear();
        Main.ShowDopIngMenu();
        for(int i = 0; i < menu.getMenu().size(); i++){
            System.out.println(i+1 + ". " + menu.getMenu().get(i));
        }
        System.out.println("Выбор:");
        menu.getMenu().remove(sc.nextInt()-1);
        return;
    }
}

class AdminAuthenticationException extends Exception{
    public AdminAuthenticationException(String mes){
        super(mes);
    }
}
