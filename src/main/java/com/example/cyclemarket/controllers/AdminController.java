package com.example.cyclemarket.controllers;

import com.example.cyclemarket.dto.CreateEmployeeReq;
import com.example.cyclemarket.dto.CreateShopReq;
import com.example.cyclemarket.dto.auth.AuthReq;
import com.example.cyclemarket.services.EmployeeService;
import com.example.cyclemarket.services.ShopService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin")
@RequiredArgsConstructor
public class AdminController {
    private final ShopService shopService;
    private final EmployeeService employeeService;

    @GetMapping
    public String getAdminPage() {
        return "admin/admin";
    }

    @GetMapping("/employees/new")
    public String getNewEmployeePage(Model model) {
        model.addAttribute("employeeForm", new CreateEmployeeReq());
        model.addAttribute("registerForm", new AuthReq());
        model.addAttribute("shops", shopService.getAllShops());
        return "admin/employee-new";
    }

    @PostMapping("/employees/new")
    public String createEmployee(@ModelAttribute("employeeForm") CreateEmployeeReq createEmployeeReq,
                                 @ModelAttribute("registerForm") AuthReq authReq, Model model) {
        try {
            employeeService.createEmployee(createEmployeeReq, authReq);
        } catch (Exception e) {
            model.addAttribute("error", e.getMessage());
            model.addAttribute("employeeForm", createEmployeeReq);
            model.addAttribute("registerForm", authReq);
            return "admin/employee-new";
        }
        return "redirect:/admin";
    }

    @GetMapping("/shops/new")
    public String getNewShopPage(Model model) {
        model.addAttribute("shopForm", new CreateShopReq());
        return "admin/shop-new";
    }

    @PostMapping("/shops/new")
    public String createShop(@ModelAttribute("shopForm") CreateShopReq createShopReq) {
        shopService.createShop(createShopReq);
        return "redirect:/admin";
    }

    @GetMapping("/shops")
    public String getAllShopsPage(Model model) {
        model.addAttribute("shops", shopService.getAllShops());
        return "admin/shops";
    }


}
