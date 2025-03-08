package level1;

import java.util.List;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        List<List<String>> menu = List.of(
                List.of("불고기 버거", "￦ 4,200", "불고기 소스에 패티와 마요네즈, 양상추의 맛있는 조합."),
                List.of("치즈 버거", "￦ 3,600", "맛있는 치즈와 100% 순 쇠고기 패티, 클래식 치즈 버거."),
                List.of("슈비 버거", "￦ 6,600", "탱글한 통새우살에 비프 패티를 더해 푸짐한 슈비 버거."),
                List.of("슈슈 버거", "￦ 5,500", "탱글한 통새우살이 가득한 슈슈 버거.")
        );

        while (true) {
            System.out.println("\n[ MENU - BURGER ]");

            for (int i = 0; i < menu.size(); i++) {
                List<String> burger = menu.get(i);
                String burgerName = getDisplayBurgerName(menu, burger.get(0));
                System.out.println((i + 1) + ". " + burgerName + "\t| " + burger.get(1) + " | " + burger.get(2));
            }
            System.out.println("0. 종료하기");
            System.out.print("\n주문할 메뉴의 번호를 입력하세요: ");
            String input = scanner.nextLine();

            int user;
            if (!isInteger(input)) {
                continue;
            }
            user = Integer.parseInt(input);

            if (user < 0 || user > menu.size()) {
                System.out.println("올바른 메뉴 번호를 입력하세요.");
                continue;
            }

            if (user == 0) {
                System.out.println("프로그램을 종료합니다.");
                break;
            }

            List<String> userChoice = menu.get(user-1);
            String userBurgerName = userChoice.get(0);
            String userBugerPrice = userChoice.get(1);

            System.out.println("\n[" + userBurgerName + "] " + userBugerPrice);
            System.out.print("결제하시겠습니까? [예: 1, 아니오: 2] ");
            input = scanner.nextLine();

            if (!isInteger(input)) {
                continue;
            }
            user = Integer.parseInt(input);

            if (user == 2) {
                continue;
            } else if (user == 1) {
                System.out.print("\n지불할 금액을 입력하세요: ");
                input = scanner.nextLine();

                if (!isInteger(input)) {
                    continue;
                }
                user = Integer.parseInt(input);

                String numericPrice = userBugerPrice.replace("￦", "").replace(",", "").trim();
                int price = Integer.parseInt(numericPrice);

                if (user > price) {
                    System.out.println(String.format("[잔액] ￦ %,d", (user - price)));
                } else if (user < price) {
                    System.out.println("금액이 부족합니다. 결제가 취소됩니다.");
                    continue;
                }

                System.out.println("결제가 완료되었습니다. 감사합니다.");
            }
        }
    }

    public static boolean isInteger(String input) {
        try {
            Integer.parseInt(input);
        } catch (NumberFormatException e) {
            System.out.println("숫자만 입력 가능합니다.\n");
            return false;
        }
        return true;
    }

    // 메뉴 이름을 한글 기준으로 정렬 후 공백을 포함한 문자열을 반환하는 메서드
    public static String getDisplayBurgerName(List<List<String>> menu, String burgerName) {
        int maxLength = 0; // 최대 길이 계산
        for (List<String> item : menu) {
            maxLength = Math.max(maxLength, getDisplayLength(item.get(0)));
        }

        int paddingSize = maxLength - getDisplayLength(burgerName); // 부족한 공백 개수 계산
        return burgerName + " ".repeat(paddingSize); // 버거명 + 공백 리턴
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
