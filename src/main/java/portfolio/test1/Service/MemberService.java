package portfolio.test1.Service;


import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import portfolio.test1.DTO.MemberDTO;
import portfolio.test1.DTO.MyPageDto;
import portfolio.test1.Repositiry.MemberRepository;
import portfolio.test1.entity.MemberEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service

public class MemberService {

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;


    public MemberService(MemberRepository memberRepository, PasswordEncoder passwordEncoder) {


        this.memberRepository = memberRepository;
        this.passwordEncoder = passwordEncoder;
    }

    ///////회원가입
    public void save(MemberDTO dto) {
        Optional<MemberEntity> byId = memberRepository.findByUserid(dto.getUserid());

        if(byId.isEmpty()) {
            MemberEntity memberEntity = MemberEntity.toSaveEntity(dto);
            //패스워드 인코더
            memberEntity.setPwd(passwordEncoder.encode(memberEntity.getPwd()));
            memberRepository.save(memberEntity);
        }
    }
    //아이디 체크
    public boolean finduserid(String userid) {
       return memberRepository.existsByUserid(userid);
    }

    // view (my page)
    public MyPageDto findUserid(String username) {
       Optional<MemberEntity> member = memberRepository.findByUserid(username);

        if(member.isPresent()) {
            MemberEntity entity = member.get();

            return  MyPageDto.builder()
                    .userid(entity.getUserid())
                    .name(entity.getName())
                    .phone(entity.getPhone())
                    .email(entity.getEmail())
                    .deposit(entity.getDeposit())
                    .build();
       }else {

        return null;
       }
    }
    //list
    public List<MemberDTO> findAll() {
        List<MemberEntity> list = memberRepository.findAll(Sort.by(Sort.Order.desc("idx")));
        List<MemberDTO> memberDTOS = new ArrayList<>();

        for(MemberEntity memberEntity : list) {
            memberDTOS.add(MemberDTO.toMemberDTO(memberEntity));
        }
        return  memberDTOS;
    }

    public void delete(Long idx) {
        memberRepository.deleteById(idx);
    }
}
