package com.example.cyclemarket.repos;

import com.example.cyclemarket.entities.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface EmployeeRepo extends JpaRepository<Employee, Integer> {
    Optional<Employee> findByUserEmail(String name);

    List<Employee> findAllByActiveIsTrue();

    List<Employee> findAllByActiveIsFalse();
}
