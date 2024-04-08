package portfolio.test1.DTO;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class MyUserDto {

    private String userid;
    private String name;
    private String phone;
    private String email;
    private int deposit;

}
