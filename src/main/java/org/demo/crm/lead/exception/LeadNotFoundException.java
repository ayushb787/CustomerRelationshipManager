package org.demo.crm.lead.exception;

public class LeadNotFoundException extends RuntimeException {
    public LeadNotFoundException(String message) {
        super(message);
    }
}
