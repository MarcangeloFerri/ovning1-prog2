package se.su.ovning1;
//hejdå
public class Book extends Item implements PriceableWithVAT6 {
    private String author;
    private double price;
    private boolean bound;

    public Book(String name, String author, double price, boolean bound){
        super(name);
        this.author = author;
        this.price = price;
        this.bound = bound;
    }
    @Override
    public String getName() {
        return super.getName();
    }
    //get type samma som get name?
    public String getType(){
        return "Book";
    }

    @Override
    public double getPrice() {
        if(bound){
            return price * 1.3; // +30%
        }else{
            return this.price;
        }
    }

    @Override
    public double getVAT() {
        return 0;
    }

    @Override
    public double PriceWithVAT() {
        return 0;
    }

    public String toString(){
        return getName()+" "+author+" "+price+" "+bound;
    }
}

