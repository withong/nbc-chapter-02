package challenge.level2;

public class CartItem {

    private MenuItem menuItem;
    private int quantity;

    CartItem(MenuItem menuItem) {
        this.menuItem = menuItem;
        this.quantity = 1;
    }

    public void addQuantity() {
        this.quantity++;
    }

    public MenuItem getMenuItem() {
        return menuItem;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        if (quantity < 0) {
            this.quantity = 0;
            return;
        }
        this.quantity = quantity;
    }

    public int getTotalPrice() {
        return menuItem.getPrice() * this.quantity;
    }
}
