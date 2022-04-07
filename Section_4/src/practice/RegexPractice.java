package Section_4.src.practice;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegexPractice {

    public static void main(String[] args) {

        System.out.println("Cat".matches("[cCbB ]at"));
        System.out.println("-at".matches("\\wat"));

        String phoneNumber = "(321) 111-1111";
        String regex =  """ 
                        # This is my regex to parse the parts of a phone number
                        (?:(?<countryCode>\\d)[-.,_\\s]*)?  # Get's country code
                        (?:(?<areaCode>\\(?\\d{3})[-.,_)\\s]*)   # Get's area code
                        (?:(?<exchange>\\d{3})[-.,_\\s]*)   # Get's exchange
                        (?<lineNumber>\\d{3,})              # Get's line number
                        """;
        System.out.println(phoneNumber.matches(regex));

        Pattern pat = Pattern.compile(regex, Pattern.COMMENTS);
        Matcher mat = pat.matcher(phoneNumber);

        if (mat.matches()) {
            System.out.format("Area code: %s\n", mat.group("areaCode"));
            System.out.format("Exchange: %s\n", mat.group("exchange"));
            System.out.format("Line number: %s\n", mat.group("lineNumber"));
            System.out.println(mat.group(0));
        }

        System.out.println("doggy".matches("^.....$"));
        System.out.println("cat doggy".matches("...\\s\\b....."));
    }
}
