package portfolio.test1.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import portfolio.test1.entity.CategoryEntity;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SecondCategoryDTO {
    private Long idx;

    private int second;

    private String CategoryName;


}
