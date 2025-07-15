package Lectra.UserAuth.AuthUtilities;

import Lectra.UserAuth.POJOs.LectraUser;
import Lectra.UserAuth.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class CustomBasicAuthProvider implements AuthenticationProvider{
    @Autowired
    private final UserRepo userRepo;

    public CustomBasicAuthProvider(UserRepo userRepo) {
        this.userRepo = userRepo;
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String username=(String)authentication.getName();
        if(userRepo.existsById(username)){
            List<GrantedAuthority> roles=userRepo.findRolesById(username).stream().map(SimpleGrantedAuthority::new).collect(Collectors.toList());
            return new UsernamePasswordAuthenticationToken(username,"bababoeyy",roles);
        }
        else{
            userRepo.save(new LectraUser(username, username, List.of("STUDENT")));
            return new UsernamePasswordAuthenticationToken(username,"bababoeyy",List.of(()->"STUDENT"));
        }
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication);
    }
}
