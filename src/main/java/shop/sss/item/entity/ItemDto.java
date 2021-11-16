package shop.sss.item.entity;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter @Setter
// 상품 데이터 출력용 Dto
public class ItemDto {

    private Long id;
    private String itemNm;
    private Integer price; // Integer는 Null 처리가 가능하기 때문에 SQL사용시 편리
    private String itemDetail;
    private String sellStatCd;
    private LocalDateTime regTime;
    private LocalDateTime updateTime;
}
