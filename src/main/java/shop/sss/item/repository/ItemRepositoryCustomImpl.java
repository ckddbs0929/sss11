package shop.sss.item.repository;

import com.querydsl.core.QueryResults;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.thymeleaf.util.StringUtils;
import shop.sss.constant.ItemStatus;
import shop.sss.item.entity.Item;
import shop.sss.item.entity.ItemSearchDto;
import shop.sss.item.entity.QItem;
import shop.sss.item.entity.QItemImg;
import shop.sss.item.entity.MainItemDto;
import shop.sss.item.entity.QMainItemDto;

import javax.persistence.EntityManager;
import java.time.LocalDateTime;
import java.util.List;

// 사용자 정의 인터페이스는 클래스명 끝에 "Impl" 를 붙여야 정상적을으로 작동
public class ItemRepositoryCustomImpl implements ItemRepositoryCustom{

    private JPAQueryFactory queryFactory;

    // 생성자 DI 를 통해서 EntityManager 주입
    private ItemRepositoryCustomImpl(EntityManager em){
        this.queryFactory = new JPAQueryFactory(em);
    }

    // 상품 등록일에 대한 조회 조건 BooleanExpression -> where 절에 적용될 조회 조건 생성
    // BooleanExpression 값이 null 이면 해당 조회 조건을 사용하지 않겠다
    private BooleanExpression regDtsAfter(String searchDateType){
        LocalDateTime dateTime = LocalDateTime.now();

        if(StringUtils.equals("all", searchDateType) || searchDateType == null){
            return null;
        } else if(StringUtils.equals("1d", searchDateType)){
            dateTime = dateTime.minusDays(1);
        } else if(StringUtils.equals("1w", searchDateType)){
            dateTime = dateTime.minusWeeks(1);
        } else if(StringUtils.equals("1m", searchDateType)){
            dateTime = dateTime.minusMonths(1);
        } else if(StringUtils.equals("6m", searchDateType)){
            dateTime = dateTime.minusMonths(6);
        }

        return QItem.item.regTime.after(dateTime);
    }
    // 상품 판매 상태 조건
    private BooleanExpression searchSellStatus(ItemStatus searchSellStatus){
        return searchSellStatus == null ? null : QItem.item.itemStatus.eq(searchSellStatus);
    }

    // 상품명, 상품 등록자 아이디 조건
    private BooleanExpression searchByLike(String searchBy, String searchQuery){
        if(StringUtils.equals("itemName", searchBy)){
            return QItem.item.itemName.like("%" + searchQuery + "%");
        } else if(StringUtils.equals("createBy", searchBy)){
            return QItem.item.createBy.like("%" + searchQuery + "%");
        }
        return null;
    }

    @Override
    public Page<Item> getItemPage(ItemSearchDto itemSearchDto, Pageable pageable){

        QueryResults<Item> results = queryFactory
                .selectFrom(QItem.item)
                .where(regDtsAfter(itemSearchDto.getSearchDateType()),
                        searchSellStatus(itemSearchDto.getSearchStatus()),
                        searchByLike(itemSearchDto.getSearchBy(), itemSearchDto.getSearchQuery()))
                .orderBy(QItem.item.id.desc())
                .offset(pageable.getOffset()) // 데이터를 가지고 올 시작 인덱스 지정
                .limit(pageable.getPageSize()) // 한번에 가지고 올 최대 개수 지정
                .fetchResults(); // 조회 대상 리스트 및 전체 개수를 포함하는 메소드

        // 조회 대상 리스트 결과
        List<Item> content = results.getResults();

        // 조회 대상 리스트의 갯수
        long total = results.getTotal();

        return new PageImpl<>(content, pageable, total);
    }

    // 검색어가 포함된 상품 조회 조건
    public BooleanExpression itemNameLike(String searchQuery){
        return StringUtils.isEmpty(searchQuery) ?
                null : QItem.item.itemName.like("%" + searchQuery + "%");
    }

    @Override
    public Page<MainItemDto> getMainItemPage(ItemSearchDto itemSearchDto, Pageable pageable){

        QItem item = QItem.item;
        QItemImg itemImg = QItemImg.itemImg;

        QueryResults<MainItemDto> result = queryFactory
                .select(
                        new QMainItemDto(
                                item.id,
                                item.itemName,
                                item.itemDetail,
                                itemImg.imgUrl,
                                item.price)
                )
                .from(itemImg)
                .join(itemImg.item, item) // itemImg 테이블의 item 필드가 참조하는 item 테이블을 조인
                .where(itemImg.repimgYn.eq("Y")) // 대표사진만 조회
                .where(itemNameLike(itemSearchDto.getSearchQuery()))
                .orderBy(item.id.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetchResults();

        List<MainItemDto> content = result.getResults();
        long total = result.getTotal();
        return new PageImpl<>(content, pageable, total);
    }
}
