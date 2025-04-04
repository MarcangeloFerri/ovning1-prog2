package se.su.ovning1;
public abstract class Recording extends Item implements PriceableWithVAT25 {
    private final String artist;
    private final int year;
    int condition;
    private final double price;

    protected Recording(String name, String artist, int year, int condition, double price){
        super(name);
        this.artist = artist;
        this.year = year;
        this.condition = condition;
        this.price = price;
    }

    public String getArtist() {
        return artist;
    }
    public String getType(){
        return "Record";
    }

    public int getCondition() {
        return condition;
    }

    @Override
    public double getPrice() {
       double priceReductionFactror = condition / 10.0;
       double reducedPrice = price * priceReductionFactror;

       return Math.max(10, reducedPrice);
    }

    public int getYear() {
        return year;
    }

    @Override
    public String toString() {
        return super.toString();
    }

    protected double getOriginalPrice(){
        return price;
    }
}
