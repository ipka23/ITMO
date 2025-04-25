import java.security.SecureRandom;
import java.sql.*;


public class Test2 {
    private String chrs = "0123456789abcdefghijklmnopqrstuvwxyz-_ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    private SecureRandom secureRandom = new SecureRandom();
    private String customTag = secureRandom
            .ints(12, 0, chrs.length()) // 9 is the length of the string you want
            .mapToObj(i -> chrs.charAt(i))
            .collect(StringBuilder::new, StringBuilder::append, StringBuilder::append)
            .toString();
    public static void main(String[] args) {
        System.out.println(new Test2().customTag);
    }
}
