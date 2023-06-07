package me.chiqors.minimarket_backend.repository;

import me.chiqors.minimarket_backend.model.Employee;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {
    Employee findByEmployeeCode(String employeeCode);

    Page<Employee> findAllByNameContaining(String name, Pageable pageable);

    Employee findByPhoneNumber(String phoneNumber);
}
