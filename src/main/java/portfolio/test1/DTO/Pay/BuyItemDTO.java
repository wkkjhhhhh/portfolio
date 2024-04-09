package portfolio.test1.DTO.Pay;

import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class BuyItemDTO {

    private Long idx;

    private int itemIdx;

    private int quantity;
}
