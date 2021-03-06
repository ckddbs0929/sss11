package shop.sss.cart.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import shop.sss.constant.BaseEntity;
import shop.sss.member.entity.Member;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter @Setter
@ToString
public class Cart extends BaseEntity{

    @Id
    @Column(name = "cart_id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @OneToMany(mappedBy = "cart")
    private List<CartItem> cartItems = new ArrayList<>();

    public static Cart createCart(Member member){
        Cart cart = new Cart();
        cart.member = member;
        return cart;
    }
}
