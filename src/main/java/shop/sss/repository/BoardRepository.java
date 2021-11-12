package shop.sss.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import shop.sss.entity.BoardEntity;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class BoardRepository {

    @PersistenceContext
    private EntityManager em;


    public void insertBoard(BoardEntity board){
        em.persist(board);
    }


    public BoardEntity selectBoardDetail(Long id){
        return em.find(BoardEntity.class, id);
    }

    public List<BoardEntity> selectBoardList(){
       /* return em.createQuery("select b from BoardEntity b",BoardEntity.class)
                .getResultList();*/
        TypedQuery<BoardEntity> query = em.createQuery("select b from BoardEntity b", BoardEntity.class);
        List<BoardEntity> board = query.getResultList();
        return board;
    }

    public void deleteBoard(Long id){
        em.remove(id);
    }

}
