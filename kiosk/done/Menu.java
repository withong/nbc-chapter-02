package done;

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
        this.menuItems.addAll(Arrays.asList(menu));
        return this;
    }
}
