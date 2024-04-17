package portfolio.test1.Repositiry;

import org.springframework.data.jpa.repository.JpaRepository;
import portfolio.test1.entity.Pay.OrderEntity;

import java.util.List;

public interface OrderRepository extends JpaRepository<OrderEntity,Long> {
    List<OrderEntity> findByUsername(String userid);
}
