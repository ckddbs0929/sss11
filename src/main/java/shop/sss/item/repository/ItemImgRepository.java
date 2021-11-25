package shop.sss.item.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import shop.sss.item.entity.ItemImg;

import java.util.List;

public interface ItemImgRepository extends JpaRepository<ItemImg, Long> {
    List<ItemImg> findByItemIdOrderByIdAsc(Long itemId);
}
