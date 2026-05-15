package com.example.cyclemarket.services.entity;

import com.example.cyclemarket.dto.CreateEmployeeReq;
import com.example.cyclemarket.dto.auth.AuthReq;
import com.example.cyclemarket.entities.Employee;
import com.example.cyclemarket.entities.Shop;
import com.example.cyclemarket.entities.User;
import com.example.cyclemarket.exception.notfound.EmployeeNotFoundException;
import com.example.cyclemarket.repos.EmployeeRepo;
import com.example.cyclemarket.services.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class EmployeeService {
    private final EmployeeRepo employeeRepo;
    private final ShopService shopService;
    private final AuthService authService;

    @Transactional
    public void createEmployee(CreateEmployeeReq employeeReq, AuthReq authReq) {
        User user = authService.registerEmployee(authReq.getUsername(), authReq.getPassword());

        Employee employee = new Employee();
        employee.setFirstName(employeeReq.getFirstName());
        employee.setLastName(employeeReq.getLastName());
        employee.setPhoneNumber(employeeReq.getPhoneNumber());
        employee.setShop(shopService.getById(employeeReq.getShopId()));
        employee.setUser(user);

        employeeRepo.save(employee);
    }

    public Long getShopIdByEmployeeName(String name) {
        return employeeRepo.findByUserEmail(name)
                .orElseThrow(EmployeeNotFoundException::new)
                .getShop()
                .getId();
    }
    public Shop getCurrentManagerShop(String name) {
        return employeeRepo.findByUserEmail(name)
                .orElseThrow(EmployeeNotFoundException::new)
                .getShop();
    }
}
