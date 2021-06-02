package com.cloudservices.proposalservice.statevalidation;

import com.cloudservices.proposalservice.enums.ProposalState;

import java.util.List;
import java.util.Map;

public final class ProposalStateTransition {

    public static final Map<ProposalState, List<ProposalState>> AVAILABLE_TRANSITIONS = Map.of(
            ProposalState.CREATED, List.of(ProposalState.CREATED, ProposalState.DELETED, ProposalState.VERIFIED),
            ProposalState.DELETED, List.of(ProposalState.DELETED),
            ProposalState.REJECTED, List.of(ProposalState.REJECTED),
            ProposalState.VERIFIED, List.of(ProposalState.VERIFIED, ProposalState.REJECTED, ProposalState.ACCEPTED),
            ProposalState.ACCEPTED, List.of(ProposalState.ACCEPTED, ProposalState.REJECTED, ProposalState.PUBLISHED),
            ProposalState.PUBLISHED, List.of(ProposalState.PUBLISHED)
    );

    private ProposalStateTransition() {
    }
}
