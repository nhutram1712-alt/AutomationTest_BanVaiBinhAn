package Common;

public class Utilities {
    public static String generateRandomString(int length) {
        String chars = "abcdefghijklmnopqrstuvwxyz";
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < length; i++) {
            int index = (int)(Math.random() * chars.length());
            sb.append(chars.charAt(index));
        }
        return sb.toString();
    }

    public static String generateRandomPhone() {
        return "09" + (long)(Math.random() * 100000000L + 10000000L);
    }
}
