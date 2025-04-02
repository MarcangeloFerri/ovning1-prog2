package se.su.ovning1;
public abstract class Recording extends Item implements PriceableWithVAT25 {
    private String artist;
    private int year;
    int condition;
    private double price;

    protected Recording(String name, String atrist, int year, int condition, double price){
        super(name);
        this.artist = atrist;
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
        return price;
    }

    public int getYear() {
        return year;
    }

    @Override
    public String toString() {
        return super.toString();
    }

    protected double getOriginalPrice(){
        return 0;
    }
}
