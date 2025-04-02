package se.su.ovning1;

public class Main {
    public static void main(String[] args) {

        Item book1 = new Book("A guide to modern jazz", "Unknown author", 100, false);
        Item book2 = new Book("Beethoven: a biography", "Holmqvist", 400, false);
        Item book2bound = new Book("Beethoven: a biography", "Holmqvist", 400, true);

        Item item1 = new LongPlay("Giant Steps", "John Coltrane", 1959, 10, 100);
        Item cd2 = new CompactDisc("Kind of Blue", "Miles Davis", 1959, 5, 100);
        Item lp1 = new CompactDisc("Punisher", "Phoebe Bridgers", 2020, 10, 200);
        Item lp2 = new LongPlay("What Kinda Music", "Tom Misch", 2020, 10, 150);
        Item lp3 = new LongPlay("Little Oblivions", "Julien Baker", 2021, 10, 120);

        Order order1 = new Order(book1, book2bound);

        System.out.println(order1.getReceipt());

		/*
        Receipt for order #1
        -----------
        Book { name='A guide to modern jazz', author='Unknown author', bound=false, price=100.0, price+VAT=106.0 }
        Book { name='Beethoven: a biography', author='Holmqvist', bound=true, price=520.0, price+VAT=551.2 }
        Total excl. VAT: 620.0
        Total incl. VAT: 657.2
        -----------
		*/

        Order jazz = new Order(book2, item1, cd2);
        System.out.println(jazz.getReceipt());

		/*
        Receipt for order #2
        -----------
        Book { name='Beethoven: a biography', author='Holmqvist', bound=false, price=400.0, price+VAT=424.0 }
        LP { name='Giant Steps', artist='John Coltrane', year=1959, condition=10, original price=100.0, price=425.0, price+VAT=531.25 }
        CD { name='Kind of Blue', artist='Miles Davis', year=1959, condition=5, original price=100.0, price=50.0, price+VAT=62.5 }
        Total excl. VAT: 875.0
        Total incl. VAT: 1017.75
        -----------
		*/

        Order modernIndie = new Order(lp1, lp2, lp3);
        System.out.println(modernIndie.getReceipt());

		/*
        Receipt for order #3
        -----------
        CD { name='Punisher', artist='Phoebe Bridgers', year=2020, condition=10, original price=200.0, price=200.0, price+VAT=250.0 }
        LP { name='What Kinda Music', artist='Tom Misch', year=2020, condition=10, original price=150.0, price=170.0, price+VAT=212.5 }
        LP { name='Little Oblivions', artist='Julien Baker', year=2021, condition=10, original price=120.0, price=135.0, price+VAT=168.75 }
        Total excl. VAT: 505.0
        Total incl. VAT: 631.25
        -----------
		*/

    }
}
