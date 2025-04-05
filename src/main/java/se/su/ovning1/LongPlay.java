package se.su.ovning1;

public class LongPlay extends Recording{
    public LongPlay(String name, String artist, int year, int condition, double price){
        super(name,artist,year,condition,price);
    }

    @Override
    public double getPrice() {
        return super.getPrice() +(90.0) * (0.1) + (2025 - 2020) * (5.0);
    }

    @Override
    public double getPriceWithVAT() {
        return getPrice() *(1+getVAT());
    }

    @Override
    public String getType() {
        return "LP";
    }
}
