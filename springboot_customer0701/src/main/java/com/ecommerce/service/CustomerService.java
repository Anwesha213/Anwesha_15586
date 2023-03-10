package com.ecommerce.service;

 

    import java.util.ArrayList;
    import java.util.List;
    import java.util.Optional;
    import javax.persistence.EntityNotFoundException;
    import org.springframework.beans.factory.annotation.Autowired;
    import org.springframework.stereotype.Service;
    import com.ecommerce.dao.ICustomerRepository;
    import com.ecommerce.dto.CustomerData;
     import com.ecommerce.model.Customer;

    @Service
    public class CustomerService implements ICustomerService {

 

        

        @Autowired
        private ICustomerRepository customerRepository;

 

        private Customer getCustomerEntity(CustomerData customerData) {
            Customer customer = new Customer();
            customer.setCustId(customerData.getCustId());
            customer.setCustName(customerData.getCustName());
            customer.setPhone(customerData.getPhone());
            customer.setEmail(customerData.getEmail());
            customer.setPermanentAddress(customerData.getPermanentAddress());
            return customer;
        }

 

        private CustomerData getCustomerData(Customer customer) {
            CustomerData customerData = new CustomerData();
            customerData.setCustId(customer.getCustId());
            customerData.setCustName(customer.getCustName());
            customerData.setPhone(customer.getPhone());
            customerData.setEmail(customer.getEmail());
            customerData.setPermanentAddress(customer.getPermanentAddress());
            return customerData;
        }

 

        @Override
        public List<CustomerData> findAll() {
            List<CustomerData> customerDataList=new ArrayList<>();
            List<Customer> customers=customerRepository.findAll();
            customers.forEach(customer->{customerDataList.add(getCustomerData(customer));});
            return customerDataList;
        }

 

        @Override
        public CustomerData findById(Long id) {
            Optional<Customer> customerOptional = customerRepository.findById(id);
            if (customerOptional == null) {
                new EntityNotFoundException("Customer Not Found");
            }
            return getCustomerData(customerOptional.get());
        }

 


        @Override
        public CustomerData create(CustomerData customerData) {
            Customer customer=getCustomerEntity(customerData);

            return getCustomerData(customerRepository.save(customer));
        }

 

        @Override
        public boolean delete(Long id) {
            boolean test=findAll().remove(findById(id));
            customerRepository.deleteById(id);        
            return test;
        }
    }

 