package basic.level4;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Menu {

    private String category;
    private List<MenuItem> menuItems;

    Menu(String category) {
        this.category = category;
        menuItems = new ArrayList<>();
    }

    public String getCategory() {
        return category;
    }

    public List<MenuItem> getMenuItems() {
        return menuItems;
    }

    public Menu addMenuItems(MenuItem... menu) {
        // 전달된 menu를 리스트로 변환하여 menuItems 리스트에 추가
        this.menuItems.addAll(Arrays.asList(menu));
        // 현재 Menu 객체 반환
        return this;
    }
}
