/*
 * Decompiled with CFR 0.152.
 */
package ic2.core.utils.config.utils;

import ic2.core.utils.config.utils.ParseResult;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.nio.file.attribute.FileAttribute;
import java.util.Arrays;
import java.util.StringJoiner;

public class Helpers {
    private static final String[] EMPTY = new String[0];

    public static boolean validateString(CharSequence s) {
        return s == null || s.length() == 0 || s.charAt(0) <= ' ' || s.charAt(s.length() - 1) <= ' ';
    }

    public static String firstLetterUppercase(String string) {
        if (string == null || string.isEmpty()) {
            return string;
        }
        String first = Character.toString(string.charAt(0));
        return string.replaceFirst(first, first.toUpperCase());
    }

    public static void ensureFolder(Path folder) {
        if (Files.notExists(folder, new LinkOption[0])) {
            try {
                Files.createDirectories(folder, new FileAttribute[0]);
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public static void copyFile(Path from, Path to) {
        Helpers.ensureFolder(to.getParent());
        try {
            Files.copy(from, to, StandardCopyOption.REPLACE_EXISTING);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static String generateIndent(int level) {
        char[] arr = new char[level * 2];
        Arrays.fill(arr, ' ');
        return new String(arr);
    }

    public static String[] validateComments(String[] inputs) {
        if (inputs == null) {
            return null;
        }
        for (int i = 0; i < inputs.length; ++i) {
            if (inputs[i] != null) continue;
            inputs[i] = "";
        }
        return inputs;
    }

    public static String[] splitArray(String value, String splitter) {
        return value.isEmpty() ? EMPTY : Helpers.trimArray(value.split(splitter));
    }

    public static String[] trimArray(String[] array) {
        int m = array.length;
        for (int i = 0; i < m; ++i) {
            array[i] = array[i].trim();
        }
        return array;
    }

    public static String mergeCompound(String[] array) {
        StringJoiner joiner = new StringJoiner(";");
        for (String s : array) {
            joiner.add(s);
        }
        return joiner.toString();
    }

    public static int clamp(int value, int min, int max) {
        return Math.min(Math.max(value, min), max);
    }

    public static double clamp(double value, double min, double max) {
        return Math.min(Math.max(value, min), max);
    }

    public static ParseResult<Integer> parseInt(String value) {
        try {
            return ParseResult.success(Integer.parseInt(value));
        }
        catch (Exception e) {
            return ParseResult.error(value, e, "Couldn't parse Number");
        }
    }

    public static ParseResult<Double> parseDouble(String value) {
        try {
            return ParseResult.success(Double.parseDouble(value));
        }
        catch (Exception e) {
            return ParseResult.error(value, e, "Couldn't parse Number");
        }
    }
}

