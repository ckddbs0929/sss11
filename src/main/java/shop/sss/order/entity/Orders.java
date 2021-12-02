package shop.sss.order.entity;

import lombok.Getter;
import lombok.Setter;
import shop.sss.constant.BaseEntity;
import shop.sss.constant.OrderStatus;
import shop.sss.member.entity.Member;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter @Setter
public class Orders extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "order_id")
    private Long id;

    @Enumerated(value = EnumType.STRING)
    private OrderStatus orderStatus; // 주문 상태

    private LocalDateTime orderDate; // 주문 시간

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @OneToMany(mappedBy = "orders", cascade = CascadeType.ALL)
    private List<OrderItem> orderItems = new ArrayList<>();

    public void addOrderItem(OrderItem orderItem){

        orderItems.add(orderItem); // 주문 객체에 주문 상품 객체 연결
        orderItem.setOrders(this); // 주문 상품 객체에 주문 객체 연결 (연관관계 주인)
    }

    // 주문 객체 생성
    public static Orders createOrder(Member member, List<OrderItem> orderItemList){

        Orders orders = new Orders();
        orders.setMember(member);
        for(OrderItem orderItem : orderItemList){
            orders.addOrderItem(orderItem);
        }
        orders.setOrderStatus(OrderStatus.ORDER);
        orders.setOrderDate(LocalDateTime.now());
        return orders;
    }

    public int getTotalPrice(){
        int totalPrice = 0;

        // 각 상품마다 TotalPrice 를 구하고 더함
        for(OrderItem orderItem : orderItems){
            totalPrice += orderItem.getTotalPrice();
        }
        return totalPrice;
    }
}
