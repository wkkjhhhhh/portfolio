
package portfolio.test1.Repositiry;

import org.springframework.data.jpa.repository.JpaRepository;
import portfolio.test1.DTO.CartDTO;
import portfolio.test1.entity.CartEntity;

import java.util.List;

public interface CartRepository extends JpaRepository<CartEntity,Long> {
    List<CartEntity> findByUsername(String username);
}

