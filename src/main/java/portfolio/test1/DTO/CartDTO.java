package portfolio.test1.DTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import portfolio.test1.entity.CartEntity;
import portfolio.test1.entity.ItemEntity;
import portfolio.test1.entity.MemberEntity;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CartDTO {
    private Long idx;
    private ItemEntity item;
    private String username;
    private int quantity;

    public static CartDTO toChange(CartEntity cart) {
        CartDTO cartDTO = new CartDTO();
        cartDTO.setIdx(cart.getIdx());
        cartDTO.setItem(cart.getItem());
        cartDTO.setUsername(cart.getUsername());
        cartDTO.setQuantity(cart.getQuantity());
        return  cartDTO;
    }
}
