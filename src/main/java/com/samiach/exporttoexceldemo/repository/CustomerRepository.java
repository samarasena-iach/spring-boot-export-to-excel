package com.samiach.exporttoexceldemo.repository;

import com.samiach.exporttoexceldemo.model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepository extends JpaRepository<Customer, Integer> {
}
