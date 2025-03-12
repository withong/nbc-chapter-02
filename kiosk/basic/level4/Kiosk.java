package basic.level4;

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
            // 카테고리 리스트(menus)의 카테고리명 가져와서 메뉴 출력
            for (int i = 0; i < menus.size(); i++) {
                System.out.println(" [" + (i + 1) + "] " + menus.get(i).getCategory());
            }
            System.out.println(" [0] 종료하기");

            // 사용자 입력 값 검증: 정수만 입력 가능
            int userInput = getInteger();
            // 정수가 아닌 값 입력 시 -1 반환됨
            if (userInput == -1) continue;

            // 입력된 정수가 메뉴 번호에 속한 숫자인지 확인
            if (userInput < 0 || userInput > menus.size()) {
                System.out.println(" 올바른 메뉴 번호를 입력하세요.");
                continue;
            }

            // 입력된 정수가 0 이면 프로그램 종료
            if (userInput == 0) {
                System.out.println("\n프로그램을 종료합니다.");
                break;
            }

            // 유효한 입력 시 인덱스로 변환하여 해당 카테고리 객체 가져옴
            Menu userChoice = menus.get(userInput - 1);
            // 주문하기 메서드로 해당 객체 전달하여 주문 진행
            order(userChoice);
        }
    }

    private void order(Menu userChoice) {
        // 전달받은 Menu 객체를 사용하여 상세 메뉴 정보 리스트 가져옴
        List<MenuItem> menuItems = userChoice.getMenuItems();

        while (true) {
            // 메뉴 포맷팅 함수 사용하여 출력
            System.out.println(getDisplayMenu(userChoice));

            // 사용자 입력 값 검증. -1 반환 시 재입력 요청
            int userInput = getInteger();
            if (userInput == -1) continue;

            // 0 입력 시 뒤로 가기 order() 종료 → start() 함수 내 while문으로 돌아감
            if (userInput == 0) {
                break;
            }

            // 입력된 값이 메뉴 번호에 속한 숫자인지 확인
            if (userInput < 0 || userInput > menuItems.size()) {
                System.out.println(" 올바른 메뉴 번호를 입력하세요.");
                continue;
            }

            // 유효한 입력 시 인덱스로 변환하여 해당 메뉴 아이템 가져옴
            MenuItem menuItem = menuItems.get(userInput - 1);
            // 결제하기 메서드로 해당 객체 전달하여 결제 진행
            int result = pay(menuItem);
            // 0 반환 시 order() 종료 → start() 함수 내 while문으로 돌아감
            if (result == 0) break;
        }
    }

    private int pay(MenuItem menuItem) {
        while (true) {
            // 전달받은 메뉴 아이템에서 메뉴명 가져옴
            String menuItemName = menuItem.getName();
            // 전달받은 메뉴 아이템에서 메뉴 가격 가져옴
            int price = menuItem.getPrice();
            // ￦ 6,500 형태로 가격 포맷팅
            String displayPrice = String.format("￦ %,d", price);
            // 결제 여부 확인
            System.out.println("\n☰ 메뉴 > 결제");
            System.out.println(" [ " + menuItemName + " ] " + displayPrice);
            System.out.println(" 결제하시겠습니까? (예: 1, 아니오: 2)");
            // 사용자 입력 값 검증
            int input = getInteger();

            switch (input) {
                case 1 -> {
                    // 지불 금액 입력받기
                    System.out.println("\n (결제중...) 금액을 입력하세요.");
                    // 사용자 입력 값 검증
                    int amount = getInteger();

                    if (amount < price) {
                        // 입력 값이 메뉴의 가격보다 작으면 메시지 출력 후 결제 여부 확인으로 돌아감
                        System.out.println(" 금액이 부족합니다.");
                        continue;
                    } else if (amount > price) {
                        // 입력 값이 메뉴의 가격보다 크면 잔액 출력 후 return 0 되어 초기 메뉴로 돌아감
                        System.out.println(String.format("\n [잔액] ￦ %,d", (amount - price)));
                    }
                    // 입력 값이 메뉴 가격보다 크거나 같으면 실행됨
                    // 결제 완료 메시지 출력 후 return 0 하여 start() 함수 내 while문으로 돌아감
                    System.out.println(" 결제가 완료되었습니다. 감사합니다.");
                    return 0; // 0:홈
                }
                case 2 -> {
                    // 결제 여부 확인에서 아니오(2) 입력 시 초기 메뉴로 돌아감
                    System.out.println(" 결제가 취소되었습니다.");
                    return 0;
                }
                // 정수가 아닌 값 입력 시 getInteger()에서 오류 메시지 출력 후 break → 결제 여부 재확인
                case -1 -> {}
                // 예(1), 아니오(2)가 아닌 값 입력 시 안내 메시지 출력 후 break → 결제 여부 재확인
                default -> System.out.println(" 올바른 메뉴 번호를 입력하세요.");
            }
        }
    }

    // 사용자 입력 값 검증 (정수만 입력 가능. 잘못된 입력 시 오류 메시지 출력 후 -1 반환)
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

    // 카테고리별 메뉴 정보 포맷팅 후 출력
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

    // 메뉴 이름을 한글 기준으로 정렬 후 공백을 포함한 문자열을 반환
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