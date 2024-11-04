package com.ivan.course.service.superUserData;

import com.ivan.course.entity.superuser.SuperUserData;
import com.ivan.course.repo.SuperUserDataRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SuperUserDataServiceImpl implements SuperUserDataService {

    SuperUserDataRepository superUserDataRepository;

    @Autowired
    public SuperUserDataServiceImpl(SuperUserDataRepository superUserDataRepository) {
        this.superUserDataRepository = superUserDataRepository;
    }

    @Override
    public void save(SuperUserData superUserData) {
        superUserDataRepository.save(superUserData);
    }
}
