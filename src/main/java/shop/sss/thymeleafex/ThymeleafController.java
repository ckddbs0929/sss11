package shop.sss.thymeleafex;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import shop.sss.item.entity.ItemDto;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping(value = "/thymeleaf")
public class ThymeleafController {

    @GetMapping(value = "ex01")
    public String Example01(Model model){
        ItemDto itemDto = new ItemDto();
        itemDto.setItemDetail("제품 상세 설명");
        itemDto.setItemName("테스트 상품 22");
        itemDto.setPrice(1000);
        itemDto.setRegTime(LocalDateTime.now());

        model.addAttribute("itemDto", itemDto);
        return "thymeleafEx/thymeleafEx01";
    }

    @GetMapping(value = "ex02")
    public String Example02(Model model){
        List<ItemDto> list = new ArrayList<>();
        for(int i=1; i<=10; i++){
            ItemDto itemDto = new ItemDto();
            itemDto.setItemDetail("제품 설명" + i);
            itemDto.setItemName("제품 이름 " + i);
            itemDto.setPrice(1000 * i);
            itemDto.setRegTime(LocalDateTime.now());

            list.add(itemDto);
        }
        model.addAttribute("list", list);
        return "thymeleafEx/thymeleafEx02";
    }

    @GetMapping(value = "ex07")
    public String example07(){
        return "thymeleafEx/thymeleafEx07";
    }
}
