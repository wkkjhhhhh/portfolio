package portfolio.test1.entity.Pay;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import portfolio.test1.entity.BaseEntity;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@Table(name = "Order_table")
public class OrderEntity extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idx;

    private String username;

    //구매할 아이템
    @OneToMany(mappedBy = "orderEntity")
    private List<OrderItemEntity> items = new ArrayList<>();

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "delivery_idx")
    private DeliveryEntity deliveryEntity;


    //연관관계 편의 메서드//
    public void addItem(OrderItemEntity orderItem) {
        items.add(orderItem);
        orderItem.setOrderEntity(this);
    }


}
