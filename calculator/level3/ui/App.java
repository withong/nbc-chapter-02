package calculator.level3.ui;

import calculator.level3.exception.CalculatorException;
import calculator.level3.model.CalculationRecord;
import calculator.level3.model.Operand;
import calculator.level3.model.Operator;
import calculator.level3.service.ArithmeticCalculator;

import java.util.List;
import java.util.Scanner;

public class App {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        InputValidator validator = new InputValidator(scanner);
        ArithmeticCalculator calculator = new ArithmeticCalculator();
        List<CalculationRecord> records = calculator.getRecords();

        System.out.println("--------- 계산기 Version 3.0 ---------");

        while (true) {
            try {
                System.out.println("[1] 연산하기");
                System.out.println("[2] 연산 기록 조회하기");
                System.out.println("[3] 연산 기록 삭제하기");
                System.out.println("[4] 연산 결과 검색하기");
                System.out.println("[5] 종료하기");

                // 메뉴 선택 - 입력 값 검증
                int input = validator.getValidInt();

                switch (input) {
                    case 1 -> {
                        System.out.println("-------------------------------------");
                        // 숫자 2개, 연산자 입력받기 - 입력 값 검증
                        Operand<?> number1 = validator.getValidNumber("* 첫 번째 숫자를 입력하세요: ");
                        Operator operator = validator.getValidOperator();
                        Operand<?> number2 = validator.getValidNumber("* 두 번째 숫자를 입력하세요: ");

                        // 연산 수행 결과
                        Operand<?> calculated = calculator.calculate(number1, operator, number2);

                        System.out.println("-------------------------------------");
                        System.out.println("[ " + number1 + " " + operator + " " + number2 + " = " + calculated + " ]");
                        System.out.println("-------------------------------------");
                    }
                    case 2 -> {
                        if (records.isEmpty()) {
                            System.out.println("***** 최근 연산 기록이 없습니다. *****");
                            System.out.println("-------------------------------------");
                            break;
                        }

                        System.out.println("-------------------------------------");
                        for (int i = 0; i < records.size(); i++) {
                            System.out.println("[" + (i + 1) + "] " + records.get(i).toString());
                        }
                        System.out.println("-------------------------------------");
                    }
                    case 3 -> {
                        validator.selectRecordToRemove(calculator);
                    }
                    case 4 -> {
                        if (records.isEmpty()) {
                            System.out.println("***** 검색할 연산 기록이 없습니다. *****");
                            System.out.println("-------------------------------------");
                            break;
                        }

                        List<CalculationRecord> filteredRecords = validator.selectRecordsWhere(calculator);

                        if (filteredRecords == null) {
                            break; // 취소
                        }

                        if (filteredRecords.isEmpty()) {
                            System.out.println("***** 검색 결과가 없습니다. *****");
                            System.out.println("-------------------------------------");
                            break;
                        }

                        System.out.println("-------------------------------------");
                        for (int i = 0; i < filteredRecords.size(); i++) {
                            System.out.println("[" + (i + 1) + "] " + filteredRecords.get(i).toString());
                        }
                        System.out.println("-------------------------------------");
                    }
                    case 5 -> {
                        System.out.println("------------- 계산기 종료 -------------");
                        return;
                    }
                    default -> throw new CalculatorException(CalculatorException.Type.INVALID_NUMBER);
                }
            } catch (CalculatorException e) {
                System.out.println(e.getMessage());
                System.out.println("-------------------------------------");
            }
        }
    }
}
