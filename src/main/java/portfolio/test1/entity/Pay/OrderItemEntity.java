package portfolio.test1.entity.Pay;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import portfolio.test1.entity.ItemEntity;

@Entity
@Getter
@Setter
@Table(name = "OrderItem_table")
public class OrderItemEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idx;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_idx")
    private OrderEntity orderEntity;

    @ManyToOne(fetch = FetchType.LAZY)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "item_idx")
    private ItemEntity item;

    private int price;

    private int quantity;

    public int getTotalPrice() {
        return price * quantity;
    }


}
