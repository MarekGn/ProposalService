package com.cloudservices.proposalservice.repositories;


import com.cloudservices.proposalservice.entities.Proposal;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProposalRepository extends PagingAndSortingRepository<Proposal, Long>, JpaSpecificationExecutor<Proposal> {
    @Query("SELECT coalesce(max(p.publicationNumber), 0) FROM Proposal p")
    Long getMaxPublicationNumberValue();
}
