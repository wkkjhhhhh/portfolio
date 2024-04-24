package portfolio.test1.config;


import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import portfolio.test1.entity.MemberEntity;


import java.util.ArrayList;
import java.util.Collection;

public class CustomUserDetails implements UserDetails {
    private final MemberEntity memberEntity;

    public  CustomUserDetails(MemberEntity memberEntity) {
        this.memberEntity = memberEntity;
    }


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {

        Collection<GrantedAuthority> collection = new ArrayList<>();
        collection.add(new GrantedAuthority() {
            @Override
            public String getAuthority() {
                return memberEntity.getRole();
            }
        });

        return collection;
    }

    @Override
    public String getPassword() {
        return memberEntity.getPwd();
    }

    @Override
    public String getUsername() {
        return memberEntity.getUserid();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
