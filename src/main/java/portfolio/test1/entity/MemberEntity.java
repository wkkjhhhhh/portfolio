package portfolio.test1.entity;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import portfolio.test1.DTO.MemberDTO;


@Entity
@Getter
@Setter
@Table(name = "member")
public class MemberEntity extends BaseEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idx;

    private String userid;

    private String pwd;

    private String name;

    private String phone;

    private String email;

    private int deposit; //에치금

    private String role;



    public static MemberEntity toSaveEntity(MemberDTO dto) {
       MemberEntity memberEntity = new MemberEntity();
       memberEntity.setUserid(dto.getUserid());
       memberEntity.setPwd(dto.getPwd()); // 비밀번호
       memberEntity.setName(dto.getName());
       memberEntity.setPhone(dto.getPhone1()+"-"+dto.getPhone2()+"-"+dto.getPhone3());
       memberEntity.setEmail(dto.getEmail());
       memberEntity.setDeposit(0);
       memberEntity.setRole("ROLE_USER");

       return memberEntity;
    }


}
