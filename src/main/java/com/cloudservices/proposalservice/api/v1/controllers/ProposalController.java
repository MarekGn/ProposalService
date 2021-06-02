package com.cloudservices.proposalservice.api.v1.controllers;

import com.cloudservices.proposalservice.api.v1.dto.requests.ProposalRequest;
import com.cloudservices.proposalservice.api.v1.dto.requests.ProposalStateRequest;
import com.cloudservices.proposalservice.api.v1.dto.responses.ProposalResponse;
import com.cloudservices.proposalservice.entities.Proposal;
import com.cloudservices.proposalservice.services.ProposalService;
import net.kaczmarzyk.spring.data.jpa.domain.Equal;
import net.kaczmarzyk.spring.data.jpa.domain.LikeIgnoreCase;
import net.kaczmarzyk.spring.data.jpa.web.annotation.And;
import net.kaczmarzyk.spring.data.jpa.web.annotation.Spec;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;

@RestController
@RequestMapping(value = "api/v1/proposals")
public class ProposalController {

    private final ProposalService proposalService;

    public ProposalController(ProposalService proposalService) {
        this.proposalService = proposalService;
    }

    @GetMapping
    public Page<ProposalResponse> getAllProposals(
            @And({
                    @Spec(path = "name", spec = LikeIgnoreCase.class),
                    @Spec(path = "proposalState", spec = Equal.class),
            }) Specification<Proposal> proposalSpec,
            @PageableDefault Pageable pageable) {
        return proposalService.getAllProposals(pageable, proposalSpec);
    }

    @GetMapping("/{id}")
    public ProposalResponse getProposal(@PathVariable Long id) {
        return proposalService.getProposal(id);
    }

    @PostMapping
    public ResponseEntity<Object> createProposal(@Valid @RequestBody ProposalRequest proposalRequest) {
        ProposalResponse proposalResponse =  proposalService.createProposal(proposalRequest);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(proposalResponse.getId()).toUri();
        return ResponseEntity.created(location).build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Object> updateProposal(@PathVariable Long id, @Valid @RequestBody ProposalRequest proposalRequest) {
        proposalService.updateProposal(id, proposalRequest);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}/state")
    public ResponseEntity<Object> updateProposalState(@PathVariable Long id, @Valid @RequestBody ProposalStateRequest proposalStateRequest) {
        proposalService.updateProposalState(id, proposalStateRequest);
        return ResponseEntity.noContent().build();
    }
}
