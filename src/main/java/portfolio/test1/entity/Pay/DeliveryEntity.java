package portfolio.test1.entity.Pay;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "delivery_table")
public class DeliveryEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idx;

    private String username;

    //우편 번호
    private String address1;
    //주소
    private String address2;


    private String tel;

    private String contents;

}
/*
 * 배송 테이블
 * idx
 * 멤버 정보
 * 배송지 주소
 * 전화번호
 * 배송 메세지
 */