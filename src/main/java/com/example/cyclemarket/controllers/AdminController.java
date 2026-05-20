package com.example.cyclemarket.controllers;

import com.example.cyclemarket.dto.CreateEmployeeReq;
import com.example.cyclemarket.dto.CreateShopReq;
import com.example.cyclemarket.dto.EditEmployeeReq;
import com.example.cyclemarket.dto.EditShopReq;
import com.example.cyclemarket.dto.auth.AuthReq;
import com.example.cyclemarket.services.entity.EmployeeService;
import com.example.cyclemarket.services.entity.ShopService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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
    public String getShopsPanel(Model model,
                                @RequestParam(required = false) String filter) {
        model.addAttribute("shops", shopService.getShopsView(filter));
        return "admin/shops";
    }

    @GetMapping("/shops/edit/{id}")
    public String getShopEditPage(Model model,
                                  @PathVariable("id") Long shopId) {
        model.addAttribute("editShopReq", shopService.getEditShopReq(shopId));
        return "admin/shop-edit";
    }

    @PostMapping("/shops/edit")
    public String editShop(@ModelAttribute EditShopReq editShopReq,
                           RedirectAttributes redirectAttributes) {
        shopService.editShop(editShopReq);
        redirectAttributes.addFlashAttribute("successMessage", "Изменения магазина сохранены.");
        return "redirect:/admin/shops";
    }

    @GetMapping("/employees")
    public String getEmployeePanel(Model model,
                                   @RequestParam(required = false) String filter) {
        model.addAttribute("employees", employeeService.getEmployeesView(filter));
        return "admin/employees";
    }

    @GetMapping("/employees/edit/{id}")
    public String getEmployeeEditPage(Model model,
                                      @PathVariable("id") Integer employeeId) {
        model.addAttribute("editEmployeeReq", employeeService.getEditEmployeeReq(employeeId));
        model.addAttribute("shops", shopService.getAllShops());
        return "admin/employee-edit";
    }

    @PostMapping("/employees/edit")
    public String editEmployee(@ModelAttribute EditEmployeeReq editEmployeeReq,
                               RedirectAttributes redirectAttributes) {
        employeeService.editEmployee(editEmployeeReq);
        redirectAttributes.addFlashAttribute("successMessage", "Изменения сотрудника сохранены.");
        return "redirect:/admin/employees";
    }

}
