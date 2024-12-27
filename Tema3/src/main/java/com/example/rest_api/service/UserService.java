package com.example.rest_api.service;

import com.example.rest_api.database.primary.model.RoleEntity;
import com.example.rest_api.database.primary.model.UserEntity;
import com.example.rest_api.database.primary.repository.RoleRepository;
import com.example.rest_api.database.primary.repository.UserRepository;
import com.example.rest_api.security.AuthenticatedUser;
import com.example.rest_api.security.PasswordGeneratorUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserRequest;
import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service

@Transactional("primaryTransactionManager")
public class UserService extends OidcUserService implements UserDetailsService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private Logger logger = LoggerFactory.getLogger(UserService.class);

    @Autowired
    public UserService(UserRepository userRepository, RoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
    }

    /* Authentication methods */
    /**
     * Classic Auth.
     * @param email
     * @return
     * @throws UsernameNotFoundException
     */
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        UserEntity entity = userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found: " + email));

        // Convert roles to authorities...
        Collection<? extends GrantedAuthority> authorities = entity.getRoles().stream()
                .map(role -> new SimpleGrantedAuthority(role.getName()))
                .toList();

        // Return AuthenticatedUser
        //AuthenticatedUser authenticatedUser = new AuthenticatedUser(authorities, Map.of(), entity.getEmail());
        //authenticatedUser.setPassword(entity.getPassword());
        /* Constructor based on UserDetails */
        List<SimpleGrantedAuthority> simpleGrantedAuthorities = new ArrayList<>();
        return new AuthenticatedUser(entity.getUsername(), entity.getPassword(), simpleGrantedAuthorities);
    }

    /**
     * Used for oAuth Auth.
     * @param userRequest
     * @return
     * @throws OAuth2AuthenticationException
     */
    @Override
    public OidcUser loadUser(OidcUserRequest userRequest) throws OAuth2AuthenticationException {
        OidcUser oidcUser = super.loadUser(userRequest);

        String email = oidcUser.getEmail();
        String name = oidcUser.getFullName();

        Optional<UserEntity> optUser = userRepository.findByEmail(email);
        if (optUser.isEmpty()) {
            Optional<RoleEntity> userRole = roleRepository.findByName("USER");
            if (userRole.isEmpty()) {
                throw new RuntimeException("Role 'USER' not found in the database!");
            }

            UserEntity newUser = new UserEntity();
            newUser.setEmail(email);
            newUser.setUsername(name);
            newUser.setPassword(PasswordGeneratorUtil.generate());
            newUser.setIsOAuthAccount(true);

            // Initialize roles collection and add the user role
            List<RoleEntity> roles = new ArrayList<>();
            roles.add(userRole.get());
            newUser.setRoles(roles);

            logger.info("CURRENT OAUTH USER SAVED WITH PASSWORD: {}", newUser.getPassword());
            this.save(newUser);
        }

        AuthenticatedUser authenticatedUser = new AuthenticatedUser(
                oidcUser.getAuthorities(),
                oidcUser.getAttributes(),
                email
        );
        return authenticatedUser;
    }


    /* Queries */
    public UserEntity save(UserEntity user) {
        /* Encrypt password */
        user.setPassword(new BCryptPasswordEncoder().encode(user.getPassword()));
        return userRepository.save(user);
    }

    public Boolean existsByEmail(String email){
        return this.userRepository.existsByEmail(email);
    }

    public List<UserEntity> getAllUsers() {
        return userRepository.findAll();
    }

    public UserEntity getUserById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found!"));
    }
}
