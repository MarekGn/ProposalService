package com.cloudservices.proposalservice.entities;

import com.cloudservices.proposalservice.api.v1.dto.requests.ProposalRequest;
import com.cloudservices.proposalservice.api.v1.dto.requests.ProposalStateRequest;
import com.cloudservices.proposalservice.enums.ProposalState;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.hibernate.envers.Audited;

import javax.persistence.*;

@Entity
@Table(name = "Proposal")
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@Audited
public class Proposal extends AbstractEntity {
    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "content", nullable = false, length = 1500)
    private String content;

    @Column(name = "state", nullable = false)
    @Enumerated(value = EnumType.STRING)
    private ProposalState proposalState;

    @Column(name = "state_note")
    private String stateNote;

    @Column(name = "publication_number", unique = true)
    private Long publicationNumber;

    public Proposal(Long id, String name, String content, ProposalState proposalState, String stateNote, Long publicationNumber) {
        super(id);
        this.name = name;
        this.content = content;
        this.proposalState = proposalState;
        this.stateNote = stateNote;
        this.publicationNumber = publicationNumber;
    }

    public Proposal (ProposalRequest proposalRequest) {
        this.name = proposalRequest.getName();
        this.content = proposalRequest.getContent();
    }

    public Proposal (ProposalStateRequest proposalStateRequest) {
        this.proposalState = proposalStateRequest.getProposalState();
        this.stateNote = proposalStateRequest.getStateNote();
    }
}
