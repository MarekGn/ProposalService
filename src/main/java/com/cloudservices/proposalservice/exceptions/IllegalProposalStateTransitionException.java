package com.cloudservices.proposalservice.exceptions;

public class IllegalProposalStateTransitionException extends RuntimeException {
    public IllegalProposalStateTransitionException(String message) {
        super(message);
    }
}
