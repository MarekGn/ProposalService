package com.cloudservices.proposalservice.api.v1.validation.validators;

import com.cloudservices.proposalservice.api.v1.dto.requests.ProposalStateRequest;
import com.cloudservices.proposalservice.api.v1.validation.annotations.ValidProposalStateNote;
import com.cloudservices.proposalservice.enums.ProposalState;
import org.apache.commons.lang3.StringUtils;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.List;

public class ProposalStateNoteValidator implements ConstraintValidator<ValidProposalStateNote, ProposalStateRequest> {

    private static final List<ProposalState> REQUIRED_FOR_STATES = List.of(ProposalState.REJECTED, ProposalState.DELETED);

    @Override
    public void initialize(ValidProposalStateNote constraintAnnotation) {
    }

    @Override
    public boolean isValid(ProposalStateRequest proposalStateRequest, ConstraintValidatorContext constraintValidatorContext) {
        if (proposalStateRequest.getProposalState() != null && REQUIRED_FOR_STATES.contains(proposalStateRequest.getProposalState())) {
            return !StringUtils.isBlank(proposalStateRequest.getStateNote());
        }
        return true;
    }
}
