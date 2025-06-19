package com.adminclient.util;

import java.util.Scanner;


public class ConsoleUtil {

    private final Scanner scanner;

    public ConsoleUtil(Scanner scanner) {
        this.scanner = scanner;
    }

    public int readInt(String prompt) {
        while (true) {
            try {
                System.out.print(prompt);
                return Integer.parseInt(scanner.nextLine().trim());
            } catch (NumberFormatException ex) {
                System.out.println("Please enter a valid integer.");
            }
        }
    }

    public long readLong(String prompt) {
        while (true) {
            try {
                System.out.print(prompt);
                return Long.parseLong(scanner.nextLine().trim());
            } catch (NumberFormatException ex) {
                System.out.println("Please enter a valid number.");
            }
        }
    }

    public boolean readBoolean(String prompt) {
        while (true) {
            System.out.print(prompt);
            String s = scanner.nextLine().trim().toLowerCase();
            if (s.equals("true") || s.equals("t") || s.equals("yes") || s.equals("y"))
                return true;
            if (s.equals("false") || s.equals("f") || s.equals("no") || s.equals("n"))
                return false;
            System.out.println("Enter yes/true or no/false.");
        }
    }

    public String readLine(String prompt) {
        System.out.print(prompt);
        return scanner.nextLine().trim();
    }
}
