package portfolio.test1.entity;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import portfolio.test1.DTO.ItemCreateDTO;


import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@Table(name = "item_table")
public class ItemEntity extends  BaseEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idx;

    private String name;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "Category_id")
    private CategoryEntity category;

    private int price;

    private String contents;

    private int fileAttached; // 1 or 0

    @OneToMany(mappedBy = "itemEntity",
            fetch = FetchType.EAGER,
            cascade = CascadeType.REMOVE,
            orphanRemoval = true)
    private List<ItemFileEntity> itemFileEntities = new ArrayList<>();

    public static ItemEntity toSaveEntity(ItemCreateDTO dto,CategoryEntity category) {
        ItemEntity itemEntity = new ItemEntity();
        itemEntity.setIdx(dto.getIdx());
        itemEntity.setName(dto.getName());
        itemEntity.setCategory(category); // entity 로 주입
        itemEntity.setPrice(dto.getPrice());
        itemEntity.setContents(dto.getContents());
        itemEntity.setFileAttached(0); //파일 없음
        return itemEntity;
    }
    public static ItemEntity toSaveFileEntity(ItemCreateDTO dto,CategoryEntity category) {
        ItemEntity itemEntity = new ItemEntity();
        itemEntity.setIdx(dto.getIdx());
        itemEntity.setName(dto.getName());
        itemEntity.setCategory(category);// entity 로 주입
        itemEntity.setPrice(dto.getPrice());
        itemEntity.setContents(dto.getContents());
        itemEntity.setFileAttached(1); //파일 없음
        return itemEntity;
    }

}
