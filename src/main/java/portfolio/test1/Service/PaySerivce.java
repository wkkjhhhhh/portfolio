package portfolio.test1.Service;

import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import portfolio.test1.DTO.Pay.DeliveryDTO;
import portfolio.test1.Repositiry.*;
import portfolio.test1.entity.CartEntity;
import portfolio.test1.entity.ItemEntity;
import portfolio.test1.entity.Pay.DeliveryEntity;
import portfolio.test1.entity.Pay.OrderEntity;
import portfolio.test1.entity.Pay.OrderItemEntity;

import java.util.List;

@Service
@Log4j2
public class PaySerivce {

    private final DeliveryRepository deliveryRepository;
    private final CartRepository cartRepository;
    private final OrderItemRepository orderItemRepository;
    private final OrderRepository orderRepository;
    private final ItemRepository itemRepository;

    public PaySerivce(DeliveryRepository deliveryRepository, CartRepository cartRepository, OrderItemRepository orderItemRepository, OrderRepository orderRepository, ItemRepository itemRepository) {
        this.deliveryRepository = deliveryRepository;
        this.cartRepository = cartRepository;
        this.orderItemRepository = orderItemRepository;
        this.orderRepository = orderRepository;
        this.itemRepository = itemRepository;
    }

    /***
     *  결제
     * @param deliveryDTO 배송지
     * @param idx 장바구니 idx
     * @param username 회원
     * @param itemIdx
     * @param quantity
     */
    @Transactional
    public void save(DeliveryDTO deliveryDTO, List<Long> idx, String username, Long itemIdx, int quantity) {
        OrderEntity orderEntity = new OrderEntity();
        DeliveryEntity delivery = deliveryRepository.save(DeliveryEntity.toChange(deliveryDTO));
        OrderItemEntity orderItemEntity = new OrderItemEntity();
        for (int i = 0; i < idx.size(); i++) {
            if (idx.get(0) == 0) {
                ItemEntity item = itemRepository.findById(itemIdx).orElse(null);

                orderItemEntity.setItem(item);
                orderItemEntity.setPrice(item.getPrice());
                orderItemEntity.setQuantity(quantity);
                orderItemRepository.save(orderItemEntity);

                orderEntity.addItem(orderItemEntity);

            } else {
                CartEntity cartEntity = cartRepository.findById(idx.get(i)).orElse(null);

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

}
