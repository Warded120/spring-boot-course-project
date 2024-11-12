package com.ivan.course.service.superUser;

import com.ivan.course.entity.Keys;
import com.ivan.course.entity.superuser.SuperUser;
import com.ivan.course.repo.SuperUserRepository;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.web.client.RestTemplateAutoConfiguration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SuperUserServiceImpl implements SuperUserService {

    private final RestTemplateAutoConfiguration restTemplateAutoConfiguration;
    SuperUserRepository superUserRepository;
    BCryptPasswordEncoder passwordEncoder;
    HttpSession theSession;

    @Autowired
    public SuperUserServiceImpl(SuperUserRepository superUserRepository, BCryptPasswordEncoder passwordEncoder, HttpSession theSession, RestTemplateAutoConfiguration restTemplateAutoConfiguration) {
        this.superUserRepository = superUserRepository;
        this.passwordEncoder = passwordEncoder;
        this.theSession = theSession;
        this.restTemplateAutoConfiguration = restTemplateAutoConfiguration;
    }

    @Override
    public SuperUser save(SuperUser superUser) {
        return save(superUser, true, true);
    }

    @Override
    public SuperUser save(SuperUser superUser, boolean isEncodePassword) {
        return save(superUser, isEncodePassword, true);
    }

    @Override
    public SuperUser save(SuperUser superUser, boolean isEncodePassword, boolean isSetSession) {

        if(isSetSession) {
            theSession.setAttribute("superUser", superUser);
        }

        if(isEncodePassword) {
            superUser.setPassword(new Keys(passwordEncoder.encode(superUser.getPassword().getPassword())));
        }

        return superUserRepository.save(superUser);
    }

    @Override
    public void saveAll(List<SuperUser> superUserList) {
        superUserList.forEach(user -> save(user, true, false));
    }

    @Override
    public boolean existsByUsername(String username) {

        SuperUser byUsername = superUserRepository.findByUsername(username);

        return byUsername != null;
    }

    @Override
    public SuperUser findById(int id) {
        return superUserRepository.findById(id).orElse(null);
    }

    @Override
    public boolean existsByUserId(int id) {
        return superUserRepository.existsById(id);
    }
}
