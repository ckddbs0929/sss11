package shop.sss.cart.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import shop.sss.cart.entity.CartItem;
import shop.sss.cart.entity.CartItemDto;
import shop.sss.cart.repository.CartItemRepository;
import shop.sss.constant.ItemStatus;
import shop.sss.item.entity.Item;
import shop.sss.item.repository.ItemRepository;
import shop.sss.member.entity.Member;
import shop.sss.member.repository.MemberRepository;

import javax.persistence.EntityNotFoundException;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class CartServiceTest {

    @Autowired
    ItemRepository itemRepository;

    @Autowired
    MemberRepository memberRepository;

    @Autowired
    CartService cartService;

    @Autowired
    CartItemRepository cartItemRepository;

    public Item createItem(){
        Item item = new Item();
        item.setItemName("테스트");
        item.setPrice(1000);
        item.setItemDetail("상품 상세");
        item.setItemStatus(ItemStatus.SELL);
        item.setStock(100);
        return itemRepository.save(item);
    }
    public Member createMember(){
        Member member = new Member();
        member.setEmail("test@test.com");
        return memberRepository.save(member);
    }

    @Test
    public void addCart(){
        Item item = createItem();
        Member member = createMember();

        CartItemDto cartItemDto = new CartItemDto();
        cartItemDto.setCount(5);
        cartItemDto.setItemId(item.getId());

        Long cartItemId = cartService.addCart(cartItemDto, member.getEmail());
        CartItem cartItem = cartItemRepository.findById(cartItemId)
                .orElseThrow(EntityNotFoundException::new);

        Assertions.assertEquals(item.getId(), cartItem.getItem().getId());
        Assertions.assertEquals(cartItemDto.getCount(), cartItem.getCount());
    }
}