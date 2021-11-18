package shop.sss.item.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import shop.sss.cart.entity.CartItem;
import shop.sss.constant.ItemStatus;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter @Setter
@ToString
@Entity
@Table(name = "item")
public class Item {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "item_id")
    private Long id; // 상품 코드

    @Column(nullable = false, length = 50)
    private String itemNm; // 상품 이름

    @Column(nullable = false)
    private int price; // 상품 가격

    @Column(nullable = false)
    private int stockNumber; // 재고 수량

    @Lob // 대용량 데이터타입
    @Column(nullable = false)
    private String itemDetail; // 상품 상세 설명

    @Enumerated(EnumType.STRING)
    private ItemStatus itemStatus; // 상품 판매 상태

    private LocalDateTime regTime; // 상품 등록 시간
    private LocalDateTime updateTime; // 상품 수정 시간

    @OneToMany(mappedBy = "item")
    private CartItem cartItem;
}
