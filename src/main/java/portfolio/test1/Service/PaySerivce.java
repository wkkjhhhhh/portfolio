package portfolio.test1.Service;

import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import portfolio.test1.DTO.Pay.DeliveryDTO;
import portfolio.test1.Repositiry.CartRepository;
import portfolio.test1.Repositiry.DeliveryRepository;
import portfolio.test1.Repositiry.OrderItemRepository;
import portfolio.test1.Repositiry.OrderRepository;
import portfolio.test1.entity.CartEntity;
import portfolio.test1.entity.ItemEntity;
import portfolio.test1.entity.Pay.DeliveryEntity;
import portfolio.test1.entity.Pay.OrderEntity;
import portfolio.test1.entity.Pay.OrderItemEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Log4j2
public class PaySerivce {

    private final DeliveryRepository deliveryRepository;
    private final CartRepository cartRepository;
    private final OrderItemRepository orderItemRepository;
    private final OrderRepository orderRepository;

    public PaySerivce(DeliveryRepository deliveryRepository, CartRepository cartRepository, OrderItemRepository orderItemRepository, OrderRepository orderRepository) {
        this.deliveryRepository = deliveryRepository;
        this.cartRepository = cartRepository;
        this.orderItemRepository = orderItemRepository;
        this.orderRepository = orderRepository;
    }

    /***
     *  결제
     * @param deliveryDTO 배송지
     * @param idx 장바구니 idx
     * @param username 회원
     */
    @Transactional
    public void save(DeliveryDTO deliveryDTO, List<Long> idx, String username) {
        OrderEntity orderEntity = new OrderEntity();
        DeliveryEntity delivery = deliveryRepository.save(DeliveryEntity.toChange(deliveryDTO));

        for (int i = 0; i < idx.size(); i++) {
            CartEntity cartEntity = cartRepository.findById(idx.get(i)).orElse(null);
            OrderItemEntity orderItemEntity = new OrderItemEntity();

            orderItemEntity.setItem(cartEntity.getItem());
            orderItemEntity.setPrice(cartEntity.getItem().getPrice());
            orderItemEntity.setQuantity(cartEntity.getQuantity());
            orderItemRepository.save(orderItemEntity);

            orderEntity.addItem(orderItemEntity);
            //장바구니 삭제
            cartRepository.delete(cartEntity);
        }
        orderEntity.setDeliveryEntity(delivery);
        orderEntity.setUsername(username);

        orderRepository.save(orderEntity);
    }

}
