package com.parmendra.repository;

import com.parmendra.entity.Customers;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepository extends JpaRepository<Customers , Integer> {
}
