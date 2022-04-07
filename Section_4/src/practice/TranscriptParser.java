package Section_4.src.practice;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TranscriptParser {

    public static void main(String[] args) {

        String transcript = """
                Student Number:	1234598872			Grade:		11
                Birthdate:		01/02/2000			Gender:	M
                State ID:		8923827123
                                            
                Cumulative GPA (Weighted)		3.82
                Cumulative GPA (Unweighted)	3.46
                """;

//      Birthdate:\s+(?<studentBirthdate>(\d{1,2}[/]?){2}\d{2,4}).*  # Grab student birth day
        String regex = """
                Student\\sNumber:\\s(?<studentNumber>\\d{10}).*                                                             # Grab student number
                Grade:\\s+(?<studentGrade>\\d{1,2}).*                                                                       # Grab student grade
                Birthdate:\\s+(?<studentBirthdate>(?<birthMonth>\\d{1,2})/(?<birthDay>\\d{1,2})/(?<birthYear>\\d{2,4})).*   # Grab student birth day
                Gender:\\s+(?<studentGender>\\w)\\b.*                                                                       # Grab student gender
                State\\sID:\\s+(?<studentStateID>\\d{10}).*                                                                 # Grab student state ID
                Weighted\\)\\s+(?<studentWeighedGPA>[\\d\\.]+)\\b.*                                                         # Grab student weight GPA
                Unweighted\\)\\s(?<studentUnweightedGPA>[\\d\\.]+)\\b.*                                                      # Grab student unweighted GPA  
                """;

        Pattern pat = Pattern.compile(regex, Pattern.DOTALL | Pattern.COMMENTS);
        Matcher mat = pat.matcher(transcript);

        if (mat.matches()) {
            System.out.println( "Student number: "          + mat.group("studentNumber") + "\n" +
                                "Student grade: "           + mat.group("studentGrade") + "\n" +
                                "Student birthday (mm/dd/yyyy): "   + mat.group("birthMonth") + "/"
                                                                    + mat.group("birthDay") + "/"
                                                                    + mat.group("birthYear") + "\n" +
                                "Student gender: "          + mat.group("studentGender") + "\n" +
                                "Student state ID: "        + mat.group("studentStateID") + "\n" +
                                "Student weighed GPA: "     + mat.group("studentWeighedGPA") + "\n" +
                                "Student unweighted GPA: "  + mat.group("studentUnweightedGPA"));
        }
    }
}
