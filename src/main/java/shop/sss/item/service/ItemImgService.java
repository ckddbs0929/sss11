package shop.sss.item.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.thymeleaf.util.StringUtils;
import shop.sss.item.entity.ItemImg;
import shop.sss.item.repository.ItemImgRepository;

import javax.persistence.EntityNotFoundException;
import java.io.IOException;

@Service
@Transactional
@RequiredArgsConstructor
// 상품이미지 업로드, 정보 저장 서비스
public class ItemImgService {

    @Value("${location}")
    private String itemImgLocation;

    private final ItemImgRepository itemImgRepository;
    private final FileService fileService;

    public void saveItemImg(ItemImg itemimg, MultipartFile itemImgFile) throws IOException {

        String oriImgName = itemImgFile.getOriginalFilename();
        String imgName = "";
        String imgUrl = "";

        // 파일 업로드
        if (!StringUtils.isEmpty(oriImgName)) {
            imgName = fileService.uploadFile(itemImgLocation, oriImgName, itemImgFile.getBytes());
            imgUrl = "/images/item/" + imgName;
        }


        // 상품 이미지 정보 저장
        itemimg.updateImg(oriImgName, imgName, imgUrl);
        itemImgRepository.save(itemimg);
    }

    public void updateItemImg(Long itemImgId, MultipartFile itemImgFile) throws Exception {

        // 상품 이미지를 수정했다면 -> 상품 수정이 안이루어지면 null 이 객체로 넘어옴
        if (!itemImgFile.isEmpty()) {
            ItemImg savedItemImg = itemImgRepository.findById(itemImgId)
                    .orElseThrow(EntityNotFoundException::new);

            // 기존 이미지 파일이 존재한다면 삭제
            if (!StringUtils.isEmpty(savedItemImg.getImgName())) {
                fileService.deleteFile(itemImgLocation + "/" + savedItemImg.getImgName());
            }

            String oriImgName = itemImgFile.getOriginalFilename();
            String imgName = fileService.uploadFile(itemImgLocation,
                    oriImgName, itemImgFile.getBytes());
            String imgUrl = "/images/item/" + imgName;
            savedItemImg.updateImg(oriImgName, imgName, imgUrl);
        }
    }
}

