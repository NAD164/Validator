import java.util.ArrayList;
import java.util.List;

public class OrganisationNumber implements IValidator {

    @Override
    public void validate(String orgNumber, List<String> failedChecksOrg) {
        String digitsOnly = formatString(orgNumber);
        int checkDigit = calculateChecksum(digitsOnly);

        if ((checkDigit == 0 || checkDigit == Character.getNumericValue(digitsOnly.charAt(9)))
                && middleDigits(digitsOnly)) {
            System.out.println("Personnummer " + orgNumber + " succeeded the following checks: Luhn's Algorithm");
        } else {
            System.out.println("Personnummer " + orgNumber + " failed the following checks: Luhn's Algorithm");
            failedChecksOrg.add(digitsOnly);
        }


    }

    @Override
    public int calculateChecksum(String orgNumber) {

        int sum = 0;

        for (int j = 0; j < orgNumber.length() - 1; j += 2) {

            int product = Character.getNumericValue(orgNumber.charAt(j)) * 2;

            if (product >= 10) {
                sum += product % 10 + product / 10;
            } else {
                sum += product;
            }
        }

        for (int j = 1; j < orgNumber.length() - 2; j += 2) {

            sum += Character.getNumericValue(orgNumber.charAt(j));
        }

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

    private boolean middleDigits(String orgNumber) {

        int middle2 = Integer.parseInt(orgNumber.substring(2,4));

        if (middle2 >= 20) {
            return true;
        }
        return false;
    }

    public static void main(String[] args) {

        String[] array = {"556614-3185", "16556601-6399", "262000-1111", "857202-7566"};
        OrganisationNumber OrgNumValidator = new OrganisationNumber();
        List<String> failedChecksOrg = new ArrayList<>();

        for (String number : array) {
            OrgNumValidator.validate(number, failedChecksOrg);
        }
        System.out.println("Failed checks: " + failedChecksOrg);

    }

}
