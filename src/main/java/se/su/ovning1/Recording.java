package se.su.ovning1;
public abstract class Recording extends Item implements PriceableWithVAT25 {
    private final String artist;
    private final int year;
    private int condition;
    private final double price;

    protected Recording(String name, String artist, int year, int condition, double price){
        super(name);
        this.artist = artist;
        this.year = year;
        this.condition = condition;
        this.price = price;
    }
    public abstract String getType();

    public String getArtist() {
        return artist;
    }

    public int getCondition() {
        return condition;
    }

    @Override
    public double getPrice() {
       double reducedPrice = price * (condition/10.0);
       return Math.max(10.0, reducedPrice);
    }

    public int getYear() {
        return year;
    }

    @Override
    public String toString() {
        return getType() + "{" + this.getName() +", "+
                "artist= " + artist+", "+
                "year= " + year+", " +
                "condition="+ condition+", "+
                "original price="+getOriginalPrice()+", "+
                "price="+getPrice()+", "+
                "price + vat="+getPriceWithVAT();
    }

    protected double getOriginalPrice(){
        return price;
    }
}
