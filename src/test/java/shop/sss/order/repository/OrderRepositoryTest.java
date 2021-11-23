package shop.sss.order.repository;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;
import shop.sss.constant.ItemStatus;
import shop.sss.item.entity.Item;
import shop.sss.item.repository.ItemRepository;
import shop.sss.order.entity.OrderItem;
import shop.sss.order.entity.Orders;

import javax.persistence.EntityManager;
import javax.persistence.EntityNotFoundException;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class OrderRepositoryTest {

    @Autowired
    OrderRepository orderRepository;

    @Autowired
    ItemRepository itemRepository;

    @Autowired
    EntityManager em;

    public Item create(){
        Item item = new Item();
        item.setItemNm("테스트 상품");
        item.setPrice(1000);
        item.setItemDetail("테스트 상세 설명");
        item.setItemStatus(ItemStatus.SELL);
        item.setStockNumber(100);
        item.setRegTime(LocalDateTime.now());
        item.setUpdateTime(LocalDateTime.now());
        return  item;
    }

    @Test
    @Rollback(value = false)
    public void casTest(){

        Orders orders = new Orders();

        for(int i=0; i<3; i++){
            Item item = this.create();
            itemRepository.save(item);

            OrderItem orderItem = new OrderItem();
            orderItem.setItem(item);
            orderItem.setCount(10);
            orderItem.setOrderPrice(1000);
            orderItem.setOrders(orders);

            orders.getOrderItems().add(orderItem);
        }

        orderRepository.saveAndFlush(orders);
        em.clear();

        Orders save = orderRepository.findById(orders.getId())
                .orElseThrow(EntityNotFoundException::new);
        Assertions.assertEquals(3, save.getOrderItems().size());
    }
}