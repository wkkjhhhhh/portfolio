package portfolio.test1.DTO;

import lombok.*;
import portfolio.test1.entity.CategoryEntity;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class CategoryDTO {
    private Long idx;

    private String first;

    private String second;

    private String third;

    private String name;

    public static CategoryDTO toCategoryDTO(CategoryEntity category){
        CategoryDTO categoryDTO = new CategoryDTO();
        categoryDTO.setIdx(category.getIdx());
        categoryDTO.setFirst(category.getFirst());
        categoryDTO.setSecond(category.getSecond());
        categoryDTO.setThird(category.getThird());
        categoryDTO.setName(category.getCategoryName());

        return categoryDTO;
    }

}
