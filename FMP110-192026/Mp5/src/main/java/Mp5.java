import java.util.Scanner;


// Basco Miguel
class LibraryException extends Exception {
    public LibraryException(String message) {
        super(message);
    }
}

class Book {
    private final int id;
    private final String title;
    private final int total;
    private int available;
    private int borrowed;

    public Book(int id, String title, int total) {
        this.id = id;
        this.title = title;
        this.total = total;
        this.available = total;
        this.borrowed = 0;
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public int getAvailable() {
        return available;
    }

    public void borrow() throws LibraryException {
        if (available == 0) {
            throw new LibraryException("No available copies for this book.");
        }

        available--;
        borrowed++;
        checkCopies();
    }

    public void returnCopy() throws LibraryException {
        if (borrowed == 0) {
            throw new LibraryException("There are no borrowed copies to return.");
        }

        borrowed--;
        available++;
        checkCopies();
    }

    private void checkCopies() {
        if (available + borrowed != total) {
            throw new IllegalStateException("Book copy count invariant was violated.");
        }
    }

    public void display() {
        System.out.println("ID: " + id
                + " | Title: " + title
                + " | Available: " + available
                + " | Borrowed: " + borrowed);
    }
}

public class Mp5 {
    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        Book[] books = {
            new Book(1, "Java Basics", 2),
            new Book(2, "Algorithms", 1),
            new Book(3, "Databases", 0)
        };

        boolean running = true;

        while (running) {
            displayMenu();

            try {
                int choice = readInteger("Choose an option: ");

                switch (choice) {
                    case 1:
                        borrowBook(books);
                        break;
                    case 2:
                        returnBook(books);
                        break;
                    case 3:
                        findBook(books);
                        break;
                    case 4:
                        listBooks(books);
                        break;
                    case 0:
                        running = false;
                        System.out.println("Thank you for using the library system.");
                        break;
                    default:
                        System.out.println("Invalid menu option. Choose from 0 to 4.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a whole number.");
            } catch (LibraryException e) {
                System.out.println("Transaction failed: " + e.getMessage());
            } catch (IllegalStateException e) {
                System.out.println("System error: " + e.getMessage());
            } finally {
                System.out.println();
            }
        }

        scanner.close();
    }

    private static void displayMenu() {
        System.out.println("=====++++++++=====");
//        System.out.println("Book1 (Java Basics Book)");
//        System.out.println("Book2 (Algorithms Book)");
//        System.out.println("Book3 (Databases Book)");
//        System.out.println("=====++++++++=====");
        System.out.println("1. Borrow");
        System.out.println("2. Return");
        System.out.println("3. Find");
        System.out.println("4. List");
        System.out.println("0. Exit");
        System.out.println("=====++++++++=====");
    }

    private static void borrowBook(Book[] books) throws LibraryException {
        int id = readInteger("Enter book ID to borrow: ");
        Book book = findBookById(books, id);
        book.borrow();
        System.out.println("Successfully borrowed: " + book.getTitle());
        System.out.println("Available copies: " + book.getAvailable());
    }

    private static void returnBook(Book[] books) throws LibraryException {
        int id = readInteger("Enter book ID to return: ");
        Book book = findBookById(books, id);
        book.returnCopy();
        System.out.println("Successfully returned: " + book.getTitle());
        System.out.println("Available copies: " + book.getAvailable());
    }

    private static void findBook(Book[] books) throws LibraryException {
        int id = readInteger("Enter book ID to find: ");
        findBookById(books, id).display();
    }

    private static void listBooks(Book[] books) {
        System.out.println("===== BOOK LIST =====");
        for (Book book : books) {
            book.display();
        }
    }

    private static Book findBookById(Book[] books, int id) throws LibraryException {
        for (Book book : books) {
            if (book.getId() == id) {
                return book;
            }
        }

        throw new LibraryException("Error Book " + id + " Not Found or Book Doesnt .");
    }

    private static int readInteger(String message) throws NumberFormatException {
        System.out.print(message);
        return Integer.parseInt(scanner.nextLine().trim());
    }
}
