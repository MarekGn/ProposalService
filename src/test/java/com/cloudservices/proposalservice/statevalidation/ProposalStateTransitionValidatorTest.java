package com.cloudservices.proposalservice.statevalidation;

import com.cloudservices.proposalservice.enums.ProposalState;
import com.cloudservices.proposalservice.exceptions.IllegalProposalStateTransitionException;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class ProposalStateTransitionValidatorTest {

    ProposalStateTransitionValidator proposalStateTransitionValidator;

    @BeforeEach
    public void setup() {
        proposalStateTransitionValidator = new ProposalStateTransitionValidatorImpl();
    }

    @Test
    @DisplayName("Should throw exception for invalid CREATED state transition")
    void throwExceptionForCreatedInvalidTransition() {
        Assertions.assertThatThrownBy(() -> proposalStateTransitionValidator.validProposalStateTransition(ProposalState.CREATED, ProposalState.ACCEPTED)).isInstanceOf(IllegalProposalStateTransitionException.class);
        Assertions.assertThatThrownBy(() -> proposalStateTransitionValidator.validProposalStateTransition(ProposalState.CREATED, ProposalState.PUBLISHED)).isInstanceOf(IllegalProposalStateTransitionException.class);
        Assertions.assertThatThrownBy(() -> proposalStateTransitionValidator.validProposalStateTransition(ProposalState.CREATED, ProposalState.REJECTED)).isInstanceOf(IllegalProposalStateTransitionException.class);
    }

    @Test
    @DisplayName("Should throw exception for invalid VERIFIED state transition")
    void throwExceptionForVerifiedInvalidTransition() {
        Assertions.assertThatThrownBy(() -> proposalStateTransitionValidator.validProposalStateTransition(ProposalState.VERIFIED, ProposalState.CREATED)).isInstanceOf(IllegalProposalStateTransitionException.class);
        Assertions.assertThatThrownBy(() -> proposalStateTransitionValidator.validProposalStateTransition(ProposalState.VERIFIED, ProposalState.PUBLISHED)).isInstanceOf(IllegalProposalStateTransitionException.class);
        Assertions.assertThatThrownBy(() -> proposalStateTransitionValidator.validProposalStateTransition(ProposalState.VERIFIED, ProposalState.DELETED)).isInstanceOf(IllegalProposalStateTransitionException.class);
    }

    @Test
    @DisplayName("Should throw exception for invalid DELETED state transition")
    void throwExceptionForDeletedInvalidTransition() {
        Assertions.assertThatThrownBy(() -> proposalStateTransitionValidator.validProposalStateTransition(ProposalState.DELETED, ProposalState.CREATED)).isInstanceOf(IllegalProposalStateTransitionException.class);
        Assertions.assertThatThrownBy(() -> proposalStateTransitionValidator.validProposalStateTransition(ProposalState.DELETED, ProposalState.VERIFIED)).isInstanceOf(IllegalProposalStateTransitionException.class);
        Assertions.assertThatThrownBy(() -> proposalStateTransitionValidator.validProposalStateTransition(ProposalState.DELETED, ProposalState.PUBLISHED)).isInstanceOf(IllegalProposalStateTransitionException.class);
        Assertions.assertThatThrownBy(() -> proposalStateTransitionValidator.validProposalStateTransition(ProposalState.DELETED, ProposalState.REJECTED)).isInstanceOf(IllegalProposalStateTransitionException.class);
        Assertions.assertThatThrownBy(() -> proposalStateTransitionValidator.validProposalStateTransition(ProposalState.DELETED, ProposalState.ACCEPTED)).isInstanceOf(IllegalProposalStateTransitionException.class);
    }

    @Test
    @DisplayName("Should throw exception for invalid ACCEPTED state transition")
    void throwExceptionForAcceptedInvalidTransition() {
        Assertions.assertThatThrownBy(() -> proposalStateTransitionValidator.validProposalStateTransition(ProposalState.ACCEPTED, ProposalState.CREATED)).isInstanceOf(IllegalProposalStateTransitionException.class);
        Assertions.assertThatThrownBy(() -> proposalStateTransitionValidator.validProposalStateTransition(ProposalState.ACCEPTED, ProposalState.VERIFIED)).isInstanceOf(IllegalProposalStateTransitionException.class);
        Assertions.assertThatThrownBy(() -> proposalStateTransitionValidator.validProposalStateTransition(ProposalState.ACCEPTED, ProposalState.DELETED)).isInstanceOf(IllegalProposalStateTransitionException.class);
    }

    @Test
    @DisplayName("Should throw exception for invalid PUBLISHED state transition")
    void throwExceptionForPublishInvalidTransition() {
        Assertions.assertThatThrownBy(() -> proposalStateTransitionValidator.validProposalStateTransition(ProposalState.PUBLISHED, ProposalState.CREATED)).isInstanceOf(IllegalProposalStateTransitionException.class);
        Assertions.assertThatThrownBy(() -> proposalStateTransitionValidator.validProposalStateTransition(ProposalState.PUBLISHED, ProposalState.VERIFIED)).isInstanceOf(IllegalProposalStateTransitionException.class);
        Assertions.assertThatThrownBy(() -> proposalStateTransitionValidator.validProposalStateTransition(ProposalState.PUBLISHED, ProposalState.DELETED)).isInstanceOf(IllegalProposalStateTransitionException.class);
        Assertions.assertThatThrownBy(() -> proposalStateTransitionValidator.validProposalStateTransition(ProposalState.PUBLISHED, ProposalState.REJECTED)).isInstanceOf(IllegalProposalStateTransitionException.class);
        Assertions.assertThatThrownBy(() -> proposalStateTransitionValidator.validProposalStateTransition(ProposalState.PUBLISHED, ProposalState.ACCEPTED)).isInstanceOf(IllegalProposalStateTransitionException.class);
    }

    @Test
    @DisplayName("doesnt throw exception for CREATED valid transition")
    void doesntThrowExceptionForCreatedValidTransition() {
        proposalStateTransitionValidator.validProposalStateTransition(ProposalState.CREATED, ProposalState.CREATED);
        proposalStateTransitionValidator.validProposalStateTransition(ProposalState.CREATED, ProposalState.DELETED);
        proposalStateTransitionValidator.validProposalStateTransition(ProposalState.CREATED, ProposalState.VERIFIED);
    }

    @Test
    @DisplayName("doesnt throw exception for DELETED valid transition")
    void doesntThrowExceptionForDeletedValidTransition() {
        proposalStateTransitionValidator.validProposalStateTransition(ProposalState.DELETED, ProposalState.DELETED);
    }

    @Test
    @DisplayName("doesnt throw exception for VERIFIED valid transition")
    void doesntThrowExceptionForVerifiedValidTransition() {
        proposalStateTransitionValidator.validProposalStateTransition(ProposalState.VERIFIED, ProposalState.VERIFIED);
        proposalStateTransitionValidator.validProposalStateTransition(ProposalState.VERIFIED, ProposalState.REJECTED);
        proposalStateTransitionValidator.validProposalStateTransition(ProposalState.VERIFIED, ProposalState.ACCEPTED);
    }

    @Test
    @DisplayName("doesnt throw exception for ACCEPTED valid transition")
    void doesntThrowExceptionForAcceptedValidTransition() {
        proposalStateTransitionValidator.validProposalStateTransition(ProposalState.ACCEPTED, ProposalState.ACCEPTED);
        proposalStateTransitionValidator.validProposalStateTransition(ProposalState.ACCEPTED, ProposalState.REJECTED);
        proposalStateTransitionValidator.validProposalStateTransition(ProposalState.ACCEPTED, ProposalState.PUBLISHED);
    }

    @Test
    @DisplayName("doesnt throw exception for REJECTED valid transition")
    void doesntThrowExceptionForRejectedValidTransition() {
        proposalStateTransitionValidator.validProposalStateTransition(ProposalState.REJECTED, ProposalState.REJECTED);
    }

    @Test
    @DisplayName("doesnt throw exception for PUBLISHED valid transition")
    void doesntThrowExceptionForPublishedValidTransition() {
        proposalStateTransitionValidator.validProposalStateTransition(ProposalState.PUBLISHED, ProposalState.PUBLISHED);
    }
}