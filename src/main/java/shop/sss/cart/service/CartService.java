package shop.sss.cart.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.thymeleaf.util.StringUtils;
import shop.sss.cart.entity.Cart;
import shop.sss.cart.entity.CartListDto;
import shop.sss.cart.entity.CartItem;
import shop.sss.cart.entity.CartItemDto;
import shop.sss.cart.repository.CartItemRepository;
import shop.sss.cart.repository.CartRepository;
import shop.sss.item.entity.Item;
import shop.sss.item.repository.ItemRepository;
import shop.sss.member.entity.Member;
import shop.sss.member.repository.MemberRepository;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.List;

@Transactional
@Service
@RequiredArgsConstructor
public class CartService {

    private final CartRepository cartRepository;
    private final CartItemRepository cartItemRepository;
    private final MemberRepository memberRepository;
    private final ItemRepository itemRepository;

    public Long addCart(CartItemDto cartItemDto, String email){

        Member member = memberRepository.findByEmail(email);
        Cart cart = cartRepository.findByMemberId(member.getId());

        // 장바구니가 없으면 생성
        if(cart == null){
            cart = Cart.createCart(member);
            cartRepository.save(cart);
        }

        Item item = itemRepository.findById(cartItemDto.getItemId()).orElseThrow(EntityNotFoundException::new);
        CartItem cartItem = cartItemRepository.findByCartIdAndItemId(cart.getId(), item.getId());

        // 해당 상품이 장바구니에 없으면 생성 후 추가
        if(cartItem == null) {
            cartItem = CartItem.createCartItem(cart, item, cartItemDto.getCount());
            cartItemRepository.save(cartItem);

            // 해당 상품이 장바구니에 이미 존재한다면 수량을 증가
        } else{
            cartItem.addCount(cartItemDto.getCount());
        }
        return cartItem.getId();
    }

    @Transactional(readOnly = true)
    public List<CartListDto> getCartList(String email) {

        List<CartListDto> cartListDtos = new ArrayList<>();

        Member member = memberRepository.findByEmail(email);
        Cart cart = cartRepository.findByMemberId(member.getId());

        if (cart == null) {
            return cartListDtos;
        }

        cartListDtos = cartItemRepository.findCartListDto(cart.getId());
        return cartListDtos;
    }

    @Transactional(readOnly = true)
    public boolean validateCartItem(Long cartItemId, String email){

        // 현재 로그인한 유저
        Member member = memberRepository.findByEmail(email);

        // 수량 변경 요청이 들어온 장바구니 상품의 유저
        CartItem cartItem = cartItemRepository.findById(cartItemId).orElseThrow(EntityNotFoundException::new);
        Member savedMember = cartItem.getCart().getMember();

        if(!StringUtils.equals(member.getEmail(), savedMember.getEmail())){
            return false;
        }
        return true;
    }

    public void updateCartItemCount(int count, Long cartItemId){
        CartItem cartItem = cartItemRepository.findById(cartItemId).orElseThrow(EntityNotFoundException::new);
        cartItem.updateCount(count);
    }
}
