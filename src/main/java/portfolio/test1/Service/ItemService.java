package portfolio.test1.Service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import net.coobird.thumbnailator.Thumbnailator;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import portfolio.test1.DTO.*;
import portfolio.test1.Repositiry.*;
//import portfolio.test1.entity.CartEntity;
import portfolio.test1.entity.CartEntity;
import portfolio.test1.entity.CategoryEntity;
import portfolio.test1.entity.ItemEntity;
import portfolio.test1.entity.ItemFileEntity;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Log4j2
@Service
@RequiredArgsConstructor
public class ItemService {
    private final ItemRepository itemRepository;
    private final ItemFileRepository itemFileRepository;
    private final CategoryRepository categoryRepository;
    private final CartRepository cartRepository;


    @Value("${save.path}")
    private String path;


    public void save(ItemCreateDTO dto) throws IOException {
        //카테고리값 24-03-27
        CategoryEntity category = categoryRepository.findById(dto.getThird()).orElse(null);

        if(dto.getItemFile().isEmpty()) { //파일이 없을때
            ItemEntity saveEntity = ItemEntity.toSaveEntity(dto,category);
            itemRepository.save(saveEntity);
        }else {//파일이 있을때
            ItemEntity itemEntity = ItemEntity.toSaveFileEntity(dto,category);
            Long saveIdx = itemRepository.save(itemEntity).getIdx();
            ItemEntity item = itemRepository.findById(saveIdx).get();
            File originalFile = null;
            List<String> ThumbFile = new ArrayList<>();

            for (MultipartFile itemFile: dto.getItemFile()) {
               // MultipartFile itemFile = dto.getItemFile(); //dto에 담긴 파일 꺼내기

                String originalFilename = itemFile.getOriginalFilename(); // 파일의 O_이름 가져오기
                String storedFileName = System.currentTimeMillis() + "_" + originalFilename; // 파일의 s_이름 가져오기
                ThumbFile.add(storedFileName);
                String savePath = path + storedFileName; //저장경로 설정
                originalFile = new File(savePath);
                itemFile.transferTo(originalFile); //경로에 파일 저장

                ItemFileEntity itemFileEntity =
                        ItemFileEntity.toItemFileEntity(
                                item,
                                originalFilename,
                                storedFileName);
                itemFileRepository.save(itemFileEntity);

            }
            originalFile = new File(path +ThumbFile.get(0));

            ////////썸네일
            String thum = "Thumb_" +ThumbFile.get(0);
            String thumbPath= path +"Thumb/" + thum; // 썸네일 경로
            File files = new File(thumbPath);
            Thumbnailator.createThumbnail(originalFile,files,300, 300);
        }
    }
    //Admin 에서 모든 상품 확인
    public List<ItemListDTO> findAll() {
        //카테고리

        List<ItemEntity> list = itemRepository.findAll(Sort.by(Sort.Order.desc("idx")));

        List<ItemListDTO> listDTOS = new ArrayList<>();

        for(int i=0; i<list.size(); i++) {
            ItemListDTO dto = ItemListDTO.toChange(list.get(i));
            listDTOS.add(dto);
        }
        return listDTOS;
    }
    /**
     * 수정 view
     * @param idx 아이템의 idx
     * @return DTO
     */
    public ItemDTO findById(Long idx) {
       ItemEntity item = itemRepository.findById(idx).orElse(null);

        ItemDTO dto = null;
        if (item != null) {
            dto = ItemDTO.toModify(item);
        }
        return dto;

    }


    public void delete(Long idx) {
        List<ItemFileEntity> itemImg = itemFileRepository.findByItemEntity_Idx(idx);
        ItemEntity item = itemRepository.findById(idx).get();
        String thumb = "Thumb_"+itemImg.get(0).getStoredFileName();
        for(int i=0; i<itemImg.size(); i++) {
            String FilePath = itemImg.get(i).getStoredFileName();
            File file = new File(path + FilePath);

            if (file.exists()) {
                file.delete();
            }
        }
        //썸네일 삭제
        File Thumb = new File(path+"Thumb/"+thumb);
        if(Thumb.exists()) {
            Thumb.delete();
        }

        itemRepository.delete(item);
    }
    @Transactional
    public Page<ItemListDTO> paging(Long idx, Pageable pageable) {
        int page = pageable.getPageNumber() -1;
        int pageLimit = 8; // 한 페이지에 보여줄 글 갯수
        // 한 페이지당 pageLimit 개 씩 글을 뵤여주고 정렬 기준은 idx 기준으로 내림차순 정렬
        // page 위치에 있는 값은 0부터 시작 이므로 -1
        CategoryEntity categoryEntity = categoryRepository.findById(idx).orElse(null);


        List<ItemEntity> items = categoryEntity.getItems();

        List<ItemListDTO> dtos = new ArrayList<>();
        for (int i = 0; i < items.size(); i++) {
            ItemListDTO dto = ItemListDTO.toChange(items.get(i));
            dtos.add(dto);
        }
        Page<ItemListDTO> dtoPage = new PageImpl<>(dtos, PageRequest.of(page,pageLimit,Sort.by(Sort.Direction.DESC ,"idx")), dtos.size());


        return dtoPage;
    }

    public void saveCart(String username, Long idx, int quantity) {
        ItemEntity item = itemRepository.findById(idx).orElse(null);


        CartDTO dto = new CartDTO();
        dto.setItem(item);
        dto.setUsername(username);
        dto.setQuantity(quantity);

        CartEntity cartEntity = CartEntity.toSave(dto);
        cartRepository.save(cartEntity);
    }

    public List<CartDTO> findByUsername(String username) {
        List<CartEntity> cartEntities = cartRepository.findByUsername(username);
        List<CartDTO> cart = new ArrayList<>();
        for (CartEntity cartEntity : cartEntities) {
            cart.add(CartDTO.toChange(cartEntity));
        }
        return cart;
    }
}
