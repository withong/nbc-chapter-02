package calculator.level1;

import java.util.Scanner;

public class Calculator {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("--------- 계산기 Version 1.0 ---------");

        while (true) {
            System.out.println("-------------------------------------");
            // 사용자 입력 값 받기 ( 숫자 → 연산자 → 숫자 )
            int number1 = getUserNumber(scanner, "첫 번째 숫자를 입력하세요: ");
            char operator = getUserOperator(scanner);
            int number2 = getUserNumber(scanner, "두 번째 숫자를 입력하세요: ");

            // 계산하기
            int result = calculate(number1, number2, operator);

            // 결과 메시지 ( 부적절한 연산일 경우 값에 Error 출력 )
            String resultMessage = getResultMessage(number1, number2, operator, result);

            // 결과 출력
            System.out.println(resultMessage);

            // 종료 여부 확인
            if (isEnd(scanner)) {
                System.out.println("------------- 계산기 종료 -------------");
                break;
            }
        }
    }

    public static int getUserNumber(Scanner scanner, String message) {
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

    public static char getUserOperator(Scanner scanner) {
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

    public static int calculate(int number1, int number2, char operator) {
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
                    }
                    return number1 / number2;
                default:
                    handleError("invalidOperatorError");
            }
        } catch (ArithmeticException e) {
            handleError("excessNumberError");
        }
        return -9999;
    }

    public static void handleError(String error) {
        String errorMessage = "[Error] ";

        switch (error) {
            case "divideByZeroError":
                errorMessage += "나눗셈 연산에서 분모가 0이 될 수 없습니다.";
                break;
            case "invalidOperatorError":
                errorMessage += "지원하지 않는 연산자입니다.";
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

    public static boolean isEnd(Scanner scanner) {
        System.out.print("종료하시겠습니까? [종료: exit] ");
        String userEnd = scanner.nextLine();

        if (userEnd.equals("exit")) {
            return true;
        }
        return false;
    }

    public static String getResultMessage(int number1, int number2, char operator, int result) {
        if (result == -9999) {
            return "[ " + number1 + " " + operator + " " + number2 + " = Error ]\n-------------------------------------";
        }
        return "[ " + number1 + " " + operator + " " + number2 + " = " + result + " ]\n-------------------------------------";
    }
}
