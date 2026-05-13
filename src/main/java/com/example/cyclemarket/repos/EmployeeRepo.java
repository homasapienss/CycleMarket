package com.example.cyclemarket.repos;

import com.example.cyclemarket.entities.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmployeeRepo extends JpaRepository<Employee, Long> {
    Employee findByUserEmail(String name);
}
