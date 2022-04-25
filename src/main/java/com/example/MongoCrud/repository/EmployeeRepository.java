package com.example.MongoCrud.repository;


import com.example.MongoCrud.entity.Employee;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EmployeeRepository extends MongoRepository<Employee, Integer> {

    //fields which needs to be displayed (projection)
    @Query(value = "{'salary':{$gte:?0}}", fields = "{'employeeName':1,'id':0,'salary':1}")
    List<Employee> getEmployeeMaxSalary(Integer salary);

    //and operator
    @Query(value = "{$and:[{'employeeName':?0},{'salary':?1}]}")
    List<Employee> getEmployeeNameAndSalary(String employeeName,Integer salary);

    @Query(value = "{$or:[{'employeeName':?0},{'salary':?1}]}")
    List<Employee> getEmployeeNameOrSalary(String employeeName,Integer salary);

    @Query(value = "{'employeeName':{$regex:'?0'}}")
   List<Employee> getemployeeNameLike(String regexp);

    @Query(value = "{'salary':{$gt:?0,$lte:?1}}")
    List<Employee> getSalaryBetween(Integer minSalary,Integer maxSalary);

    List<Employee> findByemployeeNameOrderBySalary(String name);
}

