package shop.sss.item.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import shop.sss.item.entity.Item;
import shop.sss.item.entity.ItemFormDto;
import shop.sss.item.repository.ItemImgRepository;
import shop.sss.item.repository.ItemRepository;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;


@Transactional
@SpringBootTest
class ItemServiceTest {

    @Autowired
    ItemService itemService;

    @Autowired
    ItemRepository itemRepository;

    @Autowired
    ItemImgRepository itemImgRepository;

    List<MultipartFile> create() throws Exception{
        List<MultipartFile> multipartFiles = new ArrayList<>();

        for(int i=0; i<5; i++){
            String path = "/Users/user/study/item";
            String imageName = "image" + i + ".jpg";
            MockMultipartFile mockMultipartFile = new MockMultipartFile(path, imageName,
                    "image/jpg", new byte[]{1, 2, 3, 4});
            multipartFiles.add(mockMultipartFile);
        }
        return multipartFiles;
    }

    @Test
    void update(){
        ItemFormDto itemFormDto = new ItemFormDto();
        itemFormDto.setPrice(100);
        itemFormDto.setItemDetail("ss");


    }
}