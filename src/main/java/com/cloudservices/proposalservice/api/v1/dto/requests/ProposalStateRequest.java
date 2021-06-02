package com.cloudservices.proposalservice.api.v1.dto.requests;

import com.cloudservices.proposalservice.api.v1.validation.annotations.ValidProposalStateNote;
import com.cloudservices.proposalservice.enums.ProposalState;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ValidProposalStateNote
public class ProposalStateRequest {
    @NotNull(message = "Proposal state cannot be null!")
    private ProposalState proposalState;
    @Size(max = 255, message = "Maximal state note length is 255")
    private String stateNote;
}
