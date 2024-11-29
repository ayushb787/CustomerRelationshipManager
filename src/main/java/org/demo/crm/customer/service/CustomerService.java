package org.demo.crm.customer.service;

import org.demo.crm.customer.dto.CustomerRequest;
import org.demo.crm.customer.dto.CustomerResponse;
import org.demo.crm.customer.exception.CustomerNotFoundException;
import org.demo.crm.customer.exception.DuplicateEmailException;
import org.demo.crm.customer.exception.DuplicatePhoneException;
import org.demo.crm.customer.model.Customer;
import org.demo.crm.customer.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CustomerService {

    @Autowired
    private CustomerRepository customerRepository;

    public List<CustomerResponse> getAllCustomers() {
        List<Customer> customers = customerRepository.findAll();
        return customers.stream()
                .map(this::convertToResponseDTO)
                .collect(Collectors.toList());
    }

    public CustomerResponse getCustomerById(Long id) {
        Customer customer = customerRepository.findById(id)
                .orElseThrow(() -> new CustomerNotFoundException("Customer not found"));
        return convertToResponseDTO(customer);
    }

    public CustomerResponse createCustomer(CustomerRequest customerRequest) {
        if (customerRepository.existsByEmail(customerRequest.getEmail())) {
            throw new DuplicateEmailException("Email already exists");
        }

        if (customerRepository.existsByPhone(customerRequest.getPhone())) {
            throw new DuplicatePhoneException("Phone number already exists");
        }

        Customer customer = convertToEntity(customerRequest);
        customer.setDateAdded(LocalDateTime.now());
        Customer savedCustomer = customerRepository.save(customer);
        return convertToResponseDTO(savedCustomer);
    }

    public CustomerResponse updateCustomer(Long id, CustomerRequest customerRequest) {
        Customer existingCustomer = customerRepository.findById(id)
                .orElseThrow(() -> new CustomerNotFoundException("Customer not found"));

        if (!existingCustomer.getEmail().equals(customerRequest.getEmail()) &&
                customerRepository.existsByEmail(customerRequest.getEmail())) {
            throw new DuplicateEmailException("Email already exists");
        }

        if (!existingCustomer.getPhone().equals(customerRequest.getPhone()) &&
                customerRepository.existsByPhone(customerRequest.getPhone())) {
            throw new DuplicatePhoneException("Phone number already exists");
        }

        existingCustomer.setName(customerRequest.getName());
        existingCustomer.setEmail(customerRequest.getEmail());
        existingCustomer.setPhone(customerRequest.getPhone());
        existingCustomer.setAddress(customerRequest.getAddress());
        existingCustomer.setCategory(customerRequest.getCategory());
        existingCustomer.setPreferences(customerRequest.getPreferences());

        Customer updatedCustomer = customerRepository.save(existingCustomer);

        return convertToResponseDTO(updatedCustomer);
    }


    public void deleteCustomer(Long id) {
        Customer customer = customerRepository.findById(id)
                .orElseThrow(() -> new CustomerNotFoundException("Customer not found"));
        customerRepository.delete(customer);
    }

    private CustomerResponse convertToResponseDTO(Customer customer) {
        CustomerResponse response = new CustomerResponse();
        response.setCustomerId(customer.getCustomerId());
        response.setName(customer.getName());
        response.setEmail(customer.getEmail());
        response.setPhone(customer.getPhone());
        response.setAddress(customer.getAddress());
        response.setCategory(customer.getCategory());
        response.setPreferences(customer.getPreferences());
        response.setDateAdded(customer.getDateAdded());
        return response;
    }

    private Customer convertToEntity(CustomerRequest customerRequest) {
        Customer customer = new Customer();
        customer.setName(customerRequest.getName());
        customer.setEmail(customerRequest.getEmail());
        customer.setPhone(customerRequest.getPhone());
        customer.setAddress(customerRequest.getAddress());
        customer.setCategory(customerRequest.getCategory());
        customer.setPreferences(customerRequest.getPreferences());
        return customer;
    }
}
