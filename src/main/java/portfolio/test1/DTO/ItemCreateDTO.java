package portfolio.test1.DTO;

import lombok.*;
import org.springframework.web.multipart.MultipartFile;
import portfolio.test1.entity.CategoryEntity;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@ToString
@AllArgsConstructor // 모든 필드를 매개변수로 하는 생성자
@NoArgsConstructor //기본 생성자
public class ItemCreateDTO {
    private Long idx;
    private String name;
    private int price;
    private String contents;




   // 24-03-27
   private Long third;
   /* private String first;
    private String second;
    private String third;
    //
    private String categoryName;// 너는 이름이다*/



    private LocalDateTime createdTime;
    private LocalDateTime updatedTime;
    //private MultipartFile thumbnail;
    private List<MultipartFile> itemFile;

    private String originalFileName;
    private String storedFileName;
    private int fileAttached;  //파일 첨부 여부(첨부 1, 미첨부 0)
}
