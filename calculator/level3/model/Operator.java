package level3.model;

import level3.exception.CalculatorException;

import java.math.BigDecimal;
import java.math.RoundingMode;

public enum Operator {
    ADD("+") {
        @Override
        public BigDecimal apply(Operand<?> number1, Operand<?> number2) {
            BigDecimal num1 = number1.toBigDecimal();
            BigDecimal num2 = number2.toBigDecimal();

            return num1.add(num2);
        }
    },
    SUBTRACT("-") {
        @Override
        public BigDecimal apply(Operand<?> number1, Operand<?> number2) {
            BigDecimal num1 = number1.toBigDecimal();
            BigDecimal num2 = number2.toBigDecimal();

            return num1.subtract(num2);
        }
    },
    MULTIPLY("*") {
        @Override
        public BigDecimal apply(Operand<?> number1, Operand<?> number2) {
            BigDecimal num1 = number1.toBigDecimal();
            BigDecimal num2 = number2.toBigDecimal();

            return num1.multiply(num2);
        }
    },
    DIVIDE("/") {
        @Override
        public BigDecimal apply(Operand<?> number1, Operand<?> number2) {
            BigDecimal num1 = number1.toBigDecimal();
            BigDecimal num2 = number2.toBigDecimal();

            if (num2.signum() == 0) {
                throw new CalculatorException(CalculatorException.Type.DIVISION_BY_ZERO);
            }

            return num1.divide(num2, 8, RoundingMode.HALF_UP);
        }
    };

    private final String symbol;

    Operator(String symbol) {
        this.symbol = symbol;
    }

    public abstract BigDecimal apply(Operand<?> number1, Operand<?> number2);

    @Override
    public String toString() {
        return symbol;
    }
}
