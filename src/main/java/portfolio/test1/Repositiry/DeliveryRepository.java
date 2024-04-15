package portfolio.test1.Repositiry;


import org.springframework.data.jpa.repository.JpaRepository;
import portfolio.test1.entity.Pay.DeliveryEntity;

public interface DeliveryRepository extends JpaRepository<DeliveryEntity, Long> {
}
