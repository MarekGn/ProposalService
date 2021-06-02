package com.cloudservices.proposalservice.api.v1.dto.requests;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProposalRequest {
    @NotBlank(message = "Proposal name is required!")
    @Size(max = 255, message = "Maximal name length is 255")
    private String name;
    
    @NotBlank(message = "Proposal content is required!")
    @Size(max = 1500, message = "Maximal content length is 1500")
    private String content;
}
