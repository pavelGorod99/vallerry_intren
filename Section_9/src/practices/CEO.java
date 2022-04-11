package Section_9.src.practices;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CEO extends Employee implements Flyer {

    private int avgStockPrice;
    private Flyer flyer = new Pilot(1000, true);

    String ceoRegex = "\\w+=(?<avgStockPrice>\\w+)";
    Pattern ceoPat = Pattern.compile(ceoRegex);

    public CEO(String peopleText) {

        super(peopleText);
        Matcher ceoMat = ceoPat.matcher(mat.group("details"));

        if (ceoMat.find())
            avgStockPrice = Integer.parseInt(ceoMat.group("avgStockPrice"));
    }

    public int getSalary() {
        return 5000 * avgStockPrice;
    }

    public void fly() {
        flyer.fly();
    }

    public int getHoursFlown() {
        return flyer.getHoursFlown();
    }

    public void setHoursFlown(int hoursFlown) {
        flyer.setHoursFlown(hoursFlown);
    }

    public boolean isIfr() {
        return flyer.isIfr();
    }

    public void setIfr(boolean ifr) {
        flyer.setIfr(ifr);
    }
}
