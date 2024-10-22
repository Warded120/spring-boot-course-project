package com.ivan.course.service.debt;

import com.ivan.course.entity.Debt;
import com.ivan.course.repo.DebtRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DebtServiceImpl implements DebtService {

    private DebtRepository debtRepository;

    @Autowired
    public DebtServiceImpl(DebtRepository debtRepository) {
        this.debtRepository = debtRepository;
    }

    @Override
    public Debt save(Debt debt) {
        return debtRepository.save(debt);
    }
}
