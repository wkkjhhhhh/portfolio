package portfolio.test1.Service;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import net.coobird.thumbnailator.Thumbnailator;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import portfolio.test1.DTO.CategoryDTO;
import portfolio.test1.DTO.ItemCreateDTO;
import portfolio.test1.Repositiry.CategoryRepository;
import portfolio.test1.Repositiry.ItemFileRepository;
import portfolio.test1.Repositiry.ItemRepository;
import portfolio.test1.entity.CategoryEntity;
import portfolio.test1.entity.ItemEntity;
import portfolio.test1.entity.ItemFileEntity;

import java.io.File;
import java.io.IOException;
import java.util.List;

@Service
@Log4j2
@RequiredArgsConstructor
public class ItemModifyService {
    private final ItemRepository itemRepository;
    private final ItemFileRepository itemFileRepository;
    private final CategoryRepository categoryRepository;

    @Value("${save.path}")
    private String path;

    public void update(ItemCreateDTO dto, List<String> check ) throws IOException {
        CategoryEntity categoryEntity = categoryRepository.findById(dto.getThird()).orElse(null);
        List<MultipartFile> itemFile = dto.getItemFile();
        ItemEntity saveEntity = ItemEntity.toSaveFileEntity(dto,categoryEntity); // 바꾼것

        // 체크가 없을 때
        for (int i = 0; i < check.size(); i++) {
            if (check.get(i).equals("checked")) {
                if (!itemFile.get(i).isEmpty()) {
                    //삭제 하고 파일 업로드 하고 세이브
                    List<ItemFileEntity> item = itemFileRepository.findByItemEntity_Idx(dto.getIdx());
                    String filePath = item.get(i).getStoredFileName();
                    File file = new File(path + filePath);

                    if (file.exists()) {
                        file.delete();
                    }
                    MultipartFile NewFile = dto.getItemFile().get(i);
                    String originalFileName = itemFile.get(i).getOriginalFilename();
                    String storedFileName = System.currentTimeMillis() + "_" + originalFileName;
                    String savePath = path + storedFileName;
                    NewFile.transferTo(new File(savePath));

                    if(i ==0) {
                        String thumb = "Thumb_"+item.get(0).getStoredFileName();
                        File thumbnail = new File(path + "Thumb/" + thumb);

                        thumbnail.delete();

                        String thum = "Thumb_" + storedFileName;
                        String thumbPath= path +"Thumb/" + thum; // 썸네일 경로
                        File files = new File(thumbPath);
                        Thumbnailator.createThumbnail(new File(savePath),files,300, 300);

                    }

                    item.get(i).setOriginalFileName(originalFileName);
                    item.get(i).setStoredFileName(storedFileName);

                    itemFileRepository.save(item.get(i));

                } else {
                    if (i == 0) {
                        //돌아가
                        log.info("썸네일 읎으면 안된다");

                    } else {
                        //삭제  db 삭제 세이브
                        List<ItemFileEntity> item = itemFileRepository.findByItemEntity_Idx(dto.getIdx());
                        String filePath = item.get(i).getStoredFileName();
                        File file = new File(path + filePath);

                        if (file.exists()) {
                            file.delete();
                        }
                        item.get(i).setOriginalFileName("Null");
                        item.get(i).setStoredFileName("Null");
                        itemFileRepository.save(item.get(i));
                    }
                }
            }
        }
            //uncheck
            //그냥 세이브
            itemRepository.save(saveEntity);

    }
}


