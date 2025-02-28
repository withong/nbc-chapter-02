package calculator.level2;

import java.util.Scanner;

public class App {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Calculator calculator = new Calculator();

        System.out.println("--------- 계산기 Version 2.0 ---------");

        while (true) {
            // 메뉴 출력 후 번호 입력받기
            int userChoice = setUserChoice(scanner);

            switch (userChoice) {
                case 1:
                    // 0 이상의 정수 2개와 연산자 입력받기
                    int number1 = calculator.setUserNumber(scanner, "첫 번째 숫자를 입력하세요: ");
                    char operator = calculator.setUserOperator(scanner);
                    int number2 = calculator.setUserNumber(scanner, "두 번째 숫자를 입력하세요: ");

                    // 입력받은 숫자와 연산자를 이용하여 계산
                    double result = calculator.calculate(number1, number2, operator);
                    // 계산 목록에 현재 계산 기록 추가
                    calculator.updateRecords(number1, number2, operator, result);
                    // 마지막 계산 기록 조회
                    calculator.showLastRecord();

                    break;

                case 2:
                    // 최근 계산 기록 조회 (최대 10개)
                    calculator.showRecords();
                    break;

                case 3:
                    // 최근 계산 기록 선택/전체 삭제
                    calculator.removeRecords(scanner);
                    break;

                case 4:
                    System.out.println("------------- 계산기 종료 -------------");
                    return;
            }
        }
    }

    public static int setUserChoice(Scanner scanner) {
        while (true) {
            System.out.println("[1] 사칙연산 계산하기");
            System.out.println("[2] 최근 계산 기록 조회하기");
            System.out.println("[3] 계산 기록 삭제하기");
            System.out.println("[4] 종료하기");
            System.out.print("실행할 메뉴 번호를 입력하세요: ");

            String userNumber = scanner.nextLine().trim();

            if (!userNumber.matches("^[1-4]$")) {
                System.out.println("잘못된 입력입니다. 다시 입력하세요.");
                System.out.println("-------------------------------------");
                continue;
            }
            return Integer.parseInt(userNumber);
        }
    }
}
