package z.code.passwordmanager;

import java.util.Random;

public class PasswordUtility {
    private static final String digitChars = "0123456789";
    private static final String lowerChars = "abcdefghijklmnopqrstuvwxyz";
    private static final String upperChars = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    private static final String symbolChars = "()!@#$%[]{}<>;:";
    private static final Random random = new Random();

    private PasswordUtility() {

    }

    private static String buildAPart(String mainString, int length) {
        StringBuilder builder = new StringBuilder();
        while (length-- != 0) {
            builder.append(mainString.charAt(
                    random.nextInt(0, mainString.length())
            ));
        }
        return builder.toString();
    }

    private static void shuffle(StringBuilder builder) {
        int length = builder.length() / 2;

        while (length-- != 0) {
            int idx1 = random.nextInt(0, builder.length());
            int idx2 = random.nextInt(0, builder.length());

            char c1 = builder.charAt(idx1);
            char c2 = builder.charAt(idx2);

            builder.setCharAt(idx1, c2);
            builder.setCharAt(idx2, c1);
        }
    }

    public static String generatePassword(int digits, int uppers, int lowers, int symbols) {
        StringBuilder builder = new StringBuilder();
        builder.append(buildAPart(digitChars, digits));
        builder.append(buildAPart(upperChars, uppers));
        builder.append(buildAPart(lowerChars, lowers));
        builder.append(buildAPart(symbolChars, symbols));

        shuffle(builder);
        return builder.toString();
    }
}