package com.samiach.exporttoexceldemo.utility;

import com.samiach.exporttoexceldemo.model.Address;
import com.samiach.exporttoexceldemo.model.Customer;
import com.samiach.exporttoexceldemo.repository.CustomerRepository;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.Arrays;
import java.util.List;

@Component
public class SeedData {

    private CustomerRepository customerRepository;

    public SeedData(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    private List<Customer> customers = Arrays.asList(
            new Customer("Chasith", "Samarasena", "chasith.samarasena@gds.ey.com",
                    new Address("Sri Lanka", "Western Province", "Mt.Lavinia", "Sylvester Rd")),
            new Customer("Shalika", "Ranasinghe", "shalika@gmail.com",
                    new Address("Sri Lanka", "Western Province", "Mt.Lavinia", "Sylvester Rd"))
    );

    // @PostConstruct //Uncomment after first execution
    public void saveCustomers() {
        customerRepository.saveAll(customers);
    }
}
