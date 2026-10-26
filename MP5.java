import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Scanner;

public class MP5 {
    private static final Scanner scan = new Scanner(System.in);
    private static final HashMap<Integer, Book> books = new HashMap<>();
    private static final HashSet<String> keys = new HashSet<>();

    private static class Book {
        private final int id;
        private final String title;
        private int available;
        private int borrowed;
        private final LinkedList<String> waiting = new LinkedList<>();

        private Book(int id, String title, int copies) {
            this.id = id;
            this.title = title;
            available = copies;
        }

        private boolean borrow() {
            if (available == 0) {
                return false;
            }
            available--;
            borrowed++;
            return true;
        }

        private boolean returned() {
            if (borrowed == 0) {
                return false;
            }
            borrowed--;
            return true;
        }

        private void assign() {
            borrowed++;
        }

        private void addCopy() {
            available++;
        }

        private void add(String name) {
            waiting.add(name);
        }

        private String next() {
            return waiting.poll();
        }

        private int getId() {
            return id;
        }

        private String getTitle() {
            return title;
        }

        private int getAvailable() {
            return available;
        }

        private int getBorrowed() {
            return borrowed;
        }

        private boolean hasWaiting() {
            return !waiting.isEmpty();
        }

        private LinkedList<String> getWaiting() {
            return waiting;
        }
    }

    public static void main(String[] args) {
        seed();
        menu();
    }

    private static void seed() {
        books.put(1, new Book(1, "Java Basics", 1));
        books.put(2, new Book(2, "Algorithms", 0));
        books.put(3, new Book(3, "Databases", 0));
    }

    private static void menu() {
        boolean run = true;

        while (run) {
            System.out.println("\nLibrary Menu");
            System.out.println("1 Borrow");
            System.out.println("2 Reserve");
            System.out.println("3 Return");
            System.out.println("4 Find");
            System.out.println("5 Show waiting");
            System.out.println("0 Exit");
            int choice = number("Choose: ");

            switch (choice) {
                case 1:
                    borrow();
                    break;
                case 2:
                    reserve();
                    break;
                case 3:
                    giveback();
                    break;
                case 4:
                    find();
                    break;
                case 5:
                    show();
                    break;
                case 0:
                    run = false;
                    System.out.println("Application ended.");
                    break;
                default:
                    System.out.println("Invalid menu option.");
            }
        }
    }

    private static void borrow() {
        Book book = book();
        if (book == null) {
            return;
        }

        String name = text("Enter patron name: ");

        if (book.borrow()) {
            System.out.println(name + " borrowed book " + book.getId()
                    + " (" + book.getTitle() + ")");
            report(book);
        } else {
            System.out.println("No copies available");
        }
    }

    private static void reserve() {
        Book book = book();
        if (book == null) {
            return;
        }

        String name = text("Enter patron name: ");
        String key = book.getId() + ":" + name.toLowerCase();

        if (keys.contains(key)) {
            System.out.println("Duplicate rejected");
            return;
        }

        keys.add(key);
        book.add(name);
        System.out.println(name + " added to waiting list");
    }

    private static void giveback() {
        Book book = book();
        if (book == null) {
            return;
        }

        if (!book.returned()) {
            System.out.println("No borrowed copies");
            return;
        }

        if (book.hasWaiting()) {
            String name = book.next();
            keys.remove(book.getId() + ":" + name.toLowerCase());
            book.assign();
            System.out.println("Copy assigned to " + name);
        } else {
            book.addCopy();
            System.out.println("Copy returned");
        }

        report(book);
    }

    private static void find() {
        Book book = book();
        if (book != null) {
            report(book);
        }
    }

    private static void show() {
        Book book = book();
        if (book == null) {
            return;
        }

        if (!book.hasWaiting()) {
            System.out.println("Waiting: none");
            return;
        }

        System.out.print("Waiting: ");
        Iterator<String> list = book.getWaiting().iterator();
        while (list.hasNext()) {
            System.out.print(list.next());
            if (list.hasNext()) {
                System.out.print(", ");
            }
        }
        System.out.println();
    }

    private static Book book() {
        int id = number("Enter book ID: ");
        Book book = books.get(id);
        if (book == null) {
            System.out.println("Unknown book ID");
        }
        return book;
    }

    private static void report(Book book) {
        System.out.println("Available " + book.getAvailable() + ", borrowed " + book.getBorrowed());
    }

    private static String text(String prompt) {
        while (true) {
            System.out.print(prompt);
            String value = scan.nextLine().trim();
            if (!value.isEmpty()) {
                return value;
            }
            System.out.println("Input cannot be empty.");
        }
    }

    private static int number(String prompt) {
        while (true) {
            System.out.print(prompt);
            String value = scan.nextLine().trim();
            try {
                return Integer.parseInt(value);
            } catch (NumberFormatException error) {
                System.out.println("Please enter a valid whole number.");
            }
        }
    }
}
