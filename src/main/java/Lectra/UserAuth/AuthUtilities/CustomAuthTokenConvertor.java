package Lectra.UserAuth.AuthUtilities;

import Lectra.UserAuth.POJOs.LectraUser;
import Lectra.UserAuth.UserRepo;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.core.convert.converter.Converter;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;
//import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;

@Component
public class CustomAuthTokenConvertor implements Converter<Jwt, AbstractAuthenticationToken>{

    private final UserRepo userRepo;

    public CustomAuthTokenConvertor(UserRepo userRepo) {
        this.userRepo = userRepo;
    }


    @Override
    public AbstractAuthenticationToken convert(Jwt source) {
        String username=(String)source.getClaims().get("sub");
        String name="Test User";
//        String name=(String)source.getClaims().get("name");
        if(userRepo.existsById(username)){
            List<GrantedAuthority> roles=userRepo.findRolesById(username).stream().map(SimpleGrantedAuthority::new).collect(Collectors.toList());
            return new JwtAuthenticationToken(source,roles);
        }
        else{
            userRepo.save(new LectraUser(username, name, List.of("STUDENT")));
            return new JwtAuthenticationToken(source,List.of(()->"STUDENT"));
        }
    }
    }
