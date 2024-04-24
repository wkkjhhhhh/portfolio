package portfolio.test1.Repositiry;

import org.springframework.data.jpa.repository.JpaRepository;
import portfolio.test1.entity.OAuthUserEntity;

public interface OAuthUserRepository extends JpaRepository<OAuthUserEntity,Long> {


    OAuthUserEntity findByUsername(String username);
}
