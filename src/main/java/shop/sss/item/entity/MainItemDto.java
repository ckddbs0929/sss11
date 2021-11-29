package shop.sss.item.entity;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
// 메인화면 출력용 DTO
public class MainItemDto {
    private Long id;
    private String itemName;
    private String itemDetail;
    private String imgUrl;
    private Integer price;

    @QueryProjection // Entity 객체를 Dto 객체로 바로 반환하도록 지원함
    public MainItemDto(Long id, String itemName, String itemDetail,
                       String imgUrl, Integer price){
        this.id = id;
        this.itemName = itemName;
        this.itemDetail = itemDetail;
        this.imgUrl = imgUrl;
        this.price = price;
    }
}
