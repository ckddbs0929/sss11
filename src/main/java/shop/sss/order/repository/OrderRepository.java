package shop.sss.order.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import shop.sss.order.entity.Orders;

import java.util.List;

public interface OrderRepository extends JpaRepository<Orders, Long> {

    // 유저의 구매 이력을 페이징 정보에 맞게 조회 (주문 객체들)
    @Query("select o from Orders o " +
            "where o.member.email = :email " +
            "order by o.orderDate desc")
    List<Orders> findOrders(@Param("email") String email, Pageable pageable);

    // 유저 주문 갯수
    @Query("select count(o) from Orders o " +
            "where o.member.email = :email")
    Long countOrder(@Param("email") String email);
}
