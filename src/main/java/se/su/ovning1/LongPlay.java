package se.su.ovning1;

public class LongPlay extends Recording{
    public LongPlay(String name, String artist, int year, int condition, double price){
        super(name,artist,year,condition,price);
    }

    @Override
    public double getVAT() {
        return 25;
    }

    @Override
    public double PriceWithVAT() {
        return getPrice() *(1.+getVAT());
    }
}
