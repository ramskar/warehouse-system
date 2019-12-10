import org.apache.commons.lang3.math.NumberUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

public abstract class Helpers extends AbstractItem {

    public static Item createItem(String[] metadata) {
        String item_name = metadata[0];
        String code = metadata[1];
        String quantity = metadata[2];
        String expiration_date = metadata[3];

        return new Item(item_name, code, quantity, expiration_date);
    }

    public static void checkQuantity(List<Item> items) {
        Scanner input = new Scanner(System.in);
        System.out.println("Enter quantity");
        String quantity = input.nextLine();

        for (Item item : items) {
            if (NumberUtils.toInt(item.getQuantity()) < NumberUtils.toInt(quantity)) {
                System.out.println(item.getItem_name() + " Quantity: " + item.getQuantity() + " Expiration date: " + item.getExpirationDate());
            }
        }
    }

    public static void checkDate(List<Item> items) {
        Scanner input = new Scanner(System.in);
        System.out.println("Enter date");
        String enteredDate = input.nextLine();

        LocalDate checkDate = LocalDate.parse(enteredDate);

        for (Item item : items) {
            LocalDate itemDate = LocalDate.parse(item.getExpirationDate());

            if (checkDate.isAfter(itemDate)) {
                System.out.println(item.getItem_name() + " Quantity: " + item.getQuantity() + " Expiration date: " + item.getExpirationDate());
            }
        }
    }

    public static List<Item> readItemsFromCSV(String fileName) {
        List<Item> items = new ArrayList<>();
        Path pathToFile = Paths.get(fileName);

        try (BufferedReader br = Files.newBufferedReader(pathToFile,
                StandardCharsets.US_ASCII)) {

            String line = br.readLine();
            int count = 0;
            while (line != null) {

                String[] attributes = line.split(",");
                if (count != 0) {
                    Item item = createItem(attributes);
                    items.add(item);
                }

                line = br.readLine();
                count++;
            }

        } catch (IOException ioe) {
            ioe.printStackTrace();
        }

        List<Item> duplicates = findDuplicates(items);
        removeDuplicates(duplicates, items);

        Collections.sort(items, Comparator.comparing(Item::getItem_name));

        return items;
    }

    private static List<Item> findDuplicates(List<Item> items) {
        List<Item> duplicates = items.stream()
                .collect(
                        Collectors.groupingBy(p -> p.getItem_name() + "-" + p.getExpirationDate() + "-" + p.getCode(),
                                Collectors.toList()))
                .values()
                .stream()
                .filter(i -> i.size() > 1)
                .flatMap(j -> j.stream())
                .collect(Collectors.toList());

        return duplicates;
    }

    public static void removeDuplicates(List<Item> duplicates, List<Item> items) {
        for (int j = 0; j < duplicates.size(); j++) {
            items.remove(duplicates.get(j));

            for (int i = j + 1; i < duplicates.size(); i++) {
                if (duplicates.get(j).getItem_name().equals(duplicates.get(i).getItem_name()) &&
                        duplicates.get(j).getCode().equals(duplicates.get(i).getCode()) &&
                        duplicates.get(j).getExpirationDate().equals(duplicates.get(i).getExpirationDate()) &&
                        i != j) {

                    String[] newItem = new String[]{
                            duplicates.get(j).getItem_name(),
                            duplicates.get(i).getCode(),
                            Integer.toString(Integer.valueOf(duplicates.get(j).getQuantity()) + Integer.valueOf(duplicates.get(i).getQuantity())),
                            duplicates.get(i).getExpirationDate()
                    };

                    Item item = createItem(newItem);
                    items.add(item);
                }
            }
        }
    }
}
