package portfolio.test1.Repositiry;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import portfolio.test1.entity.MemberEntity;


import java.util.Optional;

public interface MemberRepository extends JpaRepository<MemberEntity,Long> {
    Optional<MemberEntity> findByUserid(String userid);

    boolean existsByUserid(String userId);

}
