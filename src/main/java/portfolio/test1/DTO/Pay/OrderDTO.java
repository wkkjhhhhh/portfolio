
package portfolio.test1.DTO.Pay;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import portfolio.test1.entity.ItemEntity;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class OrderDTO {

    private Long idx;

    private ItemEntity item;

    private int price;

    private int quantity;



}

