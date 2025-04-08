package se.su.ovning1;

public class CompactDisc extends Recording {
    CompactDisc(String name, String artist, int year, int condition, double price){
        super(name,artist,year,condition,price);


    }
    @Override
    public String getType(){
        return "CD: ";
    }

    @Override
    public double getPriceWithVAT() {
        return getPrice()*1.25;
    }
}
