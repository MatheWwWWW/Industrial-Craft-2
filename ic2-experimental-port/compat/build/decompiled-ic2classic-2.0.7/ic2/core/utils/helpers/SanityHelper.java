/*
 * Decompiled with CFR 0.152.
 */
package ic2.core.utils.helpers;

public class SanityHelper {
    public static boolean check(CharSequence s) {
        return s == null || s.length() == 0 || s.charAt(0) <= ' ' || s.charAt(s.length() - 1) <= ' ';
    }

    public static String firstLetterUppercase(String string) {
        if (string == null || string.isEmpty()) {
            return string;
        }
        String first = Character.toString(string.charAt(0));
        return string.replaceFirst(first, first.toUpperCase());
    }

    public static String toPascalCase(String input) {
        StringBuilder builder = new StringBuilder();
        for (String s : input.replaceAll("_", " ").split(" ")) {
            builder.append(SanityHelper.firstLetterUppercase(s)).append(" ");
        }
        return builder.substring(0, builder.length() - 1);
    }
}

