package portfolio.test1.entity.Pay;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import portfolio.test1.DTO.Pay.DeliveryDTO;

@Entity
@Getter
@Setter
@Table(name = "delivery_table")
public class DeliveryEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idx;

    //구매자
    private String username;

    //우편 번호
    private String address1;
    //주소
    private String address2;

    private String tel;

    private String contents;

    public static DeliveryEntity toChange(DeliveryDTO deliveryDTO) {
        DeliveryEntity deliveryEntity = new DeliveryEntity();
        deliveryEntity.setUsername(deliveryDTO.getUsername());
        deliveryEntity.setAddress1(deliveryDTO.getAddress1());
        deliveryEntity.setAddress2(deliveryDTO.getAddress2() +deliveryDTO.getAddress3() + deliveryDTO.getAddress4());
        deliveryEntity.setTel(deliveryDTO.getTel1() + "-" +deliveryDTO.getTel2() + "-" + deliveryDTO.getTel3());
        deliveryEntity.setContents(deliveryDTO.getContents());

        return  deliveryEntity;
    }
}