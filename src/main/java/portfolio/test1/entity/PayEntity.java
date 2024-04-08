package portfolio.test1.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@Table(name = "pay_table")
public class PayEntity extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idx;

    // 장바구니 안 구매할 상품들
    @OneToMany(fetch = FetchType.LAZY)
    private List<CartEntity> cartEntityList = new ArrayList<>();

    @OneToOne
    @JoinColumn(name = "delivery_idx")
    private DeliveryEntity deliveryEntity;

    private int total;

}
/*
 * 결제 테이블
 * Long idx
 * 장바구니 엔티티 중 체크한 장바구니 물품
 * 배송 테이블
 * 총 결제 금액
 * 결제 날짜
 * */


