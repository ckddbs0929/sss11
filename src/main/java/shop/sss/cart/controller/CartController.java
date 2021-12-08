package shop.sss.cart.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import shop.sss.cart.entity.CartListDto;
import shop.sss.cart.entity.CartItemDto;
import shop.sss.cart.service.CartService;

import javax.validation.Valid;
import java.security.Principal;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class CartController {

    private final CartService cartService;

    @PostMapping(value = "/cart")
    @ResponseBody
    public ResponseEntity cart(@RequestBody @Valid CartItemDto cartItemDto,
                               BindingResult bindingResult, Principal principal) {

        if (bindingResult.hasErrors()) {
            StringBuilder sb = new StringBuilder();
            List<FieldError> fieldErrors = bindingResult.getFieldErrors();
            for (FieldError fieldError : fieldErrors) {
                sb.append(fieldError.getDefaultMessage());
            }
            return new ResponseEntity<String>(sb.toString(), HttpStatus.BAD_REQUEST);
        }

        Long cartItemId;

        try {
            cartItemId = cartService.addCart(cartItemDto, principal.getName());
        } catch (Exception e) {
            return new ResponseEntity<String>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<Long>(cartItemId, HttpStatus.OK);
    }


    // 장바구니 조회
    @GetMapping(value = "/cart")
    public String cartList(Principal principal, Model model) {

        List<CartListDto> cartListDtos = cartService.getCartList(principal.getName());
        model.addAttribute("cartItems", cartListDtos);
        return "cart/cartList";
    }
}
