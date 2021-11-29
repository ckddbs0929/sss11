package shop.sss.item.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import shop.sss.item.entity.Item;
import shop.sss.item.entity.ItemSearchDto;
import shop.sss.item.entity.MainItemDto;

public interface ItemRepositoryCustom {
    Page<Item> getItemPage(ItemSearchDto itemSearchDto, Pageable pageable);
     /*상품 조회 조건을 담은 itemSearchDto , 페이징 정보를 담고 있는 Pageable 객체를
     파라미터로 받고 Page 리스트로 반환*/

    Page<MainItemDto> getMainItemPage(ItemSearchDto itemSearchDto, Pageable pageable);
}
