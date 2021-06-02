package com.cloudservices.proposalservice.exceptions;

public class ProposalNotFoundException extends RuntimeException {
    public ProposalNotFoundException(String message) {
        super(message);
    }
}
