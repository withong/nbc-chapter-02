package level3.model;

public class CalculationRecord {

    private final Operand<?> number1;
    private final Operand<?> number2;
    private final Operator operator;
    private final Operand<?> result;

    public CalculationRecord(Operand<?> number1, Operator operator, Operand<?> number2, Operand<?> result) {
        this.number1 = number1;
        this.operator = operator;
        this.number2 = number2;
        this.result = result;
    }

    @Override
    public String toString() {
        return "[ " + number1 + " " + operator + " " + number2 + " = " + result + " ]";
    }

    public Operand<?> getResult() {
        return result;
    }
}
