package shop.sss.cart.entity;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter @Setter
public class CartOrderDto {

    private Long cartItemid;
    private List<CartOrderDto> cartOrderDtoList;
}
