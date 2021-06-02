package com.cloudservices.proposalservice.statevalidation;

import com.cloudservices.proposalservice.enums.ProposalState;
import com.cloudservices.proposalservice.exceptions.IllegalProposalStateTransitionException;
import org.springframework.stereotype.Component;

@Component
public class ProposalStateTransitionValidatorImpl implements ProposalStateTransitionValidator {

    @Override
    public void validProposalStateTransition(ProposalState fromState, ProposalState toState) {
        if (!ProposalStateTransition.AVAILABLE_TRANSITIONS.get(fromState).contains(toState)) {
            throw new IllegalProposalStateTransitionException("Illegal transition from state " + fromState.name().toLowerCase() + " to state " + toState.name().toLowerCase());
        }
    }
}
