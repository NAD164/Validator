import java.util.ArrayList;
import java.util.List;

public class CoordinationNumber implements IValidator {

    @Override
    public void validate(String cNumber, List<String> failedChecksC) {
        String digitsOnly = formatString(cNumber);

        int checkDigit = calculateChecksum(digitsOnly);

        if ((checkDigit == 0 || checkDigit == Character.getNumericValue(digitsOnly.charAt(9)))
                && validateCnumberDate(digitsOnly)) {
            System.out.println("Coordination number " + cNumber + " succeeded the following checks: Luhn's Algorithm," +
                    " Coordination number Date");
        } else {
            System.out.println("Coordination number " + cNumber + " failed the following checks: Luhn's Algorithm, " +
                    "Coordination numberr Date");
            failedChecksC.add(digitsOnly);
        }

    }


    @Override
    public String formatString(String input) {

        String digitsOnly = input.replaceAll("[^0-9]", "");

        if (digitsOnly.length() > 10) {
            digitsOnly = digitsOnly.substring(2);
        }
        return digitsOnly;
    }


    @Override
    public int calculateChecksum(String cNumber) {

        int sum = 0;

        for (int j = 0; j < cNumber.length() - 1; j += 2) {

            int product = Character.getNumericValue(cNumber.charAt(j)) * 2;

            if (product >= 10) {
                sum += product % 10 + product / 10;
            } else {
                sum += product;
            }
        }

        for (int j = 1; j < cNumber.length() - 2; j += 2) {

            sum += Character.getNumericValue(cNumber.charAt(j));
        }


        return (10 - (sum % 10)) % 10;

    }

    private boolean validateCnumberDate(String cNumber) {
        int date = Integer.parseInt(cNumber.substring(4, 6));

        if (date >= 61 && date <= 91) {
            return true;
        }
        return false;
    }

    public static void main(String[] args) {
        String[] array = {"19091079-9824","19091019-9823","19091029-4824"};
        CoordinationNumber CNumValidator = new CoordinationNumber();
        List<String> failedChecksC = new ArrayList<>();

        for (String number : array) {
            CNumValidator.validate(number, failedChecksC);
        }
        System.out.println("Failed checks: " + failedChecksC);

    }


}
