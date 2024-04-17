package authservice.security;

import authservice.entity.User;
import authservice.enums.AccountStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class UserDetailsImpl implements UserDetails {
  private transient User user;
  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    List<SimpleGrantedAuthority> simpleGrantedAuthorityList = new ArrayList<>();
    for (String role : user.getRoles()) {
      simpleGrantedAuthorityList.add(new SimpleGrantedAuthority(role));
    }
    return simpleGrantedAuthorityList;
  }

  @Override
  public String getPassword() {
    return user.getPassword();
  }

  @Override
  public String getUsername() {
    return user.getEmail();
  }

  public List<String> getRole() {
    return user.getRoles();
  }

  public Boolean isVerified() {
    return user.isVerify();
  }

  public Boolean isActive() {
    return user.getStatus().equals(AccountStatus.ACTIVE);
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
