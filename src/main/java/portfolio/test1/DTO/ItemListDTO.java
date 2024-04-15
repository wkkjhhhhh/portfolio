package portfolio.test1.DTO;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import portfolio.test1.entity.ItemEntity;

import java.time.LocalDateTime;


@Getter
@Setter
@ToString
@NoArgsConstructor
public class ItemListDTO {

    private Long idx;
    private String name;
    private int price;
    private String contents;
    private String storedFile;
    private LocalDateTime updatedTime;
    private int fileAttached;



   public static ItemListDTO toChange(ItemEntity itemEntity) {
        ItemListDTO itemListDTO = new ItemListDTO();
        itemListDTO.setIdx(itemEntity.getIdx());
        itemListDTO.setName(itemEntity.getName());
        itemListDTO.setPrice(itemEntity.getPrice());
        itemListDTO.setContents(itemEntity.getContents());
        itemListDTO.setStoredFile(itemEntity.getItemFileEntities().get(0).getStoredFileName());
        itemListDTO.setUpdatedTime(itemEntity.getUpdatedTime());
        itemListDTO.setFileAttached(itemEntity.getFileAttached());

        return itemListDTO;
    }
}
