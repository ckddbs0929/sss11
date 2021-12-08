package shop.sss.cart.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import shop.sss.cart.entity.Cart;

public interface CartRepository extends JpaRepository<Cart, Long> {

    Cart findByMemberId(Long memberId);
}

