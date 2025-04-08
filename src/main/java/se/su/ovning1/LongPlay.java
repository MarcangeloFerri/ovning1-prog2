package se.su.ovning1;

public class LongPlay extends Recording{
    public LongPlay(String name, String artist, int year, int condition, double price){
        super(name,artist,year,condition,price);
    }

    @Override
    public double getPrice() {
        double basePrice = super.getPrice();
        int yearNow = java.time.Year.now().getValue();
        int numOfYears = yearNow-getYear();
        double timeValueInc = numOfYears * 5.0;

        return basePrice + timeValueInc;
    }

    @Override
    public double getPriceWithVAT() {
        return getPrice() *(1.+getVAT());
    }

    @Override
    public String getType() {
        return "LP: ";
    }
}
