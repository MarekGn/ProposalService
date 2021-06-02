package com.cloudservices.proposalservice.statevalidation;

import com.cloudservices.proposalservice.enums.ProposalState;
import com.cloudservices.proposalservice.exceptions.IllegalProposalStateTransitionException;

public interface ProposalStateTransitionValidator {
    void validProposalStateTransition(ProposalState fromState, ProposalState toState) throws IllegalProposalStateTransitionException;
}
