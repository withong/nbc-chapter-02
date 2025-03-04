package calculator.level3.model;

public class Operand<T> {

    private final T operand;

    public Operand(T operand) {
        this.operand = operand;
    }

    public T getOperand() {
        return operand;
    }

    @Override
    public String toString() {
        return operand.toString();
    }
}
