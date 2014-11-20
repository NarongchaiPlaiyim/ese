package com.ese.security;

import org.slf4j.Logger;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.web.authentication.WebAuthenticationDetails;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import java.util.ArrayList;
import java.util.List;

//@ManagedBean
//@RequestScoped
public class SimpleAuthenticationManager implements AuthenticationManager {
//    @Inject
//    @DefaultLog
//    Logger log;

//    @Inject
    public SimpleAuthenticationManager() {
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        UserDetail userDetail = (UserDetail) authentication.getPrincipal();
//        log.debug("authenticate: {}", userDetail);

        WebAuthenticationDetails authenticationDetails = (WebAuthenticationDetails) authentication.getDetails();

        // system role
        if (userDetail.getRole().equalsIgnoreCase("ADMIN")) {
//            log.debug("system role.");
            return getAuthority(userDetail, authentication, authenticationDetails);
        }

        // business role
        if (userDetail.getRole().equalsIgnoreCase("USER")) {
//            log.debug("business role.");
            return getAuthority(userDetail, authentication, authenticationDetails);
        }

        throw new BadCredentialsException("Bad Credentials");
    }

    private UsernamePasswordAuthenticationToken getAuthority(UserDetail userDetail, Authentication authentication, WebAuthenticationDetails authenticationDetails) {
        List<GrantedAuthority> grantedAuthorities = new ArrayList<GrantedAuthority>();
        grantedAuthorities.add(new SimpleGrantedAuthority(userDetail.getRole()));
        UsernamePasswordAuthenticationToken result = new UsernamePasswordAuthenticationToken(userDetail,
                authentication.getCredentials(), grantedAuthorities);
        result.setDetails(authenticationDetails);
        return result;
    }
}

