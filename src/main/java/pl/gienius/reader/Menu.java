package pl.gienius.reader;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Menu {

    private Reader reader;
    List<Book> books = new ArrayList<>();
    List<Long> rented = new ArrayList<>();

    private final ReaderClient client = new ReaderClient();

    private void header() {
        System.out.println("### Reader Client app ###");
    }

    public void init() {
        header();
        createReaderMenu();
        menu();
    }

    public void menu() {
        while (true) {
            printMainMenu();
            Scanner scanner = new Scanner(System.in);
            int choice = scanner.nextInt();
            switch (choice) {
                case 1:
                    getAvailableBooks();
                    break;
                case 2:
                    rentBookMenu();
                    break;
                case 3:
                    returnBookMenu();
                    break;
                case 0: {
                    System.out.println("Exiting...");
                    return;
                }
                default:
                    System.out.println("Invalid option. Please try again.");
            }
        }
    }

    private void createReaderMenu() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Create new reader! What's your name?");
        String name = scanner.nextLine();
        while (name.isEmpty() || name.isBlank()) {
            System.out.println("Type at least 1 char! What's your name?");
        }
        reader = client.createReader(name);
        System.out.println("New Reader: " + reader);
    }

    private void printMainMenu() {
        header();
        System.out.println("1. Show all books");
        System.out.println("2. Rent a book");
        System.out.println("3. Return a book");
        System.out.println("0. Exit");
        System.out.print("Choose an option: ");
    }

    private void rentBookMenu() {
        printAllBooks();
        Scanner scanner = new Scanner(System.in);
        System.out.println("Which book to rent: ");
        Long bookId = scanner.nextLong();
        if (client.rentBook(bookId, reader.getId())) {
            rented.add(bookId);
            System.out.println("Rented the book: " + bookId);
        } else System.out.println("The book is blocked or something went wrong...");
    }

    private void getAvailableBooks() {
        printAllBooks();
    }

    private void printAllBooks() {
        books = client.getAvailableBooks();
        if (books == null || books.isEmpty())
            System.out.println("No books");
        else {
            for (Book book : books) {
                System.out.println("Id: " + book.getId());
                System.out.println("\tTitle: " + book.getTitle());
                System.out.println("\tRelease date: " + book.getReleaseDate());
                System.out.println("\tDescription: " + book.getDescription());
                System.out.println("\n");
            }
        }
    }

    private void printRentedBooks(){
        if(rented.isEmpty()){
            System.out.println("List is empty!");
        }
        else {
            System.out.println("List of books is:");
            for(Long bookId : rented){
                for (Book book : books) {
                    if(bookId.equals(book.getId())) {
                        System.out.println("Id: " + book.getId());
                        System.out.println("\tTitle: " + book.getTitle());
                        System.out.println("\tRelease date: " + book.getReleaseDate());
                        System.out.println("\tDescription: " + book.getDescription());
                        System.out.println("\n");
                    }
                }
            }
        }
    }

    private void returnBookMenu(){
        printRentedBooks();
        if(rented.isEmpty()) return;
        Scanner scanner = new Scanner(System.in);
        System.out.println("Which book to return: ");
        Long bookId = scanner.nextLong();
        if (client.returnBook(bookId, reader.getId())) {
            rented.remove(bookId);
            System.out.println("Returned the book: " + bookId);
        } else System.out.println("Something went wrong...");
    }
}
