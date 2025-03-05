package calculator.level3.ui;

import calculator.level3.exception.CalculatorException;
import calculator.level3.model.CalculationRecord;
import calculator.level3.model.Operand;
import calculator.level3.model.Operator;
import calculator.level3.service.ArithmeticCalculator;

import java.math.BigDecimal;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class InputValidator {
    private final Scanner scanner;

    public InputValidator(Scanner scanner) {
        this.scanner = scanner;
    }

    public int getValidInt() {
        while (true) {
            try {
                System.out.println("-------------------------------------");
                System.out.print("* 메뉴 번호를 입력하세요: ");
                String input = scanner.nextLine().trim();

                if (input.isEmpty()) {
                    throw new CalculatorException(CalculatorException.Type.EMPTY_VALUE);
                }

                try {
                    return Integer.parseInt(input);

                } catch (NumberFormatException e) {
                    throw new CalculatorException(CalculatorException.Type.INVALID_NUMBER);
                }
            } catch (CalculatorException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    public Operand<?> getValidNumber(String message) {
        while (true) {
            try {
                System.out.print(message);
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
                System.out.print("* 사용할 연산자를 입력하세요: ");
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

    public List<CalculationRecord> selectRecordsWhere(ArithmeticCalculator calculator) {
        List<CalculationRecord> records = calculator.getRecords();

        while (true) {
            System.out.println("-------------------------------------");
            System.out.println("[1] 보다 큰 값 검색하기");
            System.out.println("[2] 보다 작은 값 검색하기");
            System.out.println("[3] 일치하는 값 검색하기");
            System.out.println("-------------------------------------");
            System.out.print("* 메뉴 번호를 입력하세요. [취소: enter] ");
            String selectType = scanner.nextLine().trim();

            // 엔터 시(취소) 초기 메뉴로 돌아감
            if (selectType.isEmpty()) {
                System.out.println("-------------------------------------");
                return null;
            }

            int select;
            try {
                try { // 숫자로 변환 시도
                    select = Integer.parseInt(selectType);

                } catch (NumberFormatException e) {
                    throw new CalculatorException(CalculatorException.Type.INVALID_NUMBER);
                }

                // 메뉴 중에 있는 번호인지 확인
                try {
                    if (select < 1 || select > 3) {
                        throw new CalculatorException(CalculatorException.Type.INVALID_NUMBER);
                    }
                } catch (CalculatorException e) {
                    System.out.println(e.getMessage());
                    continue;
                }

                Operand<?> reference = getValidNumber("* 기준 값을 입력하세요: ");

                BigDecimal referenceValue = toBigDecimal(reference).stripTrailingZeros();

                List<CalculationRecord> filteredRecords = records.stream()
                        .filter(record -> {
                            BigDecimal resultValue = toBigDecimal(record.getResult()).stripTrailingZeros();

                            return switch (select) {
                                case 1 -> resultValue.compareTo(referenceValue) > 0; // 기준 값보다 큼
                                case 2 -> resultValue.compareTo(referenceValue) < 0; // 기준 값보다 작음
                                case 3 -> resultValue.compareTo(referenceValue) == 0; // 기준 값과 일치함
                                default -> false;
                            };
                        }).collect(Collectors.toList());

                return filteredRecords;

            } catch (CalculatorException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    public void selectRecordToRemove(ArithmeticCalculator calculator) {
        List<CalculationRecord> records = calculator.getRecords();

        while (true) {
            if (records.isEmpty()) {
                System.out.println("***** 삭제할 연산 기록이 없습니다. *****");
                System.out.println("-------------------------------------");
                return;
            }

            // 목록 보여주기
            System.out.println("-------------------------------------");
            for (int i = 0; i < records.size(); i++) {
                System.out.println("[" + (i + 1) + "] " + records.get(i).toString());
            }
            System.out.println("-------------------------------------");

            System.out.println("* 삭제할 기록의 번호를 입력하세요.");
            System.out.print("[취소: enter][전체: all 입력] ");
            String input = scanner.nextLine().trim();

            // 엔터 시(취소) 초기 메뉴로 돌아감
            if (input.isEmpty()) {
                System.out.println("-------------------------------------");
                return;
            }

            // all 입력 시 전체 삭제
            if (input.equals("all")) {
                calculator.clearRecords();
                System.out.println("***** 계산 기록이 모두 삭제되었습니다. *****");
                System.out.println("-------------------------------------");
                return;
            }

            try {
                int recordNumber;
                // 숫자로 변환 시도
                try {
                    recordNumber = Integer.parseInt(input);
                } catch (NumberFormatException e) {
                    throw new CalculatorException(CalculatorException.Type.INVALID_NUMBER);
                }

                // 목록 중에 있는 번호인지 확인
                if (recordNumber < 1 || recordNumber > records.size()) {
                    throw new CalculatorException(CalculatorException.Type.INVALID_NUMBER);
                }

                // 목록 중에 있는 번호면 인덱스로 변환
                int recordsIndex = (recordNumber - 1);
                // 기록 삭제
                calculator.removeRecord(recordsIndex);
                // 삭제 결과 출력
                System.out.println("***** 계산 기록이 삭제되었습니다. *****");
                System.out.println("-------------------------------------");

                return;

            } catch (CalculatorException e) {
                // 숫자가 아닌 값 입력 시 오류 메시지 출력 후 재입력 요청
                System.out.println(e.getMessage());
            }
        }
    }

    private boolean isNumber(String str) {
        try {
            new BigDecimal(str.trim());
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    private BigDecimal toBigDecimal(Operand<?> operand) {
        try {
            return new BigDecimal(operand.getOperand().toString().trim()).stripTrailingZeros();
        } catch (NumberFormatException e) {
            throw new CalculatorException(CalculatorException.Type.INVALID_NUMBER);
        }
    }
}
