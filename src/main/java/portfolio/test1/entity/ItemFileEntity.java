package portfolio.test1.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Setter
@Getter
@Table(name = "item_file_table")
public class ItemFileEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idx;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "item_id")
    private ItemEntity itemEntity;

    private String originalFileName;

    private String storedFileName;



    public static ItemFileEntity toItemFileEntity(ItemEntity itemEntity, String originalFileName, String storedFileName) {
        ItemFileEntity itemFileEntity = new ItemFileEntity();
        itemFileEntity.setOriginalFileName(originalFileName);
        itemFileEntity.setStoredFileName(storedFileName);
        itemFileEntity.setItemEntity(itemEntity);
        return itemFileEntity;
    }
}


