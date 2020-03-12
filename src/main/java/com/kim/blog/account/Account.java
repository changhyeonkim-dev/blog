package com.kim.blog.account;

import com.kim.blog.BaseAuditor;
import com.kim.blog.comment.Comment;
import com.kim.blog.post.Post;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.io.Serializable;
import java.util.*;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Account extends BaseAuditor implements UserDetails, Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(unique = true)
    private Long id;

    @Column(nullable = false, unique = true)
    private String userId;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private boolean isSocialUser;

    private String name;

    private String selfDescription;

    @OneToMany(mappedBy = "account",fetch = FetchType.LAZY)
    private List<Post> postList = new ArrayList<>();

    @OneToMany(mappedBy = "account",fetch = FetchType.LAZY)
    private List<Comment> commentList = new ArrayList<>();

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Role role;


    @Override
    @Transient
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Set<GrantedAuthority> grantedAuthorities = new HashSet<>();
        if(userId!=null && userId.equals("admin")){
            grantedAuthorities.add(new SimpleGrantedAuthority(Role.ADMIN.name()));
        }else{
            grantedAuthorities.add(new SimpleGrantedAuthority(Role.GUEST.name()));
        }
        return grantedAuthorities;
    }

    @Override
    @Transient
    public String getUsername() {
        return this.userId;
    }

    @Override
    @Transient
    public boolean isAccountNonExpired() { //계정이 만료되지 않았는지 리턴 true-> 만료되지 않음
        return true;
    }

    @Override
    @Transient
    public boolean isAccountNonLocked() { //계정이 잠기지 않았는지 리턴 true-> 잠기지 않음
        return true;
    }

    @Override
    @Transient
    public boolean isCredentialsNonExpired() { //계정의 패스워드가 만료 되지 않았는지 리턴 true-> 만료되지않음
        return true;
    }

    @Override
    @Transient
    public boolean isEnabled() { //사용가능한 계정인지 의미
        return true;
    }

}
