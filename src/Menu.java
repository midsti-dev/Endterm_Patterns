import java.util.*;

public class Menu {
    private List<Pizza> pizzas;

    public Menu(List<Pizza> newItems) {
        this.pizzas = newItems;
    }

    int getSize(){return this.pizzas.size();}
    List<Pizza> getMenu(){return this.pizzas;}
    
}


class MenuDopIng{
    private List<String> DopIngMenu;

    public MenuDopIng(List<String> newItems){
        this.DopIngMenu = newItems;
    }

    public List<String> getMenu(){return this.DopIngMenu;}
    public int getSize(){return this.DopIngMenu.size();}
}


class PizzaMenuIterator implements MenuIterator{
    private Menu menu;
    private int curIndex;

    PizzaMenuIterator(Menu newMenu){
        this.menu = newMenu;
        this.curIndex = 0;
    }

    @Override
    public boolean hasNext() {
        if(this.curIndex < menu.getSize()){
            return true;
        }
        else{
            return false;
        }
    }
    @Override
    public Pizza next() {
        if(hasNext()){
            Pizza pizza = this.menu.getMenu().get(curIndex);
            this.curIndex+=1;
            return pizza;
        }
        else{
            return null;
        }
    }

    @Override
    public Pizza current() {
        return this.menu.getMenu().get(curIndex);
    }

}


interface MenuIterator {
    boolean hasNext();
    Pizza next();
    Pizza current();
}
