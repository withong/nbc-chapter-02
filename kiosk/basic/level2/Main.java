package basic.level2;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        List<MenuItem> menuItems = new ArrayList<>();
        menuItems.add(new MenuItem("불고기 버거", 4200, "불고기 소스에 패티와 마요네즈, 양상추의 맛있는 조합."));
        menuItems.add(new MenuItem("치즈 버거", 3600, "맛있는 치즈와 100% 순 쇠고기 패티, 클래식 치즈 버거."));
        menuItems.add(new MenuItem("슈비 버거", 6600, "탱글한 통새우살에 비프 패티를 더해 푸짐한 슈비 버거."));
        menuItems.add(new MenuItem("슈슈 버거", 5500, "탱글한 통새우살이 가득한 슈슈 버거."));

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

            MenuItem userChoice = menuItems.get(userInput-1);
            String userChoiceName = userChoice.getName();
            int userChoicePrice = userChoice.getPrice();
            String formatPrice = String.format("￦ %,d", userChoicePrice);

            System.out.println("\n[" + userChoiceName + "] " + formatPrice);
            userInput = getInteger(scanner, "결제하시겠습니까? [예: 1, 아니오: 2] ");

            switch (userInput) {
                case 1 -> {
                    userInput = getInteger(scanner, "\n지불할 금액을 입력하세요: ");

                    if (userInput > userChoicePrice) {
                        System.out.println(String.format("\n[잔액] ￦ %,d", (userInput - userChoicePrice)));

                    } else if (userInput < userChoicePrice) {
                        System.out.println("금액이 부족합니다. 결제가 취소됩니다.");
                        break;
                    }

                    System.out.println("결제가 완료되었습니다. 감사합니다.");
                }

                case 2 -> System.out.println("결제가 취소되었습니다.");

                default -> System.out.println("올바른 입력이 아닙니다. 결제가 취소됩니다.");
            }
        }
    }

    public static int getInteger(Scanner scanner, String prompt) {
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
    public static String getDisplayBurgerName(List<MenuItem> menuItems, String menuName) {
        int maxLength = 0; // 최대 길이 계산
        for (MenuItem item : menuItems) {
            maxLength = Math.max(maxLength, getDisplayLength(item.getName()));
        }

        int paddingSize = maxLength - getDisplayLength(menuName); // 부족한 공백 개수 계산
        return menuName + " ".repeat(paddingSize); // 버거명 + 공백 리턴
    }

    // 한글을 2칸, 영어/숫자를 1칸으로 계산하는 메서드
    public static int getDisplayLength(String text) {
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
