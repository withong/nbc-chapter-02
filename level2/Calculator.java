package calculator.level2;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Scanner;

public class Calculator {

    private List<CalculationRecord> history;

    public Calculator() {
        history = new ArrayList<>();
    }

    public int setUserNumber(Scanner scanner, String message) {
        while (true) {
            System.out.print(message);
            // 엔터만 입력했을 때도 인지하기 위해 nextLine()으로 받음
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
            // 연산자가 아닐 경우 오류 메시지 출력 후 재 입력받기
            handleError("invalidOperatorError");
        }
    }

    public double calculate(int number1, int number2, char operator) {
        try {
            switch (operator) {
                case '+':
                    return Math.addExact(number1, number2); // int 범위 초과 감지
                case '-':
                    return Math.subtractExact(number1, number2); // int 범위 초과 감지
                case '*':
                    return Math.multiplyExact(number1, number2); // int 범위 초과 감지
                case '/':
                    if (number2 == 0) {
                        handleError("divideByZeroError");
                        return -9999;
                    }
                    return (double) number1 / number2;
                default:
                    handleError("invalidOperatorError");
            }
        } catch (ArithmeticException e) {
            handleError("excessNumberError");
        }
        return -9999;
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
        }
        System.out.println(errorMessage);
    }

    public void printResultMessage(int number1, int number2, char operator, double result) {
        DecimalFormat decimalFormat = new DecimalFormat("#.########"); // 불필요한 0 자동 제거
        String formatResult = decimalFormat.format(result);

        // 현재 계산을 기록에 추가
        history.add(new CalculationRecord(number1, operator, number2, formatResult));

        System.out.println("-------------------------------------");
        if (result == -9999) {
            System.out.println("[ " + number1 + " " + operator + " " + number2 + " = Error ]");
        } else {
            System.out.println("[ " + number1 + " " + operator + " " + number2 + " = " + formatResult + " ]");
        }
        System.out.println("-------------------------------------");

        // 목록은 10개까지 저장하므로 11개가 되면 가장 처음 저장한 기록 삭제
        if (history.size() == 11) {
            history.remove(0);
        }
    }

    public void showHistory() {
        if (!isHistoryEmpty()) {

            System.out.println("-------------------------------------");
            for (int i = 0; i < history.size(); i++) {
                System.out.println("[" + (i + 1) + "] " + history.get(i).toString());
            }
            System.out.println("-------------------------------------");
        }
    }

    public void removeHistory(Scanner scanner) {
        if (!isHistoryEmpty()) {
            // 목록 보여주기
            showHistory();
            // 계산 기록의 번호 목록을 가져옴
            List<Integer> historynumbers = getHistoryNumbers();
            // 계산 기록의 마지막 번호를 가져옴
            int forMatchNumber = historynumbers.get(historynumbers.size()-1);


            while (true) {
                System.out.println("삭제할 기록의 번호를 입력하세요.");
                System.out.print("[취소: enter][전체: all 입력] ");
                String userNumber = scanner.nextLine().trim();

                if (userNumber.isEmpty()) {
                    System.out.println("-------------------------------------");
                    return;
                }

                // all 입력 시 전체 삭제
                if (userNumber.equals("all")) {
                    history.clear();
                    System.out.println("계산 기록이 모두 삭제되었습니다.");
                    System.out.println("-------------------------------------");
                    return;
                }
                // 번호 목록에 있는 숫자인지 확인
                if (!userNumber.matches("^[1-"+forMatchNumber+"]$")) {
                    System.out.println("잘못된 입력입니다. 다시 입력하세요.");
                    System.out.println("-------------------------------------");
                    continue;
                }

                // 목록 중에 있는 번호면 인덱스로 변환
                int historyIndex = Integer.parseInt(userNumber) - 1;
                // 삭제
                history.remove(historyIndex);
                // 삭제 결과 출력
                System.out.println("계산 기록이 삭제되었습니다.");
                System.out.println("-------------------------------------");

                return;
            }
        }
    }

    private boolean isHistoryEmpty() {
        if (history.isEmpty()) {
            System.out.println("계산 기록이 없습니다.");
            return true;
        } else {
            return false;
        }
    }

    private List<Integer> getHistoryNumbers() {
        List<Integer> historyNumbers = new ArrayList<>();

        if (!isHistoryEmpty()) {
            for (int i = 0; i < history.size(); i++) {
                historyNumbers.add(i + 1);
            }
        }
        return historyNumbers;
    }
}
