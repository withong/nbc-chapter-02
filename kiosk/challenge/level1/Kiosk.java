package challenge.level1;

import java.util.List;
import java.util.Scanner;

public class Kiosk {

    private List<Menu> menus;
    private Cart cart;
    private Scanner scanner = new Scanner(System.in);

    Kiosk(List<Menu> menu) {
        this.menus = menu;
        this.cart = new Cart();
    }

    void start() {
        while (true) {
            int maxMenuNumber = menus.size();

            System.out.println("\n☰ 홈");
            // 카테고리 리스트(menus)의 카테고리명 가져와서 메뉴 출력
            for (int i = 0; i < maxMenuNumber; i++) {
                System.out.println(" [" + (i + 1) + "] " + menus.get(i).getCategory());
            }

            if (!cart.isEmpty()) {
                maxMenuNumber++;
                System.out.println(" [" + maxMenuNumber + "] 장바구니");
            }

            System.out.println(" [0] 종료하기");

            // 사용자 입력 값 검증: 정수만 입력 가능
            int userInput = getInteger();
            // 정수가 아닌 값 입력 시 -1 반환됨
            if (userInput == -1) continue;

            // 입력된 정수가 메뉴 번호에 속한 숫자인지 확인
            if (userInput < 0 || userInput > maxMenuNumber) {
                System.out.println(" 올바른 메뉴 번호를 입력하세요.");
                continue;
            }

            // 입력된 정수가 0 이면 프로그램 종료
            if (userInput == 0) {
                System.out.println("\n프로그램을 종료합니다.");
                break;
            }

            if (!cart.isEmpty()) {
                if (userInput == maxMenuNumber) {
                    int result = cart();
                    if (result == 0) continue;
                }
            }

            // 인덱스로 변환하여 주문하기 메서드 호출
            order(userInput - 1);
        }
    }

    private void order(int index) {
        try {
            while (true) {
                Menu menu = menus.get(index);
                List<MenuItem> menuItems = menu.getMenuItems();

                // 메뉴 포맷팅 함수 사용하여 출력
                System.out.println(getDisplayMenu(menu));

                // 사용자 입력 값 검증. -1 반환 시 재입력 요청
                int userInput = getInteger();
                if (userInput == -1) continue;

                // 0 입력 시 뒤로 가기 order() 종료 후 메인 메뉴로 돌아감
                if (userInput == 0) {
                    break;
                }

                if (userInput == 112) { // p
                    index = index - 1;
                    continue;
                } else if (userInput == 110) { // n
                    index = index + 1;
                    continue;
                } else if (userInput == 99) { // c
                    if (!cart.isEmpty()) {
                        int result = cart();
                        if (result == 0) break;
                    } else {
                        throw new ArrayIndexOutOfBoundsException();
                    }
                }

                // 입력된 값이 메뉴 번호에 속한 숫자인지 확인
                if (userInput < 0 || userInput > menuItems.size()) {
                    System.out.println(" 올바른 메뉴 번호를 입력하세요.");
                    continue;
                }

                // 유효한 입력 시 인덱스로 변환하여 해당 메뉴 아이템 가져옴
                MenuItem menuItem = menuItems.get(userInput - 1);

                // 장바구니에 추가
                cart.addCartItem(menuItem);
            }
        } catch (ArrayIndexOutOfBoundsException e) {
            System.out.println(" 올바른 메뉴를 입력하세요.");
        }
    }

    private int cart() {
        if (cart.isEmpty()) {
            System.out.println("\n 장바구니에 담긴 상품이 없습니다.");
            return 0;
        }

        while (true) {
            System.out.println(getDisplayCartItems());
            System.out.println("--------------------------------------");
            System.out.println(" [1] 결제하기");
            System.out.println(" [2] 메뉴 삭제하기");
            System.out.println(" [3] 장바구니 비우기");
            System.out.println(" [0] 홈으로");

            int input = getInteger();

            switch (input) {
                case 1 -> {
                    int result = pay();
                    if (result == 0) return 0;
                }
                case 2 -> {
                    System.out.println("\n 삭제할 메뉴의 번호를 입력하세요.");
                    input = getInteger();

                    int cartSize = cart.getCartItems().size();
                    if (input < 1 || input > cartSize) {
                        System.out.println(" 올바른 메뉴 번호를 입력하세요.");
                        continue;
                    }

                    CartItem cartItem = cart.getCartItems().get(input - 1);
                    int quantity = cartItem.getQuantity();

                    if (quantity > 1) {
                        System.out.println("\n [" + cartItem.getMenuItem().getName() + "] 현재 수량 " + quantity + "개");
                        System.out.println(" 삭제할 수량을 입력하세요.");
                        input = getInteger();

                        if (input > quantity) {
                            System.out.println(" 수량보다 큰 숫자를 입력할 수 없습니다.");
                            continue;
                        }
                        cartItem.setQuantity(quantity - input);

                        if (quantity - input < 1 || cartItem.getQuantity() < 1) {
                            cart.getCartItems().remove(cartItem);
                        }
                    } else {
                        cart.getCartItems().remove(cartItem);
                    }
                }
                case 3 -> {
                    cart.clearCart();
                    return 0;
                }
                case 0 -> {
                    return 0;
                }
            }
        }
    }

    private int pay() {
        while (true) {
            int price = cart.getTotalPrice();
            // ￦ 6,500 형태로 가격 포맷팅
            String displayPrice = String.format("￦ %,d", price);

            // 결제 메뉴 출력
            System.out.println("\n☰ 홈 > 결제");
            System.out.println(" 총 결제 금액 [ " + displayPrice + " ]");
            System.out.println(" 지불할 금액을 입력하세요. (취소: 0)");

            // 사용자 입력 값 검증
            int input = getInteger();

            // 숫자가 아닌 값 입력 시 재입력 요청
            if (input == -1) continue;

            // 0 입력 시 홈으로
            if (input == 0) {
                System.out.println(" 결제가 취소되었습니다.");
                return 0;
            }
            // 총 금액 가져오기

            if (input < price) {
                System.out.println(" 결제 오류! 잔액이 부족합니다.");
                continue;
            } else if (input > price) {
                System.out.println(String.format("\n [잔액] ￦ %,d", (input - price)));
            }
            System.out.println(" 결제가 완료되었습니다. 감사합니다.");
            cart.clearCart();

            return 0;
        }
    }

    // 사용자 입력 값 검증 (정수만 입력 가능. 잘못된 입력 시 오류 메시지 출력 후 -1 반환)
    private int getInteger() {
        System.out.print(" ⮞ ");
        String input = scanner.nextLine().trim();

        if (input.isEmpty()) { // 공백 입력 방지
            System.out.println(" 입력된 값이 없습니다.");
            return -1;
        }

        try {
            if (input.equals("p") || input.equals("n") || input.equals("c")) {
                return input.charAt(0);
            } else {
                return Integer.parseInt(input);
            }
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

        sb.append("\n☰ 홈 > " + category + "\n");

        for (int i = 0; i < menuItems.size(); i++) {
            MenuItem item = menuItems.get(i);

            String menuName = getDisplayName(item.getName());
            String formatPrice = String.format("￦ %,d", item.getPrice());
            sb.append(" [" + (i + 1) + "] " + menuName + "\t|  " + formatPrice + "   |   " + item.getDesc() + "\n");
        }
        sb.append("\n [0] 홈으로");

        int index = menus.indexOf(menu);

        if (index == 0) {
            sb.append("    [n] 다음 메뉴");
        } else if (index == (menus.size() - 1)) {
            sb.append("    [p] 이전 메뉴");
        } else if (0 < index || index < (menus.size() - 1)) {
            sb.append("    [p] 이전 메뉴    [n] 다음 메뉴");
        }
        if (!cart.isEmpty()) {
            sb.append("    [c] 장바구니");
        }

        return sb.toString();
    }

    private String getDisplayCartItems() {
        StringBuilder sb = new StringBuilder();
        List<CartItem> cartItems = cart.getCartItems();

        sb.append("\n☰ 홈 > 장바구니\n");
        sb.append("--------------------------------------\n");

        for (int i = 0; i < cartItems.size(); i++) {
            CartItem item = cartItems.get(i);

            String itemName = getDisplayName(item.getMenuItem().getName());
            String formatPrice = String.format("￦ %,d", item.getTotalPrice());
            sb.append(" [" + (i + 1) + "] " + itemName + "\t×  " + item.getQuantity() + "  =  " + formatPrice + "\n");
        }

        String formatTotalPrice = String.format("￦ %,d", cart.getTotalPrice());

        sb.append("--------------------------------------\n");
        sb.append(" 총 결제 금액\t\t\t\t\t " + formatTotalPrice);

        return sb.toString();
    }

    // 메뉴 이름을 한글 기준으로 정렬 후 공백을 포함한 문자열을 반환
    private String getDisplayName(String menuName) {
        int maxLength = 0; // 최대 길이 계산

        for (int i = 0; i < menus.size(); i++) {
            Menu menu = menus.get(i);
            List<MenuItem> menuItem = menu.getMenuItems();

            for (MenuItem item : menuItem) {
                maxLength = Math.max(maxLength, getDisplayLength(item.getName()));
            }
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