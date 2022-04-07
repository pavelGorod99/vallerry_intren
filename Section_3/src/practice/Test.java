package Section_3.src.practice;

public class Test {

    public static void main(String[] args) {
        String s1 = "abc";
        String s2 = "bcd";
        String s3 = "abg";
        String s4 = "ABC";

        System.out.println(s1.compareTo(s2));
        System.out.println(s1.compareTo(s3));
        System.out.println(s1.compareToIgnoreCase(s4));


        // indexOf()

        String phoneNumber = "(234) 333-5551";
        String areaCode = "";
        String exchange = "";
        String lineNumber = "";

        System.out.println(
                "areaCode: " + parseAreaCode(phoneNumber) + "\n" +
                "exchange: " + parseExchange(phoneNumber) + "\n" +
                "lineNumber: " + parseLineNumber(phoneNumber)
        );
    }

    public static String parseAreaCode(String phoneNumber) {
        return phoneNumber.substring(phoneNumber.indexOf("(") + 1, phoneNumber.indexOf(")"));
    }

    public static String parseExchange(String phoneNumber) {
        return phoneNumber.substring(phoneNumber.indexOf(" "), phoneNumber.indexOf("-"));
    }

    public static String parseLineNumber(String phoneNumber) {
        return phoneNumber.substring(phoneNumber.indexOf("-") + 1);
    }
}
