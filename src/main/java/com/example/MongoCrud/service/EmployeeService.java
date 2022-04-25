package com.example.MongoCrud.service;

import com.example.MongoCrud.entity.Employee;
import com.example.MongoCrud.repository.EmployeeRepository;
import com.example.MongoCrud.request.EmployeeRequest;
import org.springframework.beans.factory.ListableBeanFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import java.util.EnumMap;
import java.util.List;

@Service
public class EmployeeService {

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private MongoTemplate mongoTemplate;

    public String addEmployee(EmployeeRequest employeeRequest) {

        Employee employee = new Employee();
        employee.setId(employeeRequest.getId());
        employee.setEmployeeName(employeeRequest.getEmployeeName());
        employee.setSalary(employeeRequest.getSalary());
        employee.setAge(employeeRequest.getAge());
        employeeRepository.save(employee);
        return "employee saved";
    }

    public List<Employee> getEmployee(EmployeeRequest employeeRequest) {
        return employeeRepository.findAll();
    }

    public Employee getEmployeeById(Integer id) {
        return employeeRepository.findById(id).orElse(null);
    }

    public String updateEmployee(EmployeeRequest employeeRequest) {
        Employee employee = employeeRepository.findById(employeeRequest.getId()).orElse(null);
        /// Employee employee=employeeRepository.findEmployeeByemployeeName(employeeRequest.getEmployeeName());
        System.out.println(employee);
        employee.setSalary(employeeRequest.getSalary());
        employeeRepository.save(employee);
        return "employee updated";
    }

    public String deleteEmployee(Integer id) {
        employeeRepository.deleteById(id);
        return "employee deleted";
    }

    public List<Employee> getEmployeeMaxSalary(Integer salary) {
        return employeeRepository.getEmployeeMaxSalary(salary);
    }

    public List<Employee> getEmployeeNameAndSalary(String name, Integer salary) {
        return employeeRepository.getEmployeeNameAndSalary(name, salary);
    }

    public List<Employee> getEmployeeNameOrSalary(String name, Integer salary) {
        return employeeRepository.getEmployeeNameOrSalary(name, salary);
    }

    public List<Employee> getEmployeeByCriteria(String name) {
        Query query = new Query();
        query.addCriteria(Criteria.where("employeeName").is(name));

        return mongoTemplate.find(query, Employee.class);
    }

}
