package portfolio.test1.Service;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import portfolio.test1.DTO.SecondCategoryDTO;
import portfolio.test1.Repositiry.CategoryRepository;
import portfolio.test1.entity.CategoryEntity;

import java.util.ArrayList;
import java.util.List;

@Service
@Log4j2
@RequiredArgsConstructor
public class CategoryItemService {


    private final CategoryRepository categoryRepository;


    public List<SecondCategoryDTO> select(String first, String second) {
        List<SecondCategoryDTO> dtos = new ArrayList<>();
        if(second.equals("0")) {

            List<CategoryEntity> secondEntity = categoryRepository.findSecond(first);

            for (int i = secondEntity.size() - 1; i >= 0; i--) {
                if (secondEntity.get(i).getThird().equals("0")) {
                    SecondCategoryDTO dto = new SecondCategoryDTO();
                    dto.setIdx(secondEntity.get(i).getIdx());
                    dto.setSecond(Integer.parseInt(secondEntity.get(i).getSecond()));
                    dto.setCategoryName(secondEntity.get(i).getCategoryName());
                    dtos.add(dto);
                }
            }
            return dtos;
        }else {
            List<CategoryEntity> thirdEntity = categoryRepository.findThird(first,second);

            for (int i = thirdEntity.size() - 1; i >= 0; i--) {
                SecondCategoryDTO dto = new SecondCategoryDTO();
                dto.setIdx(thirdEntity.get(i).getIdx());
                dto.setSecond(Integer.parseInt(thirdEntity.get(i).getThird()));
                dto.setCategoryName(thirdEntity.get(i).getCategoryName());
                dtos.add(dto);
            }
        }
        return dtos;

    }
    //카테고리 이름 찾기
    public String findById(Long idx) {
        CategoryEntity categoryEntity = categoryRepository.findById(idx).orElse(null);

        String categoryName = categoryEntity.getCategoryName();

        return categoryName;
    }
}