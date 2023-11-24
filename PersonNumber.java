import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class PersonNumber implements IValidator {


    @Override
    public void validate(String personNumber, List<String> failedChecks) {

        String digitsOnly = formatString(personNumber);
        int checkDigit = calculateChecksum(digitsOnly);

        if (checkDigit == 0 || checkDigit == Character.getNumericValue(digitsOnly.charAt(9))) {
            if (validateDate(digitsOnly)) {
                System.out.println("Personnummer " + personNumber + " succeeded the following checks: Luhn's Algorithm");
            } else {
                System.out.println("Personnummer " + personNumber + " failed date validation but passed Luhn's Algorithm");
                failedChecks.add(digitsOnly);
            }
        } else {
            System.out.println("Personnummer " + personNumber + " failed the following checks: Luhn's Algorithm, and/or Date invalid");
            failedChecks.add(digitsOnly);
        }


    }


    @Override
    public int calculateChecksum(String personNumber) {

        int sum = 0;


        for (int j = 0; j < personNumber.length() - 1; j += 2) {

            int product = Character.getNumericValue(personNumber.charAt(j)) * 2;

            if (product >= 10) {
                sum += product % 10 + product / 10;
            } else {
                sum += product;
            }
        }

        for (int j = 1; j < personNumber.length() - 2; j += 2) {

            sum += Character.getNumericValue(personNumber.charAt(j));
        }

        // Beräkna kontrollsiffran
        return (10 - (sum % 10)) % 10;
    }


    @Override
    public String formatString(String input) {

        String digitsOnly = input.replaceAll("[^0-9]", "");


        if (digitsOnly.length() > 10) {
            digitsOnly = digitsOnly.substring(2);
        }
        return digitsOnly;
    }


    private boolean validateDate(String personnummer) {
        String dateString = personnummer.substring(0, 6);

        SimpleDateFormat sdf = new SimpleDateFormat("yyMMdd");
        sdf.setLenient(false);
        try {
            Date d1 = sdf.parse(dateString);

        } catch (ParseException e) {

            return false;
        }
        return true;

    }

    public static void main(String[] args) {
        String[] array = {"201701102384", "141206-2380", "20080903-2386", "7101169295",
                "198107249289", "198107249289", "19021214-9819", "190910199827", "191006089807",
                "192109099180", "4607137454", "194510168885", "900118+9811", "189102279800", "189912299816", "201701272394", "190302299813"};
        List<String> failedChecks = new ArrayList<>();

        PersonNumber personNumberValidator = new PersonNumber();

        for (String personnummer : array) {
            personNumberValidator.validate(personnummer, failedChecks);
        }

        System.out.println("Failed checks: " + failedChecks);
    }


}
