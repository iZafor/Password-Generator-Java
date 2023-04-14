package z.code.passwordmanager;

import java.util.Random;

public class PasswordManager implements IPasswordManger {
    private final String digits = "0123456789";
    private final String lowers = "abcdefghijklmnopqrstuvwxyz";
    private final String uppers = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    private final String symbols = "()!@#$%[]{}<>;:";
    private final Random random;

    public PasswordManager() {
        random = new Random();
    }

    private String getAPart(String mainString, int length) {
        StringBuilder builder = new StringBuilder();
        while (length-- != 0) {
            builder.append(mainString.charAt(
                    random.nextInt(0, mainString.length())
            ));
        }
        return builder.toString();
    }

    private void shuffle(StringBuilder builder) {
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

    public String generatePassword(int digits, int uppers, int lowers, int symbols) {
        StringBuilder builder = new StringBuilder();
        builder.append(getAPart(this.digits, digits));
        builder.append(getAPart(this.uppers, uppers));
        builder.append(getAPart(this.lowers, lowers));
        builder.append(getAPart(this.symbols, symbols));

        shuffle(builder);
        return builder.toString();
    }
}