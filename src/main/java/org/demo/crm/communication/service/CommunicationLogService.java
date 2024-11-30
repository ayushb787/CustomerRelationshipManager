package org.demo.crm.communication.service;

import org.demo.crm.communication.entity.CommunicationLog;
import org.demo.crm.communication.repository.CommunicationLogRepository;
import org.demo.crm.customer.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class CommunicationLogService {

    @Autowired
    private CommunicationLogRepository communicationLogRepository;

    public CommunicationLog logCommunication(Long customerId, String channel, String message, String status) {
        CommunicationLog log = new CommunicationLog();
        log.setCustomerId(customerId);
        log.setChannel(channel);
        log.setMessage(message);
        log.setDate(new Date());
        log.setStatus(status);
        return communicationLogRepository.save(log);
    }

    @Autowired
    private CustomerRepository customerRepository;

    public boolean existsById(Long customerId) {
        return customerRepository.existsById(customerId);
    }

    @Autowired
    private EmailService emailService;


    public void notifyCustomer(Long customerId, String subject, String message) {
        var customer = customerRepository.findById(customerId)
                .orElseThrow(() -> new IllegalArgumentException("Customer not found with ID: " + customerId));

        emailService.sendEmail(customer.getEmail(), subject, message);
    }

}
