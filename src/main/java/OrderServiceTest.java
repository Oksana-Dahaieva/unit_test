import com.epam.tamentoring.bo.DiscountUtility;
import com.epam.tamentoring.bo.OrderService;
import com.epam.tamentoring.bo.Product;
import com.epam.tamentoring.bo.ShoppingCart;
import com.epam.tamentoring.bo.UserAccount;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class OrderServiceTest {

  @Mock
  private DiscountUtility discountUtility;

  @InjectMocks
  private OrderService orderService;

  private UserAccount user;

  @BeforeEach
  void setUp() {
    MockitoAnnotations.openMocks(this);

    ShoppingCart shoppingCart = new ShoppingCart(new ArrayList<>());
    shoppingCart.addProductToCart(new Product(1, "Laptop", 1000.0, 1));

    user = new UserAccount("John", "Smith", "1990/10/10", shoppingCart);
  }

  @Test
  public void testOrderServiceDiscountCalculation() {
    when(discountUtility.calculateDiscount(user)).thenReturn(3.0);

    double orderPrice = orderService.getOrderPrice(user);

    assertEquals(997.0, orderPrice);
    verify(discountUtility, times(1)).calculateDiscount(user);
    verifyNoMoreInteractions(discountUtility);
  }
}
