package challenge.level2;

import java.util.ArrayList;
import java.util.List;

public class Cart {

    private List<CartItem> cartItems;

    Cart() {
        this.cartItems = new ArrayList<>();
    }

    public void addCartItem(MenuItem menuItem) {
        for (CartItem cartItem : cartItems) {
            if (cartItem.getMenuItem().getName().equals(menuItem.getName())) {
                cartItem.addQuantity();
                return;
            }
        }
        cartItems.add(new CartItem(menuItem));
    }

    public List<CartItem> getCartItems() {
        return cartItems;
    }

    void setCartItems(List<CartItem> cartItems) {
        this.cartItems = cartItems;
    }

    public int getTotalPrice() {
        int total = 0;
        for (CartItem cartItem : cartItems) {
            total += cartItem.getTotalPrice();
        }
        return total;
    }

    public void clearCart() {
        cartItems.clear();
    }

    public boolean isEmpty() {
        return cartItems.isEmpty();
    }
}
