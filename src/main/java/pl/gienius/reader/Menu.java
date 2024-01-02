package pl.gienius.reader;

import java.util.Scanner;

public class Menu {

    private Reader reader;

    private final ReaderClient client = new ReaderClient();

    private void header(){
        System.out.println("### Reader Client app ###");
    }

    public void init(){
        header();
        createReaderMenu();
        menu();
    }

    public void menu(){
        header();

    }

    private void createReaderMenu(){
        Scanner scanner = new Scanner(System.in);
        System.out.println("Create new reader! What's your name?");
        String name = scanner.nextLine();
        while (name.isEmpty() || name.isBlank()) {
            System.out.println("Type at least 1 char! What's your name?");
        }
        reader = client.createReader(name);
        System.out.println("New Reader: " + reader);
    }
}
