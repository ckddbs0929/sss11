package shop.sss.item.entity;

import lombok.Getter;
import lombok.Setter;
import shop.sss.constant.ItemStatus;

@Getter @Setter
// 상품 조회 조건 Dto
public class ItemSearchDto {
    private String searchDateType;
    private ItemStatus searchStatus;
    private String searchBy;
    private String searchQuery = "";
}
