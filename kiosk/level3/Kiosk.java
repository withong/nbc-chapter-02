package level3;

import java.util.List;
import java.util.Scanner;

public class Kiosk {

    private List<MenuItem> menuItems;

    Kiosk(List<MenuItem> menuItems) {
        this.menuItems = menuItems;
    }

    void start() {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("\n[ MENU - BURGER ]");

            for (int i = 0; i < menuItems.size(); i++) {
                MenuItem menu = menuItems.get(i);
                String menuName = getDisplayBurgerName(menuItems, menu.getName());
                String formatPrice = String.format("￦ %,d", menu.getPrice());

                System.out.println((i + 1) + ". " + menuName + "\t| " + formatPrice + " | " + menu.getDesc());
            }
            System.out.println("0. 종료하기");

            int userInput = getInteger(scanner, "\n주문할 메뉴의 번호를 입력하세요: ");

            if (userInput < 0 || userInput > menuItems.size()) {
                System.out.println("올바른 메뉴 번호를 입력하세요.");
                continue;
            }

            if (userInput == 0) {
                System.out.println("프로그램을 종료합니다.");
                break;
            }

            MenuItem userChoice = menuItems.get(userInput - 1);
            String userChoiceName = userChoice.getName();
            int userChoicePrice = userChoice.getPrice();
            String formatPrice = String.format("￦ %,d", userChoicePrice);

            userInput = getInteger(scanner, "\n[" + userChoiceName + "] " + formatPrice + "\n결제하시겠습니까? [예: 1, 아니오: 2] ");

            if (userInput == 1) {
                int input = getInteger(scanner, "\n지불할 금액을 입력하세요: ");

                if (input > userChoicePrice) {
                    System.out.println(String.format("\n[잔액] ￦ %,d", (input - userChoicePrice)));

                } else if (input < userChoicePrice) {
                    System.out.println("금액이 부족합니다. 결제가 취소됩니다.");
                    continue;
                }
                System.out.println("결제가 완료되었습니다. 감사합니다.");

            } else if (userInput == 2) {
                System.out.println("결제가 취소되었습니다.");
            } else {
                System.out.println("올바른 입력이 아닙니다. 결제가 취소됩니다.");
            }
        }
    }

    private static int getInteger(Scanner scanner, String prompt) {
        while (true) {
            System.out.print(prompt);
            String input = scanner.nextLine();
            try {
                return Integer.parseInt(input);
            } catch (NumberFormatException e) {
                System.out.println("숫자만 입력 가능합니다.");
            }
        }
    }

    // 메뉴 이름을 한글 기준으로 정렬 후 공백을 포함한 문자열을 반환하는 메서드
    private static String getDisplayBurgerName(List<MenuItem> menuItems, String menuName) {
        int maxLength = 0; // 최대 길이 계산
        for (MenuItem item : menuItems) {
            maxLength = Math.max(maxLength, getDisplayLength(item.getName()));
        }

        int paddingSize = maxLength - getDisplayLength(menuName); // 부족한 공백 개수 계산
        return menuName + " ".repeat(paddingSize); // 버거명 + 공백 리턴
    }

    // 한글을 2칸, 영어/숫자를 1칸으로 계산하는 메서드
    private static int getDisplayLength(String text) {
        int length = 0;
        for (char c : text.toCharArray()) {
            if (Character.toString(c).matches("[가-힣]")) {
                length += 2; // 한글이면 2칸
            } else {
                length += 1; // 영어/숫자/기타 문자는 1칸
            }
        }
        return length;
    }
}
