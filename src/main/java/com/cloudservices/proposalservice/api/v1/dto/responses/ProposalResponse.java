package com.cloudservices.proposalservice.api.v1.dto.responses;

import com.cloudservices.proposalservice.entities.Proposal;
import com.cloudservices.proposalservice.enums.ProposalState;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProposalResponse {
    private Long id;

    private String name;

    private String content;

    private ProposalState proposalState;

    private String stateNote;

    private Long publicationNumber;

    public ProposalResponse(Proposal proposal) {
        this.id = proposal.getId();
        this.name = proposal.getName();
        this.content = proposal.getContent();
        this.proposalState = proposal.getProposalState();
        this.stateNote = proposal.getStateNote();
        this.publicationNumber = proposal.getPublicationNumber();
    }
}
