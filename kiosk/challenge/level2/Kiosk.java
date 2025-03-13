package challenge.level2;

import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

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

            // 장바구니가 비어 있지 않으면 메뉴 목록에 추가
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

            // 장바구니 메뉴 선택 시 cart() 호출, 0 반환 시 start() 처음으로
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
                // 전달받은 인덱스로 해당 카테고리와 카테고리의 메뉴 목록 가져옴
                Menu menu = menus.get(index);
                List<MenuItem> menuItems = menu.getMenuItems();

                // 메뉴 포맷팅 함수 사용하여 출력
                System.out.println(getDisplayMenu(menu));

                // 사용자 입력 값 검증. -1 반환 시 재입력 요청
                int userInput = getInteger();
                if (userInput == -1) continue;

                // 0 입력 시 order() 종료, start()로 돌아감
                if (userInput == 0) {
                    break;
                }

                // 메뉴 네비게이션 바 입력 값 검사
                if (userInput == 112) { // p 이전 메뉴
                    index = index - 1;
                    continue;

                } else if (userInput == 110) { // n 다음 메뉴
                    index = index + 1;
                    continue;

                } else if (userInput == 99) { // c 장바구니
                    if (cart.isEmpty()) {
                        throw new ArrayIndexOutOfBoundsException();
                    }
                    int result = cart();
                    if (result == 0) break; // 0 반환 시 start()로 돌아감
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
            // 메뉴 네비게이션 바 입력 값 예외 처리: 없는 인덱스거나, 빈 장바구니 호출 시 실행
            System.out.println(" 올바른 메뉴를 입력하세요.");
        }
    }

    private int cart() {
        if (cart.isEmpty()) {
            System.out.println("\n 장바구니에 담긴 상품이 없습니다.");
            return 0;
        }

        while (true) {
            // 장바구니 목록 포맷팅하여 출력
            System.out.println(getDisplayCartItems());
            System.out.println("--------------------------------------");
            System.out.println(" [1] 결제하기");
            System.out.println(" [2] 메뉴 삭제하기");
            System.out.println(" [3] 장바구니 비우기");
            System.out.println(" [0] 홈으로");

            int input = getInteger();

            switch (input) {
                case 1 -> { // 결제하기 선택 시 할인 메서드 호출
                    int result = discount();
                    if (result == 0) return 0;
                }
                case 2 -> { // 삭제하기 선택 시 삭제할 메뉴 번호 입력받음, -1 반환 시 재입력 요청
                    System.out.println("\n 삭제할 메뉴의 번호를 입력하세요.");
                    input = getInteger();
                    if (input == -1) continue;

                    int cartSize = cart.getCartItems().size();

                    // 입력된 값이 메뉴 번호에 속해 있는 숫자인지 확인
                    if (input < 1 || input > cartSize) {
                        System.out.println(" 올바른 메뉴 번호를 입력하세요.");
                        continue;
                    }

                    // 올바른 값이 입력되면 인덱스로 변환하여 선택된 메뉴의 정보 가져옴
                    CartItem cartItem = cart.getCartItems().get(input - 1);
                    // 해당 메뉴의 수량 정보
                    int quantity = cartItem.getQuantity();

                    // 수량이 1보다 크면 삭제할 수량 입력받음
                    if (quantity > 1) {
                        System.out.println("\n [" + cartItem.getMenuItem().getName() + "] 현재 수량 " + quantity + "개");
                        System.out.println(" 삭제할 수량을 입력하세요.");

                        input = getInteger();
                        if (input == -1) continue;

                        // 수량보다 큰 숫자 입력시 재입력 요청
                        if (input > quantity) {
                            System.out.println(" 수량보다 큰 숫자를 입력할 수 없습니다.");
                            continue;
                        }

                        // 올바른 값 입력시 수량 세팅
                        cartItem.setQuantity(quantity - input);

                        // 수량이 0 이상인 경우만 리스트에 추가
                        cart.setCartItems(cart.getCartItems().stream()
                                .filter(item -> item.getQuantity() > 0)
                                .collect(Collectors.toList()));
                    } else {
                        // 수량이 1개면 바로 삭제
                        cart.setCartItems(cart.getCartItems().stream()
                                .filter(item -> !item.equals(cartItem))
                                .collect(Collectors.toList()));
                    }
                }
                case 3 -> {
                    // 장바구니 비우기 선택시 리스트를 비우고 0 반환
                    cart.clearCart();
                    return 0;
                }
                case 0 -> {
                    // 홈으로 선택시 0 반환
                    return 0;
                }
            }
        }
    }

    private int discount() {
        while (true) {
            // 장바구니에 들어 있는 메뉴들의 총 가격 정보
            int price = cart.getTotalPrice();
            // 할인 정보 목록
            Discount[] discounts = Discount.values();
            // 해당 없음을 포함한 마지막 메뉴 번호
            int lastNumber = Discount.values().length + 1;

            // 할인 메뉴 출력
            System.out.println(getDisplayDiscount());

            // 사용자 입력 값 검증
            int input = getInteger();
            // -1 반환 시 재입력 요청
            if (input == -1) continue;

            // 홈으로
            if (input == 0) {
                System.out.println(" 결제가 취소되었습니다.");
                return 0;
            }

            // 메뉴에 속한 숫자인지 확인
            if (input < 1 || input > lastNumber) {
                System.out.println(" 올바른 메뉴 번호를 입력하세요.");
                continue;
            }

            // 해당 없음 선택시 현재 가격 그대로 결제 메서드 호출
            if (input == lastNumber) {
                pay(price);
            }

            // 할인 대상이면 할인 적용한 가격 담아서 결제하기 호출
            int discountedPrice = discounts[input-1].getDiscounted(price);
            int result = pay(discountedPrice);

            // 0 반환 시 0 반환, start()로 돌아감
            if (result == 0) return 0;
        }
    }

    private int pay(int price) {
        while (true) {
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

            if (input < price) {
                System.out.println(" 결제 오류! 잔액이 부족합니다.");
                continue;
            } else if (input > price) {
                System.out.println(String.format("\n [잔액] ￦ %,d", (input - price)));
            }
            System.out.println(" 결제가 완료되었습니다. 감사합니다.");
            // 결제 완료 시 장바구니 비우기
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

        try { // 네비게이션 바 메뉴
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
        List<MenuItem> menuItems = menu.getMenuItems();
        String category = menu.getCategory();

        String menuList = menuItems.stream()
                .map(item -> String.format(" [%d] %s\t|  ￦ %,d   |   %s",
                        menuItems.indexOf(item) + 1,
                        getDisplayName(item.getName()),
                        item.getPrice(),
                        item.getDesc()))
                .collect(Collectors.joining("\n"));

        int index = menus.indexOf(menu);
        String navigation = "\n [0] 홈으로";

        // 메뉴 네비게이션 바 조건: 첫 번째 메뉴면 다음 메뉴만, 마지막 메뉴면 이전 메뉴만, 아니면 모두 출력
        if (index == 0) {
            navigation += "    [n] 다음 메뉴";
        } else if (index == (menus.size() - 1)) {
            navigation += "    [p] 이전 메뉴";
        } else if (0 < index || index < (menus.size() - 1)) {
            navigation += "    [p] 이전 메뉴    [n] 다음 메뉴";
        }
        // 장바구니가 비어 있지 않을 때만 네비게이션 바에 출력
        if (!cart.isEmpty()) {
            navigation += "    [c] 장바구니";
        }

        return "\n☰ 홈 > " + category
                + "\n" + menuList
                + navigation;
    }

    private String getDisplayCartItems() {
        List<CartItem> cartItems = cart.getCartItems();

        String line ="--------------------------------------\n";

        String cartList = cartItems.stream()
                .filter(item -> item.getQuantity() > 0)
                .map(item -> String.format(" [%d] %s\t×  %d  =  ￦ %,d",
                        cartItems.indexOf(item) + 1,
                        getDisplayName(item.getMenuItem().getName()),
                        item.getQuantity(),
                        item.getTotalPrice()))
                .collect(Collectors.joining("\n"));

        String formatTotalPrice = String.format("￦ %,d", cart.getTotalPrice());
        return "\n☰ 홈 > 장바구니\n"
                + line + cartList
                + "\n" + line
                + " 총 결제 금액     \t      =  " + formatTotalPrice;
    }

    private String getDisplayDiscount() {
        Discount[] discounts = Discount.values();

        String discountList = IntStream.range(0, discounts.length)
                        .mapToObj(i -> String.format(" [%d] %s\t\uD83D\uDD25 %.0f%%",
                                i + 1,
                                getDisplayName(discounts[i].getName()),
                                discounts[i].getRate() * 100))
                        .collect(Collectors.joining("\n"));

        return "\n☰ 홈 > 결제 > 할인\n"
                + discountList
                + "\n [" + (discounts.length + 1)
                + "] 해당 없음\n [0] 홈으로";
    }

    // 메뉴 이름을 한글 기준으로 정렬 후 공백을 포함한 문자열을 반환
    private String getDisplayName(String menuName) {
        int maxLength = 0; // 최대 길이 계산

        for (int i = 0; i < menus.size(); i++) {
            Menu menu = menus.get(i);
            List<MenuItem> menuItem = menu.getMenuItems();

            for (MenuItem item : menuItem) {
                // 현재 있는 메뉴들 중 가장 긴 메뉴명의 길이 계산
                maxLength = Math.max(maxLength, getDisplayLength(item.getName()));
            }
        }

        int paddingSize = maxLength - getDisplayLength(menuName); // 부족한 공백 개수 계산
        return menuName + " ".repeat(paddingSize); // 메뉴명 + 공백 리턴
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