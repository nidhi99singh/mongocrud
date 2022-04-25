package com.example.MongoCrud.controller;

import com.example.MongoCrud.entity.Employee;
import com.example.MongoCrud.repository.EmployeeRepository;
import com.example.MongoCrud.request.EmployeeRequest;
import com.example.MongoCrud.service.EmployeeService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.data.mongodb.core.aggregation.MatchOperation;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/employee")
public class EmployeeController {

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private EmployeeService employeeService;

    @Autowired
    private MongoTemplate mongoTemplate;

    @PostMapping("/add")
    public String addEmployee(@RequestBody EmployeeRequest employeeRequest) {
        return employeeService.addEmployee(employeeRequest);
    }

    @GetMapping("/get")
    public List<Employee> getEmployee(EmployeeRequest employeeRequest) {
        return employeeService.getEmployee(employeeRequest);
    }

    @GetMapping("/getByid/{id}")
    public Employee getEmployeeById(@PathVariable Integer id) {
        return employeeService.getEmployeeById(id);
    }

    @PutMapping("/update")
    public String updateEmployee(@RequestBody EmployeeRequest employeeRequest) {
        return employeeService.updateEmployee(employeeRequest);
    }

    @DeleteMapping("/delete/{id}")
    public String deleteEmployee(@PathVariable Integer id) {
        return employeeService.deleteEmployee(id);
    }

    @GetMapping("/getbysalary")
    public List<Employee> getEmployeeWithMaxSalary(@RequestParam(name = "salary") Integer salary) {
        return employeeService.getEmployeeMaxSalary(salary);
        // return employeeService.getEmployee(employeeRequest);
    }

    @GetMapping("/getnameandsalary")
    public List<Employee> getEmployeeNameAndSalary(@RequestParam(name = "employeeName") String employeeName, @RequestParam(name = "salary") Integer salary) {
        return employeeService.getEmployeeNameAndSalary(employeeName, salary);
    }

    @GetMapping("/getnameorsalary")
    public List<Employee> getEmployeeNameOrSalary(@RequestParam(name = "employeeName") String employeeName, @RequestParam(name = "salary") Integer salary) {
        return employeeService.getEmployeeNameOrSalary(employeeName, salary);
    }

    @GetMapping("/getall/{name}")
    public List<Employee> findAll(@PathVariable(value = "name") String name) {
        return employeeService.getEmployeeByCriteria(name);
    }

    @GetMapping("/getnamelike/{name}")
    public List<Employee> getByNameLike(@PathVariable(value = "name") String name) {
        return employeeRepository.getemployeeNameLike(name);
    }

    @GetMapping("/getsalarybetween/{minsal}/{maxsal}")
    public List<Employee> getSalaryBetween(@PathVariable(value = "minsal") Integer minsal, @PathVariable(value = "maxsal") Integer maxsal) {
        return employeeRepository.getSalaryBetween(minsal, maxsal);
    }

    @GetMapping("/orderby/{name}")
    public List<Employee> getEmployeeNameOrderBy(@PathVariable String name) {
        return employeeRepository.findByemployeeNameOrderBySalary(name);
    }

    @GetMapping("/aggregation/{age}")
    public List<Employee> findByAgematch(@PathVariable(value = "age") Integer age) {

        //match operation
        MatchOperation matchOperation = Aggregation.match(Criteria.where("age").is(age));
        Aggregation aggregation = Aggregation.newAggregation(matchOperation);
        AggregationResults results = mongoTemplate.aggregate(aggregation, "employee", Employee.class);
        return results.getMappedResults();

//        sort operation
//        SortOperation sortOperation=Aggregation.sort(Sort.by(Sort.Direction.DESC,"age"));
//        Aggregation aggregation=Aggregation.newAggregation(sortOperation);
//        AggregationResults results=mongoTemplate.aggregate(aggregation,"employee",Employee.class);
//        return results.getMappedResults();


    }
}
