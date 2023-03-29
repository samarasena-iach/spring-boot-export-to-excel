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
            new Customer("Chasith", "Samarasena", "chasith.samarasena@gds.ey.com", 1,
                    new Address("Sri Lanka", "Western Province", "Mt.Lavinia", "Sylvester Rd")),
            new Customer("Shalika", "Ranasinghe", "shalika@gmail.com", 0,
                    new Address("Sri Lanka", "Western Province", "Mt.Lavinia", "Sylvester Rd")),
            new Customer("Dasan", "Samarasena", "dasan@gmail.com", 0,
                                 new Address("Sri Lanka", "Western Province", "Panadura", "42, D.B.Perera Mw, Parattha Rd, Keselwattha")),
            new Customer("Malithi Maneesha", "Undugoda", "malithi@gmail.com", 0,
                    new Address("Australia", "Melbourne, Victoria", "Glen Waverly", "Block 3A, Park View Street"))
    );

    // @PostConstruct //Uncomment after first execution
    public void saveCustomers() {
        customerRepository.saveAll(customers);
    }
}
