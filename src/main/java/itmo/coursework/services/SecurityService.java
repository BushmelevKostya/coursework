package itmo.coursework.services;

import itmo.coursework.exceptions.UnauthorizedUserException;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.security.Principal;

@Component
public class SecurityService {

    public String findUserName() {
        Principal principal = SecurityContextHolder.getContext().getAuthentication();
        if (principal == null || principal.getName() == null) {
            throw new UnauthorizedUserException(
                    "Информация о пользователе недоступна"
            );
        }
        return principal.getName();
    }


    public boolean hasAdminRole() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (!authentication.isAuthenticated() || authentication == null && authentication instanceof AnonymousAuthenticationToken) {
            return false;
        }

        return authentication.getAuthorities().stream()
                .anyMatch(grantedAuthority -> grantedAuthority.getAuthority().equals("ROLE_ADMIN"));
    }
}
