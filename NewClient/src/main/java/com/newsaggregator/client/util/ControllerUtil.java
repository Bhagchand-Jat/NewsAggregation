package com.newsaggregator.client.util;

import java.util.List;
import java.util.function.Function;
import java.util.function.Supplier;

import static com.newsaggregator.client.util.UiText.*;

public final class ControllerUtil {

    private ControllerUtil() {
    }

    public static <E extends Enum<E> & Labeled> E promptChoice(E[] values, String header) {
        System.out.println("\n" + header + "\n");

        for (int index = 0; index < values.length; index++) {
            System.out.printf("%d. %s%n", index + 1, values[index].getLabel());
        }
        try {
            int choice = ConsoleUtils.readInt(CHOICE);
            if (invalidIndex(choice, values.length)) {
                return promptChoice(values, header);
            }
            return values[choice - 1];
        } catch (NumberFormatException exception) {
            System.out.println(INVALID);
            return promptChoice(values, header);
        }

    }


    public static <T> void printIndexed(List<T> items, Function<T, String> labeler) {
        for (int i = 0; i < items.size(); i++) {
            System.out.printf("%d. %s%n", i + 1, labeler.apply(items.get(i)));
        }
    }

    public static void safeRun(Runnable task) {
        try {
            task.run();
        } catch (IllegalArgumentException illegalArgumentException) {
            System.out.println(INPUT_ERROR + illegalArgumentException.getMessage());
        } catch (Exception exception) {
            System.out.println(UNEXPECTED_ERROR + exception.getMessage());
        }
    }


    public static boolean invalidIndex(int choice, int size) {
        if (choice < 1 || choice > size) {
            System.out.println("\n" + INVALID);
            return true;
        }
        return false;
    }

    public static void pause() {
        ConsoleUtils.readLine(PRESS_RETURN);
    }

    public static <T> T withTry(Supplier<T> s) {
        try {
            return s.get();
        } catch (NumberFormatException e) {
            System.out.println(INVALID);
            throw e;
        }
    }

    public static int readIntSafe(String prompt) {
        while (true) {
            try {
                return ConsoleUtils.readInt(prompt);
            } catch (NumberFormatException ex) {
                System.out.println("\n" + INVALID);
            }
        }
    }
}
