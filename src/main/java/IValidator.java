import java.util.List;

public interface IValidator {
    void validate(String number, List<String> failedChecks);
    int calculateChecksum(String digitsOnly);
    String formatString(String input);

}
