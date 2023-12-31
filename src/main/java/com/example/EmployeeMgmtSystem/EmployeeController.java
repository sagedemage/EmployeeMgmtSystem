/*
 * Employee Rest APIs
 */

package com.example.EmployeeMgmtSystem;

import com.example.EmployeeMgmtSystem.request_bodies.MessageStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "http://localhost:8080")
@RequestMapping(path = "/employee")
public class EmployeeController {
    @Autowired
    private EmployeeRepository employeeRepository;

    /* CRUD Employee APIs */
    @PostMapping(path = "/add", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public MessageStatus addNewEmployee(@RequestBody Employee employeeBody) {
        /* Add employee api with data (CREATE)
         * The required attributes: name, email, and phone_number
         */

        Employee n = new Employee();

        // Error handling for required attributes
        if (employeeBody.getName() == null) {
            return new MessageStatus("Error", "Name is empty");
        } else if (employeeBody.getEmail() == null) {
            return new MessageStatus("Error", "Email is empty");
        } else if (employeeBody.getPhone_number() == null) {
            return new MessageStatus("Error", "Phone number is empty");
        }

        n.setName(employeeBody.getName());
        n.setEmail(employeeBody.getEmail());
        n.setPhone_number(employeeBody.getPhone_number());
        employeeRepository.save(n);
        return new MessageStatus("Success", "Added");
    }

    @GetMapping(path = "fetch", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public Employee fetchEmployee(@RequestParam int id) {
        /* Fetch employee api with data of the employee (READ)
         * The required attributes: id
         */

        Employee employee = employeeRepository.findById(id).get();
        return employee;
    }

    @PatchMapping(path = "/update", consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody MessageStatus updateEmployee(@RequestBody Employee employeeBody) {
        /* Update employee api with updates the data of employee (UPDATE)
         * The required attributes: id, name, email, and phone_number
         */

        // Error handling for required attributes
        if (employeeBody.getId() == null) {
            return new MessageStatus("Error", "Id is empty");
        }
        else if (employeeBody.getName() == null) {
            return new MessageStatus("Error", "Name is empty");
        } else if (employeeBody.getEmail() == null) {
            return new MessageStatus("Error", "Email is empty");
        } else if (employeeBody.getPhone_number() == null) {
            return new MessageStatus("Error", "Phone number is empty");
        }

        Employee employee = employeeRepository.findById(employeeBody.getId()).get();
        employee.setName(employeeBody.getName());
        employee.setEmail(employeeBody.getEmail());
        employee.setPhone_number(employeeBody.getPhone_number());
        employeeRepository.save(employee);
        return new MessageStatus("Success", "Updated");
    }

    @DeleteMapping(path = "/delete", produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody MessageStatus removeEmployee(@RequestParam int id) {
        /* Delete employee api (DELETE)
         * The required attributes: id
         */
        employeeRepository.deleteById(id);
        return new MessageStatus("Success", "Deleted");
    }

    @GetMapping(path = "/fetch-all", produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody Iterable<Employee> fetchEmployees() {
        /* Fetch all employee api to get a list of all employees
         */
        Iterable<Employee> employees = employeeRepository.findAll();
        return employees;
    }
}
