
package portfolio.test1.Repositiry;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import portfolio.test1.entity.CategoryEntity;

import java.util.List;

public interface CategoryRepository extends JpaRepository<CategoryEntity,Long> {



    List<CategoryEntity> findAllByFirst(String first);

    //첫번째꺼
    @Query(value = "select * from category_table where second = 0",nativeQuery = true)
    List<CategoryEntity> findFirst();
    
    @Query(value = "select max(first) from category_table",nativeQuery = true)
    Long findFirstIdx();

    //두번째꺼
    @Query(value = "select * from category_table where first = ?1 and second != 0 and third = 0",nativeQuery = true)
    List<CategoryEntity> findSecond(String first);
    //세번째꺼
    @Query(value = "select * from category_table where first = ?1 and second = ?2 and third !=0 ",nativeQuery = true)
    List<CategoryEntity> findThird(String first,String second);



}

