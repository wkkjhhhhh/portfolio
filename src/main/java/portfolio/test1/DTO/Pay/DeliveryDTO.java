package portfolio.test1.DTO.Pay;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class DeliveryDTO {

    private Long idx;
    //구매한 회원
    private String username;
    //우편번호
    private String address1;
    //도로명주소
    private String address2;
    //지번주소
    private String address3;
    //상세정보
    private String address4;
    //전화번호 ***-****-****
    private String tel1;
    private String tel2;
    private String tel3;
    //배송메세지
    private String contents;

}
