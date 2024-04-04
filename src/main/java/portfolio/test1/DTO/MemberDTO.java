package portfolio.test1.DTO;


import lombok.*;
import portfolio.test1.entity.MemberEntity;


import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class MemberDTO { ////////////member
    private Long idx;
    private String userid;

    private String pwd;

    private String name;

    private String phone1;
    private String phone2;
    private String phone3;

    private String email;
    private int deposit;
    private String role;
    private LocalDateTime createdTime;
    private LocalDateTime updatedTime;


    public static  MemberDTO toMemberDTO(MemberEntity entity) {
        MemberDTO memberDTO = new MemberDTO();
        memberDTO.setIdx(entity.getIdx());
        memberDTO.setUserid(entity.getUserid());
        memberDTO.setPwd(entity.getPwd());
        memberDTO.setName(entity.getName());

        String str = entity.getPhone();
        String [] strArr = str.split("-");

        memberDTO.setPhone1(strArr[0]);
        memberDTO.setPhone2(strArr[1]);
        memberDTO.setPhone3(strArr[2]);

        memberDTO.setEmail(entity.getEmail());
        memberDTO.setDeposit(entity.getDeposit());
        memberDTO.setCreatedTime(entity.getCreatedTime());

        return memberDTO;
    }

}
