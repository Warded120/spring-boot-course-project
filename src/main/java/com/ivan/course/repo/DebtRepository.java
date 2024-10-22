package com.ivan.course.repo;

import com.ivan.course.entity.Debt;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DebtRepository extends JpaRepository<Debt, Integer> {
}
