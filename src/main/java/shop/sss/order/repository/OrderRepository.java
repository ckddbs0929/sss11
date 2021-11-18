package shop.sss.order.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import shop.sss.order.entity.Orders;

public interface OrderRepository extends JpaRepository<Orders, Long> {
}
