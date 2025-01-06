import com.epam.tamentoring.bo.Product;
import com.epam.tamentoring.bo.ShoppingCart;
import com.epam.tamentoring.exceptions.ProductNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ShoppingCartTest {

  private ShoppingCart shoppingCart;

  @BeforeEach
  void setUp() {
    shoppingCart = new ShoppingCart(new ArrayList<>());
  }

  @Test
  void testAddProductToCartWhenProductIsNew() {
    Product product = new Product(1, "Laptop", 1000.0, 1);
    shoppingCart.addProductToCart(product);
    assertEquals(1, shoppingCart
        .getProducts()
        .size());
    assertTrue(shoppingCart
        .getProducts()
        .contains(product));
  }

  @Test
  void testAddProductToCartWhenProductAlreadyExists() {
    Product product = new Product(1, "Laptop", 1000.0, 1);
    shoppingCart.addProductToCart(product);

    Product additionalProduct = new Product(1, "Laptop", 1000.0, 2);
    shoppingCart.addProductToCart(additionalProduct);

    Product resultProduct = shoppingCart.getProductById(1);
    assertEquals(3, resultProduct.getQuantity());
  }

  @Test
  void testRemoveProductFromCartWhenProductExists() {
    Product product = new Product(1, "Laptop", 1000.0, 1);
    shoppingCart.addProductToCart(product);

    shoppingCart.removeProductFromCart(product);
    assertEquals(0, shoppingCart
        .getProducts()
        .size());
  }

  @Test
  void testRemoveProductFromCartWhenProductDoesNotExist() {
    Product product = new Product(1, "Laptop", 1000.0, 1);

    Exception exception = assertThrows(ProductNotFoundException.class, () -> {
      shoppingCart.removeProductFromCart(product);
    });

    assertEquals(
        "Product is not found in the cart: Product(id=1, name=Laptop, price=1000.0, quantity=1.0)",
        exception.getMessage());
  }

  @Test
  void testGetCartTotalPrice() {
    Product product1 = new Product(1, "Laptop", 1000.0, 1);
    Product product2 = new Product(2, "Mouse", 50.0, 2);
    shoppingCart.addProductToCart(product1);
    shoppingCart.addProductToCart(product2);

    double totalPrice = shoppingCart.getCartTotalPrice();
    assertEquals(1100.0, totalPrice);
  }
}
