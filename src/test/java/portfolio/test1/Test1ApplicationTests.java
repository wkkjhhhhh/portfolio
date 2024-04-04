package portfolio.test1;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import jakarta.transaction.TransactionManager;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.web.bind.annotation.ModelAttribute;
import portfolio.test1.DTO.CategoryDTO;
import portfolio.test1.DTO.ItemCreateDTO;
import portfolio.test1.DTO.ItemDTO;
import portfolio.test1.Repositiry.CategoryRepository;
import portfolio.test1.Repositiry.ItemFileRepository;
import portfolio.test1.Repositiry.ItemRepository;
import portfolio.test1.entity.CategoryEntity;
import portfolio.test1.entity.ItemEntity;
import portfolio.test1.entity.ItemFileEntity;
import portfolio.test1.entity.MemberEntity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

@SpringBootTest
class Test1ApplicationTests {

	@Autowired
	EntityManager em;

	@Autowired
	ItemRepository itemRepository;

	@Autowired
	ItemFileRepository itemFileRepository;

	@Autowired
	CategoryRepository categoryRepository;

@Test
	void aaa() {

	/*ItemCreateDTO itemCreateDTO = new ItemCreateDTO();
	itemCreateDTO.setFirst("1");
	itemCreateDTO.setSecond("1");
	itemCreateDTO.setThird("1");
	itemCreateDTO.setName("OUTER");

	ItemEntity item = ItemEntity.toSaveFileEntity(itemCreateDTO);*/






}

	/*@Test
	void aaaa() {
		List<ItemFileEntity> byItemId = itemFileRepository.findByItemEntity_Idx(5L);

		System.out.println(byItemId);
	}*/

	/*@Test
	void 테스트() {
		String sql ="SELECT * FROM member WHERE name = :name";
		Query query = em.createNativeQuery(sql);
		query.setParameter("name", "admin");


		System.out.println(Arrays.toString(results.get(0)));
	}*/
/*

	@Test
	@Rollback(false)
	void 페치() {

		ItemFileEntity item1 = new ItemFileEntity();
		ItemFileEntity item2 = new ItemFileEntity();

		item1.setOriginalFileName("파일네임1");
		item2.setOriginalFileName("파일네임2");

		em.persist(item1);
		em.persist(item2);

		List<ItemFileEntity> itemFileEntityList = new ArrayList<>();
		itemFileEntityList.add(item1);
		itemFileEntityList.add(item2);

		ItemEntity itemEntity = new ItemEntity();
		itemEntity.setName("아이템이름");
		itemEntity.setItemFileEntities(itemFileEntityList);

		item1.setItemEntity(itemEntity);
		item2.setItemEntity(itemEntity);


		em.persist(itemEntity);

		em.flush();
		em.clear();




	}
*/


/*	@Test
	void 조회() {

		ItemEntity findItem = itemRepository.aaaaaa("아이템이름");

		System.out.println("aaaaaa = " + findItem.getName());


		System.out.println("==============================================");

		for (ItemFileEntity itemFile : findItem.getItemFileEntities()) {
			System.out.println("아이템파일이름 = " + itemFile);
		}
	}*/


}
