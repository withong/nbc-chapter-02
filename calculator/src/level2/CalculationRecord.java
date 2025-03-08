package level2;

public class CalculationRecord {

    private final int number1;
    private final int number2;
    private final char operator;
    private final String result;

    CalculationRecord(int number1, char operator, int number2, String result) {
        this.number1 = number1;
        this.operator = operator;
        this.number2 = number2;
        this.result = result;
    }

    @Override
    public String toString() {
        return "[ " + number1 + " " + operator + " " + number2 + " = " + result + " ]";
    }
}
