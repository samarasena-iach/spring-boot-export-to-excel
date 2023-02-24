package com.samiach.exporttoexceldemo.service;

import com.samiach.exporttoexceldemo.model.Customer;
import com.samiach.exporttoexceldemo.repository.CustomerRepository;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public interface CustomerService {

    List<Customer> exportCustomerToExcel(HttpServletResponse response) throws IOException;

}
