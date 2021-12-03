package shop.sss.order.controller;

import org.aspectj.weaver.ast.Or;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import shop.sss.constant.ItemStatus;
import shop.sss.item.entity.Item;
import shop.sss.item.repository.ItemRepository;
import shop.sss.member.entity.Member;
import shop.sss.member.repository.MemberRepository;
import shop.sss.order.entity.OrderDto;
import shop.sss.order.entity.OrderItem;
import shop.sss.order.entity.Orders;
import shop.sss.order.repository.OrderRepository;
import shop.sss.order.service.OrderService;

import javax.persistence.EntityNotFoundException;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class OrderControllerTest {

    @Autowired
    private OrderService orderService;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    ItemRepository itemRepository;

    @Autowired
    MemberRepository memberRepository;

    public Item saveItem(){
        Item item = new Item();
        item.setItemName("test item");
        item.setPrice(10000);
        item.setItemDetail("test details");
        item.setItemStatus(ItemStatus.SELL);
        item.setStock(100);
        return itemRepository.save(item);
    }
    public Member saveMember(){
        Member member = new Member();
        member.setEmail("user@naver.com");
        return memberRepository.save(member);
    }

    @Test
    public void order(){
        Item item = saveItem();
        Member member = saveMember();

        // 상품 상세 페이지 화면에서 넘어오는 값을 설정
        OrderDto orderDto = new OrderDto();
        orderDto.setCount(10);
        orderDto.setItemId(item.getId());

        // 주문 객체 DB에 저장
        Long orderId = orderService.order(orderDto, member.getEmail());

        // 저장된 주문 객체 조회
        Orders orders = orderRepository.findById(orderId).orElseThrow(EntityNotFoundException::new);

        // DB 에 저장된 주문객체에서 주문상품 추출
        List<OrderItem> orderItems = orders.getOrderItems();

        // 추출한 주문 상품 총 가격
        int totalPrice = orderDto.getCount() * item.getPrice();

        Assertions.assertEquals(totalPrice, orders.getTotalPrice());

    }
}