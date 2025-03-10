package done;

import java.util.List;
import java.util.Scanner;

public class Kiosk {

    private List<Menu> menus;
    private Scanner scanner = new Scanner(System.in);

    Kiosk(List<Menu> menu) {
        this.menus = menu;
    }

    void start() {
        while (true) {

            System.out.println("\n☰ 메뉴");
            for (int i = 0; i < menus.size(); i++) {
                System.out.println(" [" + (i + 1) + "] " + menus.get(i).getCategory());
            }

            System.out.println(" [0] 종료하기");

            int userInput = getInteger();
            if (userInput == -1) continue;

            if (userInput < 0 || userInput > menus.size()) {
                System.out.println(" 올바른 메뉴 번호를 입력하세요.");
                continue;
            }

            if (userInput == 0) {
                System.out.println("\n프로그램을 종료합니다.");
                break;
            }

            Menu userChoice = menus.get(userInput - 1);
            order(userChoice);
        }
    }

    private void order(Menu userChoice) {
        List<MenuItem> menuItems = userChoice.getMenuItems();

        while (true) {
            System.out.println(getDisplayMenu(userChoice));

            int userInput = getInteger();
            if (userInput == -1) continue;

            if (userInput == 0) {
                break;
            }

            if (userInput < 0 || userInput > menuItems.size()) {
                System.out.println(" 올바른 메뉴 번호를 입력하세요.");
                continue;
            }

            MenuItem menuItem = menuItems.get(userInput - 1);
            int result = pay(menuItem);
            if (result == 0) break;
        }
    }

    private int pay(MenuItem menuItem) {
        while (true) {
            String menuItemName = menuItem.getName();
            int price = menuItem.getPrice();

            String displayPrice = String.format("￦ %,d", price);
            System.out.println("\n☰ 메뉴 > 결제");
            System.out.println(" [ " + menuItemName + " ] " + displayPrice);
            System.out.println(" 결제하시겠습니까? (예: 1, 아니오: 2)");
            int input = getInteger();

            switch (input) {
                case 1 -> {
                    System.out.println("\n (결제중...) 금액을 입력하세요.");
                    int amount = getInteger();

                    if (amount < price) {
                        System.out.println(" 금액이 부족합니다.");
                        continue;
                    } else if (amount > price) {
                        System.out.println(String.format("\n [잔액] ￦ %,d", (amount - price)));
                    }
                    System.out.println(" 결제가 완료되었습니다. 감사합니다.");
                    return 0; // 0:홈
                }
                case 2 -> {
                    System.out.println(" 결제가 취소되었습니다.");
                    return 0;
                }

                case -1 -> {}
                default -> System.out.println(" 올바른 메뉴 번호를 입력하세요.");
            }
        }
    }

    private int getInteger() {
        System.out.print(" ⮞ ");
        String input = scanner.nextLine();
        try {
            return Integer.parseInt(input);
        } catch (NumberFormatException e) {
            System.out.println(" 유효한 입력이 아닙니다.");
            return -1;
        }
    }

    private String getDisplayMenu(Menu menu) {
        StringBuilder sb = new StringBuilder();
        List<MenuItem> menuItems = menu.getMenuItems();
        String category = menu.getCategory();

        sb.append( "\n☰ 메뉴 > " + category + "\n");

        for (int i = 0; i < menuItems.size(); i++) {
            MenuItem item = menuItems.get(i);

            String menuName = getDisplayMenuName(menuItems, item.getName());
            String formatPrice = String.format("￦ %,d", item.getPrice());
            sb.append(" [" + (i + 1) + "] " + menuName + "\t| " + formatPrice + "\t| " + item.getDesc()+ "\n");
        }
        sb.append(" [0] 뒤로 가기");

        return sb.toString();
    }

    // 메뉴 이름을 한글 기준으로 정렬 후 공백을 포함한 문자열을 반환하는 메서드
    private String getDisplayMenuName(List<MenuItem> menuItems, String menuName) {
        int maxLength = 0; // 최대 길이 계산
        for (MenuItem item : menuItems) {
            maxLength = Math.max(maxLength, getDisplayLength(item.getName()));
        }

        int paddingSize = maxLength - getDisplayLength(menuName); // 부족한 공백 개수 계산
        return menuName + " ".repeat(paddingSize); // 버거명 + 공백 리턴
    }

    // 한글을 2칸, 영어/숫자를 1칸으로 계산하는 메서드
    private int getDisplayLength(String text) {
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