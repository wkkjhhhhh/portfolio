package portfolio.test1.Repositiry;

import org.springframework.data.jpa.repository.JpaRepository;
import portfolio.test1.entity.Pay.OrderItemEntity;

public interface OrderItemRepository extends JpaRepository<OrderItemEntity,Long> {
}
