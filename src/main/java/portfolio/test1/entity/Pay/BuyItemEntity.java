package portfolio.test1.entity.Pay;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import portfolio.test1.entity.ItemEntity;

@Entity
@Getter
@Setter
@Table(name = "BuyItem_table")
public class BuyItemEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idx;

    @ManyToOne(fetch = FetchType.EAGER)
    private ItemEntity item;

    private int quantity;
}
