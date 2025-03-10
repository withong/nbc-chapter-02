package level3.model;

import level3.exception.CalculatorException;

import java.math.BigDecimal;

public class Operand<T> {

    private final T operand;

    public Operand(T operand) {
        this.operand = operand;
    }

    public T getOperand() {
        return operand;
    }

    public BigDecimal toBigDecimal() {
        if (operand instanceof BigDecimal bigDecimal) {
            return bigDecimal;
        }
        try {
            return new BigDecimal(operand.toString());

        } catch (NumberFormatException e) {
            throw new CalculatorException(CalculatorException.Type.INVALID_NUMBER);
        }
    }

    @Override
    public String toString() {
        return operand.toString();
    }
}
