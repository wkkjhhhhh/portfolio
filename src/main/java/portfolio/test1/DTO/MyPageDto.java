package portfolio.test1.DTO;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class MyPageDto {

    private String userid;
    private String name;
    private String phone;
    private String email;
    private int deposit;

}
