package se.su.ovning1;

public class Order {
    private final long orderNumber;
    private static long counter = 1;
    private final Item[] items;

    public Order(Item[]items) {
        this.orderNumber = counter++;
        this.counter = counter;
        this.items = new Item[items.length];
    }
}
