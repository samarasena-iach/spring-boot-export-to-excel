package com.samiach.exporttoexceldemo.service;

import com.samiach.exporttoexceldemo.model.Customer;
import com.samiach.exporttoexceldemo.repository.CustomerRepository;
import com.samiach.exporttoexceldemo.utility.ExcelExportUtil;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@Service
@AllArgsConstructor
public class CustomerServiceImpl implements CustomerService {

    private CustomerRepository customerRepository;

    public List<Customer> exportCustomerToExcel(HttpServletResponse response) throws IOException {
        List<Customer> customers = customerRepository.findAll();
        ExcelExportUtil exportUtil = new ExcelExportUtil(customers);
        exportUtil.exportDataToExcel(response);
        return customers;
    }
}
