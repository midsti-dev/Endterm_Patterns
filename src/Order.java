import java.util.ArrayList;
import java.util.List;

class Order {
    private List<Pizza> pizzas = new ArrayList<>();
    private String deliveryType = "";

    public void addPizza(Pizza pizza) {
        pizzas.add(pizza);
    }

    public void setDeliveryType(String deliveryType) {
        this.deliveryType = deliveryType;
    }

    public void showOrder() {
        System.out.println("Ваш заказ:");
        for (Pizza pizza : pizzas) {
            pizza.toString();
        }
        System.out.println("Тип доставки:" + deliveryType);
    }
}

class OrderBuilder {
    private Order order;

    public OrderBuilder() {
        order = new Order();
    }
    public OrderBuilder addPizza(Pizza pizza) {
        order.addPizza(pizza);
        return this;
    }

    public Order build() {
        return this.order;
    }
}

class OrderDecorator{
    private Order order;

    public OrderDecorator(Order or){
        this.order = or;
    }
    public OrderDecorator setDeliveryType(String deliveryType) {
        order.setDeliveryType(deliveryType);
        return this;
    }
    public Order decorate(){
        return this.order;
    }
}
