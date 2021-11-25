package shop.sss.item.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import shop.sss.item.entity.Item;
import shop.sss.item.entity.ItemFormDto;
import shop.sss.item.entity.ItemImg;
import shop.sss.item.repository.ItemImgRepository;
import shop.sss.item.repository.ItemRepository;

import java.util.List;

@Transactional
@Service
@RequiredArgsConstructor
public class ItemService {

    private final ItemRepository itemRepository;
    private final ItemImgService itemImgService;
    private final ItemImgRepository itemImgRepository;

    public Long saveItem(ItemFormDto itemFormDto,
                         List<MultipartFile> itemImgFileList) throws Exception {

        // 상품 등록 (1번)
        Item item = itemFormDto.createItem();
        itemRepository.save(item);

        // 이미지 등록(2번, 순서중요)
        for (int i = 0; i < itemImgFileList.size(); i++) {
            ItemImg itemimg = new ItemImg();
            itemimg.setItem(item);
            if (i == 0) {
                itemimg.setRepimgYn("Y");
            } else {
                itemimg.setRepimgYn("N");
            }
            itemImgService.saveItemImg(itemimg, itemImgFileList.get(i));
        }
        return item.getId();

    }
}
