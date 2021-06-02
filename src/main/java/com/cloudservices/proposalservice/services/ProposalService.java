package com.cloudservices.proposalservice.services;

import com.cloudservices.proposalservice.api.v1.dto.requests.ProposalRequest;
import com.cloudservices.proposalservice.api.v1.dto.requests.ProposalStateRequest;
import com.cloudservices.proposalservice.api.v1.dto.responses.ProposalResponse;
import com.cloudservices.proposalservice.entities.Proposal;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

public interface ProposalService {
    ProposalResponse createProposal(ProposalRequest proposalRequest);

    ProposalResponse getProposal(Long id);

    Page<ProposalResponse> getAllProposals(Pageable pageable, Specification<Proposal> proposalSpec);

    void updateProposal(Long proposalId, ProposalRequest proposalRequest);

    void updateProposalState(Long proposalId, ProposalStateRequest proposalStateRequest);
}
