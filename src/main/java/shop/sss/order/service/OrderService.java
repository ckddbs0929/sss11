package shop.sss.order.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.thymeleaf.util.StringUtils;
import shop.sss.item.entity.Item;
import shop.sss.item.entity.ItemImg;
import shop.sss.item.repository.ItemImgRepository;
import shop.sss.item.repository.ItemRepository;
import shop.sss.member.entity.Member;
import shop.sss.member.repository.MemberRepository;
import shop.sss.order.entity.*;
import shop.sss.order.repository.OrderRepository;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
@Transactional
public class OrderService {

    private final OrderRepository orderRepository; // 주문 객체를 생성해야함
    private final ItemRepository itemRepository;  // 상품을 불러와서 재고를 변경해야함
    private final MemberRepository memberRepository; // 멤버를 불러와서 연결해야함
    private final ItemImgRepository itemImgRepository; // 상품 대표 이미지를 불러와야함

    public Long order(OrderDto orderDto, String email){

        Item item = itemRepository.findById(orderDto.getItemId()).orElseThrow(EntityNotFoundException::new);
        Member member = memberRepository.findByEmail(email);

        List<OrderItem> orderItemList = new ArrayList<>();

        // OrderItem.createOrderItem -> static 메소드
        OrderItem orderItem = OrderItem.createOrderItem(item, orderDto.getCount());
        orderItemList.add(orderItem);

        // Orders.createOrder -> static 메소드
        Orders orders = Orders.createOrder(member, orderItemList);
        orderRepository.save(orders);

        return orders.getId();
    }

    // 주문 목록 조회
    @Transactional(readOnly = true)
    public Page<OrderHistDto> getOrderList(String email, Pageable pageable){

        List<Orders> orders = orderRepository.findOrders(email, pageable);
        Long totalCount = orderRepository.countOrder(email);

        List<OrderHistDto> orderHistDtos = new ArrayList<>();

        for(Orders order : orders){
            OrderHistDto orderHistDto = new OrderHistDto(order);
            List<OrderItem> orderItems = order.getOrderItems();
            for(OrderItem orderItem : orderItems){
                ItemImg itemImg = itemImgRepository.findByItemIdAndRepimgYn(orderItem.getItem().getId(), "Y");
                OrderItemDto orderItemDto = new OrderItemDto(orderItem, itemImg.getImgUrl());
                orderHistDto.addOrderItemDto(orderItemDto);
            }

            orderHistDtos.add(orderHistDto);
        }
        return new PageImpl<OrderHistDto>(orderHistDtos, pageable, totalCount);
    }

    @Transactional(readOnly = true)
    public boolean validateOrder(Long orderId, String email){

        // 주문 취소 요청 유저조회
        Member member = memberRepository.findByEmail(email);

        // 상품 주문한 유저조회
        Orders orders = orderRepository.findById(orderId).orElseThrow(EntityNotFoundException::new);
        Member savedMember = orders.getMember();

        if(!StringUtils.equals(member.getEmail(), savedMember.getEmail())){
            return false;
        }
        return true;
    }

    // 주문 취소 메소드, 위의 메소드로 검증 후 변경 감지
    public void cancelOrder(Long orderId){
        Orders order = orderRepository.findById(orderId).orElseThrow(EntityNotFoundException::new);
        order.cancelOrder();
    }
}
