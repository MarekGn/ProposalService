package com.cloudservices.proposalservice.utils;

import com.cloudservices.proposalservice.repositories.ProposalRepository;
import org.springframework.stereotype.Component;

@Component
public class UniqueNumberGeneratorImpl implements UniqueNumberGenerator {

    private final ProposalRepository proposalRepository;

    public UniqueNumberGeneratorImpl(ProposalRepository proposalRepository) {
        this.proposalRepository = proposalRepository;
    }

    @Override
    public Long generateUniqueNumber() {
        return proposalRepository.getMaxPublicationNumberValue() + 1;
    }
}
