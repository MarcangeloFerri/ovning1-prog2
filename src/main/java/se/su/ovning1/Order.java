package se.su.ovning1;

import java.util.Arrays;

public class Order {
    private final long orderNumber;
    private static long counter = 1;
    private final Item[] items;

    public Order(Item[]items) {
        this.orderNumber = counter++;
        this.items = Arrays.copyOf(items, items.length);
    }
  public String getReceipt(){
        return "Receipt for order #" + orderNumber + "\n" +
                "----------\n"+ itemDescription()+"\n"+
                "Total excl. VAT: "+getTotalValue()+"\n"+
                "Total incl. VAT: "+getTotalValuePlusVAT()
                +"\n"+"\n";

  }
  private String itemDescription(){
        String ans ="";
        for(Item i : items){
            if(i instanceof Book){
                ans += ((Book) i).getType() + i.toString() +"\n";
            } else if (i instanceof LongPlay) {
                ans += ((LongPlay)i).getType() + i.toString() +"\n";
            } else if (i instanceof CompactDisc) {
                ans += ((CompactDisc)i).getType() +i.toString() +"\n";
            }

        }
        return ans;
  }

  public double getTotalValuePlusVAT(){
        double sumWithVAT = 0;
        for(Item item : items) {
            sumWithVAT += item.getPriceWithVAT();
        }
        return sumWithVAT;
    }

 public double getTotalValue(){
        double sum = 0;
        for(Item i : items){
            sum += i.getPrice();
        }
        return sum;
 }
}
