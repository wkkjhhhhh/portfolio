package portfolio.test1.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import portfolio.test1.DTO.CartDTO;


import java.util.ArrayList;
import java.util.List;


@Entity
@Getter
@Setter
@Table(name = "cart_table")
public class CartEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idx;

    @ManyToOne(fetch = FetchType.EAGER)
    private ItemEntity item;

    private int quantity;

    private String username;


    public static CartEntity toSave(CartDTO dto) {
    CartEntity cartEntity = new CartEntity();
    cartEntity.setItem(dto.getItem());
    cartEntity.setUsername(dto.getUsername());
    cartEntity.setQuantity(dto.getQuantity());

    return cartEntity;
    }
}
