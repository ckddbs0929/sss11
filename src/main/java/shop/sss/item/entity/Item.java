package shop.sss.item.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import shop.sss.cart.entity.CartItem;
import shop.sss.constant.BaseEntity;
import shop.sss.constant.ItemStatus;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Getter @Setter
@ToString
@Entity
@Table(name = "item")
public class Item extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "item_id")
    private Long id; // 상품 코드

    @Column(nullable = false, length = 50)
    private String itemName; // 상품 이름

    @Lob // 대용량 데이터타입
    @Column(nullable = false)
    private String itemDetail; // 상품 상세 설명

    @Column(nullable = false)
    private int price; // 상품 가격

    @Column(nullable = false)
    private int stock; // 재고 수량

    @Enumerated(EnumType.STRING)
    private ItemStatus itemStatus; // 상품 판매 상태

    @OneToMany(mappedBy = "item")
    private List<CartItem> cartItems;

    @OneToMany(mappedBy = "item")
    private List<ItemImg> itemImgs;
}
