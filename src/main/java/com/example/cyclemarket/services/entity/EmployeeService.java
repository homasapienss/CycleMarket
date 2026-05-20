package com.example.cyclemarket.services.entity;

import com.example.cyclemarket.dto.CreateEmployeeReq;
import com.example.cyclemarket.dto.EditEmployeeReq;
import com.example.cyclemarket.dto.EmployeeView;
import com.example.cyclemarket.dto.auth.AuthReq;
import com.example.cyclemarket.entities.Employee;
import com.example.cyclemarket.entities.Role;
import com.example.cyclemarket.entities.Shop;
import com.example.cyclemarket.entities.User;
import com.example.cyclemarket.exception.notfound.EmployeeNotFoundException;
import com.example.cyclemarket.repos.EmployeeRepo;
import com.example.cyclemarket.services.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

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

    public List<EmployeeView> getEmployeesView(String filter) {
        if (filter == null || filter.isEmpty() || filter.equals("all")) {
            return employeeRepo.findAll()
                    .stream()
                    .map(this::mapEmployeeToView).toList();
        }
        if (filter.equals("active")) {
            return employeeRepo.findAllByActiveIsTrue()
                    .stream()
                    .map(this::mapEmployeeToView).toList();
        }
        if (filter.equals("inactive")) {
            return employeeRepo.findAllByActiveIsFalse()
                    .stream()
                    .map(this::mapEmployeeToView).toList();
        }
        return employeeRepo.findAll().stream()
                .map(this::mapEmployeeToView)
                .toList();
    }
    private EmployeeView mapEmployeeToView(Employee employee) {
        return EmployeeView.builder()
                .id(employee.getId())
                .roleName(employee.getUser()
                        .getRoles()
                        .stream()
                        .map(Role::getName)
                        .collect(Collectors.joining(", "))
                )
                .lastName(employee.getLastName())
                .firstName(employee.getFirstName())
                .shopName(employee.getShop().getShopName())
                .active(employee.isActive())
                .build();
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

    public EditEmployeeReq getEditEmployeeReq(Integer employeeId) {
        Employee byId = employeeRepo.findById(employeeId).orElseThrow(EmployeeNotFoundException::new);
        return EditEmployeeReq.builder()
                .phoneNumber(byId.getPhoneNumber())
                .firstName(byId.getFirstName())
                .lastName(byId.getLastName())
                .shopName(byId.getShop().getShopName())
                .employeeId(byId.getId())
                .build();
    }

    @Transactional
    public void editEmployee(EditEmployeeReq editEmployeeReq) {
        Employee employee = employeeRepo.findById(editEmployeeReq.getEmployeeId()).orElseThrow(EmployeeNotFoundException::new);
        employee.setFirstName(editEmployeeReq.getFirstName());
        employee.setLastName(editEmployeeReq.getLastName());
        employee.setPhoneNumber(editEmployeeReq.getPhoneNumber());
        employee.setShop(shopService.getShopByName(editEmployeeReq.getShopName()));
        employeeRepo.save(employee);
    }
}
