package portfolio.test1.Repositiry;

import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import portfolio.test1.entity.CategoryEntity;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Rollback
class CategoryRepositoryTest {

    @Autowired
    CategoryRepository categoryRepository;

   /* @Test
    void addCategory() {

        CategoryEntity category = new CategoryEntity();

        category.setCategoryName("OUTER");
        category.setFirst("10");

        categoryRepository.save(category);

        CategoryEntity categoryEntity = categoryRepository.findById(category.getIdx()).get();
        System.out.println("categoryEntity.getCategoryName() = " + categoryEntity.getCategoryName());
        System.out.println("categoryEntity.getIdx() = " + categoryEntity.getIdx());

    }*/



}