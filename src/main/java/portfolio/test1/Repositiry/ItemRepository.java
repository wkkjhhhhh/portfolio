package portfolio.test1.Repositiry;


import org.springframework.data.jpa.repository.JpaRepository;
import portfolio.test1.entity.ItemEntity;

public interface ItemRepository extends JpaRepository<ItemEntity, Long> {

}
