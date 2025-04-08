package se.su.ovning1;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.platform.commons.util.ReflectionUtils;


import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.Locale;
import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class Exercise1Test {

    static {
        Locale.setDefault(Locale.ENGLISH);
    }
    private static final String ORDER_INCORRECT_VALUE_EXCL_VAT = "Värdet exkl. moms stämmer inte.";
    private static final String ORDER_INCORRECT_VALUE_INCL_VAT = "Värdet inkl. moms stämmer inte.";
    private static final String BOUND_BOOK_INCORRECT_VALUE_EXCL_VAT = "Värdet (exkl. moms) på en inbunden bok beräknas inte rätt.";
    private static final String BOUND_BOOK_INCORRECT_VALUE_INCL_VAT = "Värdet (inkl. moms) på en inbunden bok beräknas inte rätt.";
    private static final String UNBOUND_BOOK_INCORRECT_VALUE_EXCL_VAT = "Värdet (exkl. moms) på en icke inbunden bok beräknas inte rätt.";
    private static final String UNBOUND_BOOK_INCORRECT_VALUE_INCL_VAT = "Värdet (inkl. moms) på en icke inbunden bok beräknas inte rätt.";
    private static final String INCORRECT_VAT_VALUE = "Klassen har fått fel momsvärde.";

    @ParameterizedTest
    @org.junit.jupiter.api.Order(10)
    @DisplayName("Book: testar att inbundna böcker får rätt pris / testing that bound books are given the correct price.")
    @CsvSource({"100.0,130.0,137.8", "0.0,0.0,0.0"})
    void boundBookCorrectPrice(double price, double expectedPrice, double expectedPricePlusVat) {
        var book = new Book("A Name", "An Author", price, true);
        assertEquals(0.06, book.getVAT(), INCORRECT_VAT_VALUE);
        assertEquals(expectedPrice, book.getPrice(), BOUND_BOOK_INCORRECT_VALUE_EXCL_VAT);
        assertEquals(expectedPricePlusVat, book.getPriceWithVAT(), BOUND_BOOK_INCORRECT_VALUE_INCL_VAT);
    }

    @ParameterizedTest
    @org.junit.jupiter.api.Order(11)
    @DisplayName("Book: testar att icke inbundna böcker får rätt pris / testing that non-bound books are given the correct price.")
    @CsvSource({"100.0,100.0,106.0", "0.0,0.0,0.0"})
    void unboundBookCorrectPrice(double price, double expectedPrice, double expectedPricePlusVat) {
        var book = new Book("A Name", "An Author", price, false);
        assertEquals(expectedPrice, book.getPrice(), BOUND_BOOK_INCORRECT_VALUE_EXCL_VAT);
        assertEquals(expectedPricePlusVat, book.getPriceWithVAT(), BOUND_BOOK_INCORRECT_VALUE_INCL_VAT);
    }

    @ParameterizedTest
    @org.junit.jupiter.api.Order(12)
    @DisplayName("Book: testar att toString innehåller nödvändig information.")
    @CsvSource({"For Whom The Bell Tolls, Ernest Hemingway, 125.0, true",
        "William Gibson, Neuromancer, 200.0, false"})
    void bookCorrectToString(String title, String author, double price, boolean bound) {
        var book = new Book(title, author, price, bound);
        var parts = Set.of(title, author, ("" + (bound ? price * 1.3 : price)), String.valueOf(bound));
        var message = String.format("Klassen Book: toString saknar information. Letade efter %s i strängen '%s' men något saknades.", parts, book);
        assertTrue(parts.stream().allMatch(s -> book.toString().contains(s)), message);
    }

    @Test
    @org.junit.jupiter.api.Order(13)
    @DisplayName("Book: testar att klassen har nödvändiga medlemsvariabler.")
    void bookHasCorrectFields() {
        var bookClass = Book.class;
        var fields = bookClass.getDeclaredFields();
        var hasCorrectFieldNames = Arrays.stream(fields).map(Field::getName).collect(Collectors.toSet()).containsAll(Set.of("author", "bound", "price"));
        assertTrue(fields.length >= 3, "Klassen ska innehålla minst tre medlemsvariabler.");
        assertTrue(hasCorrectFieldNames, "Klassen innehåller inte rätt medlemsvariabler.");
    }

    @Test
    @org.junit.jupiter.api.Order(14)
    @DisplayName("Book: testar att klassen implementerar rätt gränssnitt")
    void bookImplementsCorrectInterface() {
        var bookClass = Book.class;
        var interfaces = bookClass.getInterfaces();
        PriceableWithVAT6.class.isAssignableFrom(bookClass);
        var implementsInterface = true;

        assertEquals(1, interfaces.length, "Enligt klassdiagrammet ska klassen bara implementera ett gränssnitt.");
        assertTrue(implementsInterface, "Klassen implementerar inte rätt gränssnitt.");
    }

    @Test
    @org.junit.jupiter.api.Order(15)
    @DisplayName("Book: testar att klassen har nödvändiga metoder.")
    void bookImplementsCorrectMethods() {
        var bookClass = Book.class;
        var methods = bookClass.getDeclaredMethods();
        var pubMethods = Arrays.stream(methods).filter(method -> Modifier.isPublic(method.getModifiers())).count();

        assertEquals(2, pubMethods, "Enligt klassdiagrammet ska klassen ha exakt två publika metoder utöver konstruktorn.");

        try {
            bookClass.getDeclaredMethod("getPrice");
        } catch (NoSuchMethodException e) {
            fail("Kunde inte hitta metoden getPrice.");
        }
    }

    @Test
    @org.junit.jupiter.api.Order(1)
    @DisplayName("Item: testar att klassens konstruktor är korrekt.")
    void itemHasCorrectConstructor() {
        var itemClass = Item.class;
        var constructors = itemClass.getDeclaredConstructors();
        var constructor = constructors[0];
        var constructorParameters = constructor.getParameterCount();
        var constructorType = constructor.getParameters()[0].getType();

        assertEquals(1, constructors.length, "Enligt klassdiagrammet ska det bara finnas en enda konstruktor.");
        assertEquals(1, constructorParameters, "Den enda konstruktorn ska enligt klassdiagrammet bara ha en enda parameter.");
        assertEquals(String.class, constructorType, "Den enda konstruktorns enda parameter har inte rätt typ.");
    }

    @Test
    @org.junit.jupiter.api.Order(2)
    @DisplayName("Item: testar att klassen har nödvändiga medlemmar.")
    void itemHasCorrectMember() {
        var itemClass = Item.class;
        var fields = itemClass.getDeclaredFields();
        var field = fields[0];
        var fieldType = field.getType();
        var isFinal = Modifier.isFinal(field.getModifiers());

        assertEquals(1, fields.length, "Enligt klassdiagrammet ska det bara finnas en enda instansvariabel.");
        assertEquals(String.class, fieldType, "Den enda instansvariabeln har inte den förväntade typen.");
        assertTrue(isFinal, "Den enda medlemsvariabeln ska inte kunna ändra värde.");
    }

    @Test
    @org.junit.jupiter.api.Order(3)
    @DisplayName("Item: testar att klassen har nödvändiga metoder")
    void itemHasCorrectMethods() {
        var itemClass = Item.class;
        var methods = itemClass.getDeclaredMethods();
        var pubMethods = Arrays.stream(methods).filter(method -> Modifier.isPublic(method.getModifiers())).count();

        assertEquals(1, pubMethods, "Enligt klassdiagrammet ska klassen bara ha en publik metod utöver konstruktorn.");
    }

    @Test
    @org.junit.jupiter.api.Order(4)
    @DisplayName("Item: testar att klassen implementerar rätt gränssnitt.")
    void itemImplementsVat() {
        var clazz = Item.class;
        var interfaces = clazz.getInterfaces();
        Priceable.class.isAssignableFrom(clazz);
        var implementsInterface = true;

        assertEquals(1, interfaces.length, "Enligt klassdiagrammet ska klassen bara implementera ett gränssnitt.");
        assertTrue(implementsInterface, "Klassen implementerar inte rätt gränssnitt.");
    }

    @Test
    @org.junit.jupiter.api.Order(5)
    @DisplayName("Item: testar att klassen inte kan instansieras.")
    void itemIsAbstract() {
        assertTrue(ReflectionUtils.isAbstract(Item.class), "Klassen Item ska vara abstrakt och inte kunna instansieras.");
    }

    @ParameterizedTest
    @org.junit.jupiter.api.Order(20)
    @DisplayName("Recording: testar att CompactDisc får rätt pris.")
    @CsvSource({"2021,10,200,200,250", "2021,5,200,100,125", "2021,1,200,20,25", "2021,0,200,10,12.5"})
    void recordingCorrectPriceForCD(int year, int condition, double price, double expected, double plusVat) {
        var message = String.format("Klassen CompactDisc: värdet (exkl. moms) på en CD beräknas inte rätt för slitage %d.", condition);

        var cdCond10 = new CompactDisc("A Name", "An Artist", year, condition, price);

        assertEquals(0.25, cdCond10.getVAT(), "Klassen CompactDisc: " + INCORRECT_VAT_VALUE + ".");
        assertEquals(expected, cdCond10.getPrice(), message);
        assertEquals(plusVat, cdCond10.getPriceWithVAT(), message);
    }

    @ParameterizedTest
    @org.junit.jupiter.api.Order(21)
    @DisplayName("Recording: testar att LongPlay får rätt pris.")
    @CsvSource({"2025,10,200,200,250", "2025,5,200,100,125", "2025,1,200,20,25", "2025,0,200,10,12.5"})
    void recordingCorrectPriceForLP(int year, int condition, double price, double expected, double plusVat) {
        var message = String.format("Klassen LongPlay: värdet (exkl. moms) på en LP beräknas inte rätt för slitage %d.", condition);

        var item = new LongPlay("A Name", "An Artist", year, condition, price);

        assertEquals(expected, item.getPrice(), message);
        assertEquals(plusVat, item.getPriceWithVAT(), message);
    }

    @ParameterizedTest
    @org.junit.jupiter.api.Order(22)
    @DisplayName("Recording: testar att äldre LongPlay får rätt pris.")
    @CsvSource({"2020,10,200,225,281.25", "1950,10,200,575,718.75"})
    void recordingCorrectPriceForLPFrom2020(int year, int condition, double price, double expected, double plusVat) {
        var message = String.format("Klassen LongPlay: värdet (exkl. moms) på en LP från " + year + " beräknas inte rätt för slitage %d.", condition);

        var item = new LongPlay("A Name", "An Artist", year, condition, price);

        assertEquals(expected, item.getPrice(), message);
        assertEquals(plusVat, item.getPriceWithVAT(), message);
    }

    @ParameterizedTest
    @org.junit.jupiter.api.Order(23)
    @DisplayName("Recording: testar att toString innehåller nödvändig information för CD.")
    @CsvSource({"Phoebe Bridgers, Punisher, 2023, 10, 200, 200, 250", "Bruce Springsteen, The River, 1980, 10, 300, 300, 375"})
    void recordingCorrectToStringCD(String artist, String title, int year, int condition, double originalPrice, double price, double pricePlusVAT) {
        var item1 = new CompactDisc(title, artist, year, condition, originalPrice);
        var parts = Set.of("CD", title, artist, String.valueOf(year), String.valueOf(condition), String.valueOf(price));
        var type = item1.getClass().getSimpleName();

        var strings = "CD" + " { name='" + title + "', artist='" + artist + "', year=" + year + ", condition=" + condition + ", original price=" + originalPrice + ", price=" + price + ", price+VAT=" + pricePlusVAT + " }\n";
        var message = String.format("Klassen Recording: toString saknar information.%nLetade efter %s i strängen:%n%n'%s'%n%nmen något saknades för subtypen %s. Jämförelsen gör skillnad på stora och små bokstäver.%n", strings, item1, type);

        assertTrue(parts.stream().allMatch(s -> item1.toString().contains(s)), message);
    }

    @ParameterizedTest
    @org.junit.jupiter.api.Order(23)
    @DisplayName("Recording: testar att toString innehåller nödvändig information för LP.")
    @CsvSource({"Phoebe Bridgers, Punisher, 2021, 10, 200.0, 220.0, 275.0", "Bruce Springsteen, The River, 1980, 10, 300.0, 525.0, 656.25"})
    void recordingCorrectToStringLP(String artist, String title, int year, int condition, double originalPrice, double price, double pricePlusVAT) {
        var item1 = new LongPlay(title, artist, year, condition, originalPrice);
        var parts = Set.of("LP", title, artist, String.valueOf(year), String.valueOf(condition), String.valueOf(price));
        var type = item1.getClass().getSimpleName();

        var strings = "LP" + " { name='" + title + "', artist='" + artist + "', year=" + year + ", condition=" + condition + ", original price=" + originalPrice + ", price=" + price + ", price+VAT=" + pricePlusVAT + " }\n";
        var message = String.format("Klassen Recording: toString saknar information.%nLetade efter %s i strängen:%n%n'%s'%n%nmen något saknades för subtypen %s. Jämförelsen gör skillnad på stora och små bokstäver.%n", strings, item1, type);

        assertTrue(parts.stream().allMatch(s -> item1.toString().contains(s)), message);
    }

    @Test
    @org.junit.jupiter.api.Order(24)
    @DisplayName("Recording: testar att värdet inte hamnar under 10.0.")
    void recordingGetsCorrectMinimumValue() {
        Recording item = new LongPlay("test", "test", 2025, 1, 90);
        assertEquals(10.0, item.getPrice(), "Fel värde för Longplay med condition 1.");

        item = new CompactDisc("test", "test", 2024, 0, 5);
        assertEquals(10.0, item.getPrice(), "Fel värde för CompactDisc med condition 0.");
    }

    @Test
    @org.junit.jupiter.api.Order(25)
    @DisplayName("Recording: testar att klassen har nödvändiga medlemsvariabler.")
    void recordingHasCorrectFields() {
        var clazz = Recording.class;
        var fields = clazz.getDeclaredFields();
        var hasCorrectFieldNames = Arrays.stream(fields).map(Field::getName).collect(Collectors.toSet()).containsAll(Set.of("year", "artist", "condition", "price"));
        assertEquals(4, fields.length, "Klassen innehåller inte rätt antal medlemsvariabler.");
        assertTrue(hasCorrectFieldNames, "Klassen innehåller inte rätt medlemsvariabler.");
    }

    @Test
    @org.junit.jupiter.api.Order(26)
    @DisplayName("Recording: testar att klassen implementerar rätt gränssnitt.")
    void recordingImplementsCorrectInterface() {
        var recordingClass = Recording.class;
        var interfaces = recordingClass.getInterfaces();
        var implementsInterface = PriceableWithVAT25.class.isAssignableFrom(recordingClass);

        assertEquals(1, interfaces.length, "Enligt klassdiagrammet ska klassen bara implementera ett gränssnitt.");
        assertTrue(implementsInterface, "Klassen implementerar inte rätt gränssnitt.");
    }

    @Test
    @org.junit.jupiter.api.Order(27)
    @DisplayName("Recording: testar att klassen har nödvändiga metoder.")
    void recordingImplementsCorrectMethods() {
        var recordingClass = Recording.class;
        var methods = recordingClass.getDeclaredMethods();
        var pubMethods = Arrays.stream(methods).filter(method -> Modifier.isPublic(method.getModifiers())).count();

        assertEquals(6, pubMethods, "Enligt klassdiagrammet ska klassen ha sex publika metoder utöver konstruktorn.");
    }

    @Test
    @org.junit.jupiter.api.Order(28)
    @DisplayName("Recording: testar att klassen inte kan instansieras.")
    void recordingIsAbstract() {
        assertTrue(ReflectionUtils.isAbstract(Recording.class), "Klassen Recording ska vara abstrakt och inte kunna instansieras.");
    }

    @Test
    @org.junit.jupiter.api.Order(29)
    @DisplayName("Order: testar metoderna getPrice, getPriceWithVAT och printReceipt.")
    void testOrderMethods() {
        Item book1 = new Book("A guide to modern jazz", "Unknown author", 100, false);
        Item book2 = new Book("Beethoven: a biography", "Holmqvist", 400, false);
        Item book2bound = new Book("Beethoven: a biography", "Holmqvist", 400, true);

        Item item1 = new LongPlay("Giant Steps", "John Coltrane", 1959, 10, 100);
        Item cd2 = new CompactDisc("Kind of Blue", "Miles Davis", 1959, 5, 100);
        Item lp1 = new CompactDisc("Punisher", "Phoebe Bridgers", 2020, 10, 200);
        Item lp2 = new LongPlay("What Kinda Music", "Tom Misch", 2020, 10, 175);
        Item lp3 = new LongPlay("Little Oblivions", "Julien Baker", 2021, 10, 120);

        /*
Receipt for order #1
-----------
Book { name='A guide to modern jazz', author='Unknown author', bound=false, price=100.0, price+VAT=106.0 }
Book { name='Beethoven: a biography', author='Holmqvist', bound=true, price=520.0, price+VAT=551.2 }
Total excl. VAT: 620.0
Total incl. VAT: 657.2
-----------
         */
        var message = "Metoden getReceipt saknar information.%nLetade efter följande ord %s i strängen:%n%n%s%nmen något saknades.%n";

        var order = new Order(book1, book2bound);
        var receipt = order.getReceipt();

        assertEquals(620.0, order.getTotalValue(), ORDER_INCORRECT_VALUE_EXCL_VAT);
        assertEquals(657.2, order.getTotalValuePlusVAT(), ORDER_INCORRECT_VALUE_INCL_VAT);

        var part = Set.of("Book", "A guide to modern jazz", "Unknown author", "false", "100.0", "106.0");
        assertTrue(part.stream().allMatch(receipt::contains), String.format(message, part, receipt));

        part = Set.of("Book", "Beethoven: a biography", "Holmqvist", "true", "520.0", "551.2");
        assertTrue(part.stream().allMatch(receipt::contains), String.format(message, part, receipt));

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
        order = new Order(book2, item1, cd2);
        receipt = order.getReceipt();

        assertEquals(880.0, order.getTotalValue(), ORDER_INCORRECT_VALUE_EXCL_VAT);
        assertEquals(1024, order.getTotalValuePlusVAT(), ORDER_INCORRECT_VALUE_INCL_VAT);

        part = Set.of("Book", "Beethoven: a biography", "Holmqvist", "false", "400.0", "424.0");
        assertTrue(part.stream().allMatch(receipt::contains), String.format(message, part, receipt));

        part = Set.of("Giant Steps", "John Coltrane", "1959", "LP", "10", "100.0", "430.0", "537.5");
        assertTrue(part.stream().allMatch(receipt::contains), String.format(message, part, receipt));

        part = Set.of("Kind of Blue", "Miles Davis", "1959", "CD", "5", "100.0", "50.0", "62.5");
        assertTrue(part.stream().allMatch(receipt::contains), String.format(message, part, receipt));

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
        order = new Order(lp1, lp2, lp3);
        receipt = order.getReceipt();

        assertEquals(540.0, order.getTotalValue(), ORDER_INCORRECT_VALUE_EXCL_VAT);
        assertEquals(675.00, order.getTotalValuePlusVAT(), ORDER_INCORRECT_VALUE_INCL_VAT);

        part = Set.of("Punisher", "Phoebe Bridgers", "2020", "CD", "10", "200.0", "250.0");
        assertTrue(part.stream().allMatch(receipt::contains), String.format(message, part, receipt));

        part = Set.of("What Kinda Music", "Tom Misch", "2020", "LP", "10", "175.0", "200.0", "250.0");
        assertTrue(part.stream().allMatch(receipt::contains), String.format(message, part, receipt));

        part = Set.of("Little Oblivions", "Julien Baker", "2021", "LP", "10", "120.0", "175.0");
        assertTrue(part.stream().allMatch(receipt::contains), String.format(message, part, receipt));
    }
}
