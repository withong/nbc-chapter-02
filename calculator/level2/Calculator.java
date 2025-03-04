package calculator.level2;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Calculator {
/*
    이 'records' 필드는 private으로 감춰져 있습니다.
    별도의 getRecords(), setRecords() 기능을 제공하지 않고,
    showRecords(), showLastRecord(), removeRecords(), updateRecords() 메서드를 통해서만
    기록을 조회/삭제/추가하도록 구현하여 캡슐화를 강화했습니다.
*/
    private List<CalculationRecord> records;

    public Calculator() {
        records = new ArrayList<>();
    }

    public int setUserNumber(Scanner scanner, String message) {
        while (true) {
            System.out.print(message);
            // 엔터만 입력했을 때도 인식하기 위해 nextLine()으로 받음
            String userNumber = scanner.nextLine().trim();

            // 입력 값이 빈 값인지 확인
            if (userNumber.isEmpty()) {
                handleError("emptyInputError");
                continue;
            }
            // 입력 값이 0 이상의 정수인지 확인
            if (!userNumber.matches("\\d+")) {
                handleError("decimalNumberError");
                continue;
            }
            // int 범위 내에서 변환 가능 여부 확인
            try {
                int number = Integer.parseInt(userNumber);
                return number;
            } catch (NumberFormatException e) {
                handleError("excessNumberError"); // int 범위 초과
            }
        }
    }

    public char setUserOperator(Scanner scanner) {
        while (true) {
            System.out.print("사용할 연산자를 입력하세요: ");

            String userOperator = scanner.nextLine();
            String operators = "+-*/";

            // 입력 값이 한 글자인지 확인
            boolean isLetter = userOperator.length() == 1;
            // 입력 값이 연산자인지 확인
            boolean isOperator = isLetter && operators.contains(userOperator);

            // 연산자가 맞을 경우 반환
            if (isOperator) {
                return userOperator.charAt(0);
            }
            // 연산자가 아닐 경우 오류 메시지 출력 후 재 입력 요청
            handleError("invalidOperatorError");
        }
    }

    public String calculate(int number1, int number2, char operator) {
        try {
            switch (operator) {
                case '+':
                    return String.valueOf(Math.addExact(number1, number2)); // int 범위 초과 감지
                case '-':
                    return String.valueOf(Math.subtractExact(number1, number2));
                case '*':
                    return String.valueOf(Math.multiplyExact(number1, number2));
                case '/':
                    if (number2 == 0) {
                        handleError("divideByZeroError");
                        return "정의되지 않음";
                    }
                    return String.valueOf((double) number1 / number2);
                default:
                    handleError("invalidOperatorError");
                    return "Error";
            }
        } catch (ArithmeticException e) {
            handleError("excessNumberError");
            return "연산 범위 초과";
        }
    }

    private void handleError(String error) {
        String errorMessage = "[Error] ";

        switch (error) {
            case "divideByZeroError":
                errorMessage += "나눗셈 연산에서 분모가 0이 될 수 없습니다.";
                break;
            case "invalidOperatorError":
                errorMessage += "잘못된 입력이거나 지원하지 않는 연산자입니다.";
                break;
            case "emptyInputError":
                errorMessage += "값이 입력되지 않았습니다.";
                break;
            case "excessNumberError":
                errorMessage += "연산 가능한 범위의 숫자를 초과했습니다.";
                break;
            case "decimalNumberError":
                errorMessage += "0 이상의 정수만 입력할 수 있습니다.";
                break;
            case "invalidRecordsNumberError":
                errorMessage += "잘못된 입력입니다. 올바른 번호를 입력하세요.";
                break;
        }
        System.out.println(errorMessage);
    }

    public void updateRecords(int number1, int number2, char operator, String result) {
        String formattedResult = "";
        // 숫자가 아닌 경우 오류 메시지 그대로 저장
        if (!result.matches("-?\\d+(\\.\\d+)?")) {
            formattedResult = result;
        } else {
            // 입력 내용이 숫자인 경우 double로 변환
            double StringToDouble = Double.parseDouble(result);
            // 계산 결과 최대 소수점 8자리까지 반올림, 소수점 이하가 0일 경우 정수 형태
            DecimalFormat decimalFormat = new DecimalFormat("#.########");
            formattedResult = decimalFormat.format(StringToDouble);
        }

        // 현재 계산을 기록에 추가
        records.add(new CalculationRecord(number1, operator, number2, formattedResult));

        // 목록 관리 (10개 유지)
        trimRecords();
    }

    private void trimRecords() {
        // 목록은 10개까지 저장하므로 10개를 초과하면 가장 오래된 기록 삭제
        while (records.size() > 10) {
            records.remove(0);
        }
    }

    public void showRecords() {
        if (records.isEmpty()) {
            System.out.println("조회할 기록이 없습니다.");
            System.out.println("-------------------------------------");
            return;
        }
        // 최근 연산 기록 최대 10개까지 출력
        System.out.println("-------------------------------------");
        for (int i = 0; i < records.size(); i++) {
            System.out.println("[" + (i + 1) + "] " + records.get(i).toString());
        }
        System.out.println("-------------------------------------");
    }

    public void showLastRecord() {
        if (records.isEmpty()) {
            System.out.println("[Error] 계산 기록이 없습니다.");
            return;
        }
        // 마지막 연산 기록 출력
        System.out.println("-------------------------------------");
        System.out.println(records.get(records.size()-1).toString());
        System.out.println("-------------------------------------");
    }

    public void removeRecords(Scanner scanner) {
        if (records.isEmpty()) {
            System.out.println("삭제할 기록이 없습니다.");
            System.out.println("-------------------------------------");
            return;
        }

        while (true) {
            // 목록 보여주기
            showRecords();

            System.out.println("삭제할 기록의 번호를 입력하세요.");
            System.out.print("[취소: enter][전체: all 입력] ");
            String userNumber = scanner.nextLine().trim();

            // 빈 값 입력 시(취소) 초기 메뉴로 돌아감
            if (userNumber.isEmpty()) {
                System.out.println("-------------------------------------");
                return;
            }

            // all 입력 시 전체 삭제
            if (userNumber.equals("all")) {
                records.clear();
                System.out.println("계산 기록이 모두 삭제되었습니다.");
                System.out.println("-------------------------------------");
                return;
            }

            try { // 숫자로 변환 시도
                int recordNumber = Integer.parseInt(userNumber);

                // 목록 중에 있는 번호인지 확인
                if (recordNumber < 1 || recordNumber > records.size()) {
                    handleError("invalidRecordsNumberError");
                    continue;
                }

                // 목록 중에 있는 번호면 인덱스로 변환
                int recordsIndex = (recordNumber - 1);
                // 기록 삭제
                records.remove(recordsIndex);
                // 삭제 결과 출력
                System.out.println("계산 기록이 삭제되었습니다.");
                System.out.println("-------------------------------------");

                return;

            } catch (NumberFormatException e) {
                // 숫자가 아닌 값 입력 시 오류 메시지 출력 후 재입력 요청
                handleError("invalidRecordsNumberError");
                continue;
            }
        }
    }
}