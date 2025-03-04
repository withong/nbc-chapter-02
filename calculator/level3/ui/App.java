package calculator.level3.ui;

import calculator.level3.exception.CalculatorException;
import calculator.level3.model.Operand;
import calculator.level3.model.Operator;
import calculator.level3.service.ArithmeticCalculator;

import java.util.Scanner;

public class App {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        InputValidator validator = new InputValidator(scanner);
        ArithmeticCalculator calculator = new ArithmeticCalculator();

        while (true) {
            try {
                System.out.println("[1] 연산 수행하기");
                System.out.println("[2] 최근 기록 조회하기");
                System.out.println("[3] 연산 기록 삭제하기");
                System.out.println("[4] 연산 결과 비교하기");
                System.out.println("[5] 종료하기");

                int input = validator.getValidInt();

                switch (input) {
                    case 1 -> {
                        Operand<?> number1 = validator.getValidNumber();
                        Operator operator = validator.getValidOperator();
                        Operand<?> number2 = validator.getValidNumber();

                        Operand<?> result = calculator.calculate(number1, operator, number2);
                        System.out.println("[ " + number1 + " " + operator + " " + number2 + " = " + result + " ]");
                        break;
                    }
                    case 2 -> {
                        System.out.println("최근 기록 조회하기");
                        break;
                    }
                    case 3 -> {
                        System.out.println("연산 기록 삭제하기");
                        break;
                    }
                    case 4 -> {
                        System.out.println("연산 결과 비교하기");
                        break;
                    }
                    case 5 -> {
                        System.out.println("종료하기");
                        return;
                    }
                    default -> {
                        throw new CalculatorException(CalculatorException.Type.INVALID_NUMBER);
                    }
                }
            } catch (CalculatorException e) {
                System.out.println(e.getMessage());
            }
        }
    }
}
