package com.cloudservices.proposalservice.api.v1.validation.annotations;

import com.cloudservices.proposalservice.api.v1.validation.validators.ProposalStateNoteValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = {ProposalStateNoteValidator.class})
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidProposalStateNote {
    String message() default "State note can't be empty for given state transition";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}

