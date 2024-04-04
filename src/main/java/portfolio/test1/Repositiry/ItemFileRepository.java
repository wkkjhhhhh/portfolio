package portfolio.test1.Repositiry;


import org.springframework.data.jpa.repository.JpaRepository;
import portfolio.test1.entity.ItemFileEntity;


import java.util.List;

public interface ItemFileRepository extends JpaRepository<ItemFileEntity,Long> {
    List<ItemFileEntity> findByItemEntity_Idx(Long idx);


}
