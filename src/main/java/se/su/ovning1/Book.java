package se.su.ovning1;

public class Book extends Item implements PriceableWithVAT6 {
    private final String author;
    private final double price;
    private final boolean bound;

    public Book(String name, String author, double price, boolean bound){
        super(name);
        this.author = author;
        this.price = price;
        this.bound = bound;
    }

    protected String getType(){
        return "Book: ";
    }

    @Override
    public double getPrice() {
        if(bound){
            return price * 1.3; // +30%
        }else{
            return this.price;
        }
    }

    public String toString(){
        return getType() +"{"+
                "name="+getName()+", "
                +"author="+author+", "
                +"price="+getPrice()+", "
                +"bound"+bound+", "
                +"price+VAT="+getPriceWithVAT() +"}";
    }
}




