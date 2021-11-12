package shop.sss.entity;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

/*@NoArgsConstructor(access = AccessLevel.PROTECTED)*/
@Getter @Setter
@Entity
@Table(name = "board")
public class BoardEntity{

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;

    @Column(length = 100, nullable = false)
    private String title;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String content;

    @Column(length = 10, nullable = false)
    private String writer;

    @Column(name = "view_cnt")
    private int viewCnt;

    @Enumerated(value = EnumType.STRING)
    private BoardStatus boardStatus;

    private LocalDateTime insertTime;
    private LocalDateTime updateTime;
    private LocalDateTime deleteTime;
}
