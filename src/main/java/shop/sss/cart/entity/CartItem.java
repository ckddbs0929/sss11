package shop.sss.cart.entity;

import lombok.Getter;
import lombok.Setter;
import shop.sss.item.entity.Item;

import javax.persistence.*;

@Entity
@Getter @Setter
public class CartItem {

    @Id
    @Column(name = "cart_item_id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private int count;

    @JoinColumn(name = "cart_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private Cart cart;

    @JoinColumn(name = "item_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private Item item;
}
