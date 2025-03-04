package calculator.level3.ui;

import calculator.level3.exception.CalculatorException;
import calculator.level3.model.Operand;
import calculator.level3.model.Operator;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.Scanner;

public class InputValidator {
    private final Scanner scanner;

    public InputValidator(Scanner scanner) {
        this.scanner = scanner;
    }

    public int getValidInt() {
        while (true) {
            try {
                System.out.print("메뉴 번호를 입력하세요: ");
                String input = scanner.nextLine().trim();

                if (input.isEmpty()) {
                    throw new CalculatorException(CalculatorException.Type.EMPTY_VALUE);
                }

                if (!input.matches("\\d+")) {
                    throw new CalculatorException(CalculatorException.Type.INVALID_NUMBER);
                }

                return Integer.parseInt(input);

            } catch (CalculatorException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    public Operand<?> getValidNumber() {
        while (true) {
            try {
                System.out.print("숫자를 입력하세요: ");
                String input = scanner.nextLine().trim();

                if (input.isEmpty()) {
                    throw new CalculatorException(CalculatorException.Type.EMPTY_VALUE);
                }

                if (!isNumber(input)) {
                    throw new CalculatorException(CalculatorException.Type.INVALID_NUMBER);
                }

                BigDecimal bigDecimal = new BigDecimal(input);
                return new Operand<>(bigDecimal);

            } catch (CalculatorException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    public Operator getValidOperator() {
        while (true) {
            try {
                System.out.print("연산자를 입력하세요: ");
                String input = scanner.nextLine().trim();

                if (input.isEmpty()) {
                    throw new CalculatorException(CalculatorException.Type.EMPTY_VALUE);
                }

                for (Operator operator : Operator.values()) {
                    if (operator.toString().equals(input)) {
                        return operator;
                    }
                }
                throw new CalculatorException(CalculatorException.Type.INVALID_OPERATOR);

            } catch (CalculatorException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    private boolean isNumber(String str) {
        return str.matches("-?\\d+(\\.\\d+)?");
    }

    private Operand<?> convertToType(String input) {
        String toString = String.valueOf(input);
        Double toDouble = Double.parseDouble(toString);

        DecimalFormat decimalFormat = new DecimalFormat("#.########");
        String result = decimalFormat.format(toDouble);

        System.out.println("convertToType result = " + result);

        if (result.contains(".")) {
            return new Operand<>(Double.parseDouble(result));  // 소수점이 있으면 Double로 변환
        } else {
            long longValue = Long.parseLong(result);
            if (longValue <= Integer.MAX_VALUE && longValue >= Integer.MIN_VALUE) {
                return new Operand<>((int) longValue);  // int 범위면 int로 변환

            } else if (longValue <= Long.MAX_VALUE && longValue >= Long.MIN_VALUE) {
                return new Operand<>(longValue);  // long 범위면 long

            } else {
                double doubleValue = Double.parseDouble(result);  // long 초과하면 double로 변환
                return new Operand<>(removeZeroes(doubleValue));
            }
        }
    }

    private double removeZeroes(double value) {
        if (value == Math.floor(value)) {
            return (long) value; // 정수 형태로 유지
        }
        return value; // 소수 형태 유지
    }
}
