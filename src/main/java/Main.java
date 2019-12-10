import java.util.List;

public class Main {

    public static void main(String... args) {
        List<Item> items = Helpers.readItemsFromCSV("C:\\Users\\20190216s\\Desktop\\sample.csv");
        Helpers.checkQuantity(items);
        Helpers.checkDate(items);
    }

}



