package shop.sss.item.entity;

import lombok.Getter;
import lombok.Setter;
import org.modelmapper.ModelMapper;
import shop.sss.constant.ItemStatus;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@Getter @Setter
public class ItemFormDto {
    // 화면으로부터 입력 받은 상품 데이터 정보 Dto
    private Long id;

    @NotBlank(message = "상품명은 필수입니다.")
    private String itemName;

    @NotNull(message = "가격은 필수 입력 값입니다.")
    private Integer price; //Integer는 Null 처리가 가능하기 때문에 SQL사용시 편리

    @NotBlank(message = "상품 상세정보는 필수 입력 값입니다.")
    private String itemDetail;

    @NotNull(message = "재고는 필수 입력 값입니다.")
    private Integer stock;

    private ItemStatus itemStatus;

    private List<ItemImgDto> itemImgDtoList = new ArrayList<>();
    private List<Long> itemImgIds = new ArrayList<>();

    private static ModelMapper modelMapper = new ModelMapper();

    // DTO -> Entity 변환
    public Item createItem(){
        return modelMapper.map(this, Item.class);
    }
    // Entity -> DTO 변환
    public static ItemFormDto of(Item item){
        return modelMapper.map(item, ItemFormDto.class);
    }
}
