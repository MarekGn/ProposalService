package com.cloudservices.proposalservice.services;

import com.cloudservices.proposalservice.api.v1.dto.requests.ProposalRequest;
import com.cloudservices.proposalservice.api.v1.dto.requests.ProposalStateRequest;
import com.cloudservices.proposalservice.api.v1.dto.responses.ProposalResponse;
import com.cloudservices.proposalservice.entities.Proposal;
import com.cloudservices.proposalservice.enums.ProposalState;
import com.cloudservices.proposalservice.exceptions.ProposalNotFoundException;
import com.cloudservices.proposalservice.repositories.ProposalRepository;
import com.cloudservices.proposalservice.statevalidation.ProposalStateTransitionValidator;
import com.cloudservices.proposalservice.utils.UniqueNumberGenerator;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;


@Service
public class ProposalServiceImpl implements ProposalService {

    private final ProposalRepository proposalRepository;

    private final ProposalStateTransitionValidator proposalStateTransitionValidator;

    private final UniqueNumberGenerator uniqueNumberGenerator;

    public ProposalServiceImpl(ProposalRepository proposalRepository, ProposalStateTransitionValidator proposalStateTransitionValidator, UniqueNumberGenerator uniqueNumberGenerator) {
        this.proposalRepository = proposalRepository;
        this.proposalStateTransitionValidator = proposalStateTransitionValidator;
        this.uniqueNumberGenerator = uniqueNumberGenerator;
    }

    @Override
    public ProposalResponse createProposal(ProposalRequest proposalRequest) {
        Proposal proposal = new Proposal(proposalRequest);
        proposal.setProposalState(ProposalState.CREATED);
        proposalRepository.save(proposal);
        return new ProposalResponse(proposal);
    }

    @Override
    public ProposalResponse getProposal(Long proposalId) {
        Proposal proposal = getProposalOrThrowException(proposalId);
        return new ProposalResponse(proposal);
    }

    @Override
    public Page<ProposalResponse> getAllProposals(Pageable pageable, Specification<Proposal> proposalSpec) {
        return proposalRepository.findAll(proposalSpec, pageable).map(ProposalResponse::new);
    }

    @Override
    public void updateProposal(Long proposalId, ProposalRequest proposalRequest) {
        Proposal proposal = getProposalOrThrowException(proposalId);
        proposal.setName(proposalRequest.getName());
        setProposalContentIfAllowed(proposal, proposalRequest.getContent());

        proposalRepository.save(proposal);
    }

    @Override
    public void updateProposalState(Long proposalId, ProposalStateRequest proposalStateRequest) {
        Proposal proposal = getProposalOrThrowException(proposalId);
        proposalStateTransitionValidator.validProposalStateTransition(proposal.getProposalState(), proposalStateRequest.getProposalState());

        proposal.setProposalState(proposalStateRequest.getProposalState());
        proposal.setStateNote(proposalStateRequest.getStateNote());
        addPublicationNumberIfNeeded(proposalStateRequest, proposal);

        proposalRepository.save(proposal);
    }

    private void addPublicationNumberIfNeeded(ProposalStateRequest proposalStateRequest, Proposal proposal) {
        if (proposalStateRequest.getProposalState().equals(ProposalState.PUBLISHED) && proposal.getPublicationNumber() == null) {
            proposal.setPublicationNumber(uniqueNumberGenerator.generateUniqueNumber());
        }
    }

    private void setProposalContentIfAllowed(Proposal proposal, String newContent) {
        if (canProposalContentBeUpdated(proposal.getProposalState())) {
            proposal.setContent(newContent);
        }
    }

    private boolean canProposalContentBeUpdated(ProposalState proposalState){
        return proposalState.equals(ProposalState.CREATED) || (proposalState.equals(ProposalState.VERIFIED));
    }

    private Proposal getProposalOrThrowException(Long proposalId){
        return proposalRepository.findById(proposalId).orElseThrow(() -> new ProposalNotFoundException("Proposal with id " + proposalId + " not found"));
    }
}
