package com.newsaggregator.client.util;

import java.io.Console;
import java.util.Scanner;

public class ConsoleUtils {
    private static final Scanner scanner = new Scanner(System.in);
    private static final Console console = System.console();

    public static String readPassword(String prompt) {
        if (console != null) {
            char[] pwd = console.readPassword(prompt);
            return new String(pwd);
        } else {
            System.out.print(prompt);
            return scanner.nextLine();
        }
    }

    public static String readLine(String prompt) {
        if (console != null) {
            return console.readLine(prompt);
        } else {
            System.out.print(prompt);
            return scanner.nextLine();
        }
    }

    public static int readInt(String prompt) {
        if (console != null) {
            return Integer.parseInt(console.readLine(prompt));
        } else {
            System.out.print(prompt);
            return Integer.parseInt(scanner.nextLine());
        }
    }

    public static long readLong(String prompt) {
        if (console != null) {
            return Long.parseLong(console.readLine(prompt));
        } else {
            System.out.print(prompt);
            return Long.parseLong(scanner.nextLine());
        }
    }

    public static boolean readBoolean(String prompt) {
        if (console != null) {
            return console.readLine(prompt).equalsIgnoreCase("y");
        } else {
            System.out.print(prompt);
            return scanner.nextLine().equalsIgnoreCase("y");
        }
    }
}
