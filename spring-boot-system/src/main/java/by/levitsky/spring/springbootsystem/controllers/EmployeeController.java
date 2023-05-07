package by.levitsky.spring.springbootsystem.controllers;

import by.levitsky.spring.springbootsystem.model.Employee;
import by.levitsky.spring.springbootsystem.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/employees")
public class EmployeeController {
    @Autowired
    private EmployeeService employeeService;

    @GetMapping("")
    public String getAllEmployeesPage(Model model) {
        model.addAttribute("employees", employeeService.getAllEmployees());
        return "employees-main";
    }

    @GetMapping("/add")
    public String getAddEmployeeDialog(Model model) {
        Employee employee = new Employee();
        model.addAttribute("employee", employee);
        return "employee-add";
    }

    @PostMapping("")
    public String saveEmployee(@ModelAttribute("employee") Employee employee) {
        employeeService.create(employee);
        return "redirect:/employees";
    }

    @GetMapping("/{id}")
    public String getPostByID(@PathVariable(value = "id") long id, Model model) {
        if(employeeService.getEmployeeById(id).isEmpty()){
            return "redirect:/employees";
        }

        model.addAttribute("employee", employeeService.getEmployeeById(id).get());
        return "employee-details";
    }

    @GetMapping("/{id}/edit")
    public String editPost(@PathVariable("id") long id, Model model) {
        if(employeeService.getEmployeeById(id).isEmpty()){
            return "redirect:/employees";
        }

        model.addAttribute("employee", employeeService.getEmployeeById(id).get());

        return "employee-edit";
    }

    @PostMapping("/{id}")
    public String saveEditedPost(@PathVariable("id") long id, @ModelAttribute("employee") Employee employee, Model model){
        employeeService.create(employee);
        return "redirect:/employees";
    }

    @GetMapping("/{id}/remove")
    public String deleteEmployee(@PathVariable("id") long id){
        employeeService.deleteById(id);
        return "redirect:/employees";
    }
}
