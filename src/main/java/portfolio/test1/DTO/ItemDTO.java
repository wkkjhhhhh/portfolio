package portfolio.test1.DTO;

import lombok.*;
import org.springframework.web.multipart.MultipartFile;
import portfolio.test1.entity.CategoryEntity;
import portfolio.test1.entity.ItemEntity;
import portfolio.test1.entity.ItemFileEntity;


import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@ToString
@AllArgsConstructor // 모든 필드를 매개변수로 하는 생성자
@NoArgsConstructor //기본 생성자
public class ItemDTO {
    private Long idx;
    private String name;

    private CategoryEntity category;
    private int price;
    private String contents;
    private List<Long> c_Idx;
    //사진
    private List<MultipartFile> files;
    private List<String> originalFileName;
    private List<String> storedFileName;


    public static ItemDTO toModify(ItemEntity item) {
        ItemDTO dto = new ItemDTO();
        dto.setIdx(item.getIdx());
        dto.setName(item.getName());
        dto.setCategory(item.getCategory());
        dto.setPrice(item.getPrice());
        dto.setContents(item.getContents());
        if(item.getFileAttached() == 1) {
            List<String> originalFileNameList = new ArrayList<>();
            List<String> storedFileNameList = new ArrayList<>();
            //파일 이름 가지고오기
            for (ItemFileEntity itemFileEntity : item.getItemFileEntities()) {
                originalFileNameList.add(itemFileEntity.getOriginalFileName());
                storedFileNameList.add(itemFileEntity.getStoredFileName());
            }

            dto.setOriginalFileName(originalFileNameList);
            dto.setStoredFileName(storedFileNameList);
        }
        return dto;
    }

}
