
package portfolio.test1.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;
import portfolio.test1.DTO.CategoryDTO;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@Table(name = "category_table" , uniqueConstraints = {
        @UniqueConstraint(columnNames ={"first","second","third"})})
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CategoryEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long idx;

    private String first;

    private String second;

    private String third;

    private String CategoryName; //상품명

    //@JsonIgnore
    @OneToMany(mappedBy = "category")
    private List<ItemEntity> items = new ArrayList<>();


    public static CategoryEntity toSave(CategoryDTO dto) {
        CategoryEntity categoryEntity = new CategoryEntity();
        categoryEntity.setFirst(dto.getFirst());
        categoryEntity.setSecond(dto.getSecond());
        categoryEntity.setThird(dto.getThird());
        categoryEntity.setCategoryName(dto.getName());
        return categoryEntity;
    }


}

