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
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.Optional;

@SpringBootTest
public class ProposalServiceTests {

    @MockBean
    private ProposalRepository proposalRepository;
    @MockBean
    private ProposalStateTransitionValidator proposalStateTransitionValidator;
    @MockBean
    private UniqueNumberGenerator uniqueNumberGenerator;

    private ProposalService proposalService;

    @Captor
    ArgumentCaptor<Proposal> proposalCaptor;

    @BeforeEach
    public void setup() {
        proposalService = new ProposalServiceImpl(proposalRepository, proposalStateTransitionValidator, uniqueNumberGenerator);
    }

    @Test
    @DisplayName("Should map proposal to proposalDTO")
    void shouldMapProposalToDto() {
        //Given
        Proposal repositoryReturnedProposal = new Proposal(1L, "name", "content", ProposalState.CREATED, "stateNote", 1L);
        Mockito.when(proposalRepository.findById(1L)).thenReturn(Optional.of(repositoryReturnedProposal));
        //When
        ProposalResponse actual = proposalService.getProposal(1L);
        //Then
        ProposalResponse expected = new ProposalResponse(1L, "name", "content", ProposalState.CREATED, "stateNote", 1L);
        Assertions.assertThat(actual).isEqualTo(expected);
    }

    @Test
    @DisplayName("When no proposal is found should throw ProposalNotFoundException")
    void whenNoProposalFoundShouldThrowException() {
        //Given
        Mockito.when(proposalRepository.findById(1L)).thenReturn(Optional.empty());
        //When Then
        Assertions.assertThatThrownBy(() -> proposalService.getProposal(1L)).isInstanceOf(ProposalNotFoundException.class);
    }

    @Test
    @DisplayName("Creating proposal state check")
    void creatingProposalTest() {
        //Given
        ProposalRequest proposalRequest = new ProposalRequest("name", "content");
        //When
        ProposalResponse actual = proposalService.createProposal(proposalRequest);
        //Then
        Mockito.verify(proposalRepository, Mockito.times(1)).save(proposalCaptor.capture());

        Assertions.assertThat(proposalCaptor.getValue().getId()).isNull();
        Assertions.assertThat(proposalCaptor.getValue().getContent()).isEqualTo("content");
        Assertions.assertThat(proposalCaptor.getValue().getName()).isEqualTo("name");
        Assertions.assertThat(proposalCaptor.getValue().getStateNote()).isNull();
        Assertions.assertThat(proposalCaptor.getValue().getPublicationNumber()).isNull();
        Assertions.assertThat(proposalCaptor.getValue().getProposalState()).isEqualTo(ProposalState.CREATED);

        Assertions.assertThat(actual.getName()).isEqualTo("name");
        Assertions.assertThat(actual.getContent()).isEqualTo("content");
        Assertions.assertThat(actual.getStateNote()).isNull();
        Assertions.assertThat(actual.getPublicationNumber()).isNull();
        Assertions.assertThat(actual.getProposalState()).isEqualTo(ProposalState.CREATED);
    }

    @DisplayName("Proposal content can be changed for given state")
    @ParameterizedTest
    @EnumSource(value = ProposalState.class, names = {"CREATED", "VERIFIED"})
    void proposalContentCanBeUpdated(ProposalState proposalState) {
        //Given
        Proposal repositoryReturnedProposal = new Proposal(1L, "name", "content", proposalState, "stateNote", 1L);
        Mockito.when(proposalRepository.findById(1L)).thenReturn(Optional.of(repositoryReturnedProposal));
        //When
        ProposalRequest proposalRequest = new ProposalRequest("newName", "newContent");
        proposalService.updateProposal(1L, proposalRequest);
        //Then
        Mockito.verify(proposalRepository, Mockito.times(1)).save(proposalCaptor.capture());

        Assertions.assertThat(proposalCaptor.getValue().getId()).isEqualTo(1L);
        Assertions.assertThat(proposalCaptor.getValue().getName()).isEqualTo("newName");
        Assertions.assertThat(proposalCaptor.getValue().getContent()).isEqualTo("newContent");
        Assertions.assertThat(proposalCaptor.getValue().getProposalState()).isEqualTo(proposalState);
        Assertions.assertThat(proposalCaptor.getValue().getStateNote()).isEqualTo("stateNote");
        Assertions.assertThat(proposalCaptor.getValue().getPublicationNumber()).isEqualTo(1L);
    }

    @DisplayName("Proposal content cannot be changed for states")
    @ParameterizedTest
    @EnumSource(value = ProposalState.class, names = {"REJECTED", "ACCEPTED", "PUBLISHED", "DELETED"})
    void proposalContentCannotBeUpdated(ProposalState proposalState) {
        //Given
        Proposal repositoryReturnedProposal = new Proposal(1L, "name", "content", proposalState, "stateNote", 1L);
        Mockito.when(proposalRepository.findById(1L)).thenReturn(Optional.of(repositoryReturnedProposal));
        //When
        ProposalRequest proposalRequest = new ProposalRequest("newName", "newContent");
        proposalService.updateProposal(1L, proposalRequest);
        //Then
        Mockito.verify(proposalRepository, Mockito.times(1)).save(proposalCaptor.capture());

        Assertions.assertThat(proposalCaptor.getValue().getId()).isEqualTo(1L);
        Assertions.assertThat(proposalCaptor.getValue().getName()).isEqualTo("newName");
        Assertions.assertThat(proposalCaptor.getValue().getContent()).isEqualTo("content");
        Assertions.assertThat(proposalCaptor.getValue().getProposalState()).isEqualTo(proposalState);
        Assertions.assertThat(proposalCaptor.getValue().getStateNote()).isEqualTo("stateNote");
        Assertions.assertThat(proposalCaptor.getValue().getPublicationNumber()).isEqualTo(1L);
    }

    @Test
    @DisplayName("Add Publication number while publishing proposal")
    void addPublicationNumberWhilePublishing() {
        //Given
        Proposal repositoryReturnedProposal = new Proposal(1L, "name", "content", ProposalState.ACCEPTED, "stateNote", null);
        Mockito.when(proposalRepository.findById(1L)).thenReturn(Optional.of(repositoryReturnedProposal));
        Mockito.when(uniqueNumberGenerator.generateUniqueNumber()).thenReturn(8L);
        //When
        ProposalStateRequest proposalStateRequest = new ProposalStateRequest(ProposalState.PUBLISHED, "newNote");
        proposalService.updateProposalState(1L, proposalStateRequest);
        //Then
        Mockito.verify(proposalRepository, Mockito.times(1)).save(proposalCaptor.capture());

        Assertions.assertThat(proposalCaptor.getValue().getId()).isEqualTo(1L);
        Assertions.assertThat(proposalCaptor.getValue().getName()).isEqualTo("name");
        Assertions.assertThat(proposalCaptor.getValue().getContent()).isEqualTo("content");
        Assertions.assertThat(proposalCaptor.getValue().getProposalState()).isEqualTo(ProposalState.PUBLISHED);
        Assertions.assertThat(proposalCaptor.getValue().getStateNote()).isEqualTo("newNote");
        Assertions.assertThat(proposalCaptor.getValue().getPublicationNumber()).isEqualTo(8L);
    }

    @Test
    @DisplayName("Updating PUBLISHED proposal cannot change publication number")
    void updatingPublishedProposalCannotChangePublicationNumber() {
        //Given
        Proposal repositoryReturnedProposal = new Proposal(1L, "name", "content", ProposalState.ACCEPTED, "stateNote", 2L);
        Mockito.when(proposalRepository.findById(1L)).thenReturn(Optional.of(repositoryReturnedProposal));
        Mockito.when(uniqueNumberGenerator.generateUniqueNumber()).thenReturn(8L);
        //When
        ProposalStateRequest proposalStateRequest = new ProposalStateRequest(ProposalState.PUBLISHED, "newNote");
        proposalService.updateProposalState(1L, proposalStateRequest);
        //Then
        Mockito.verify(proposalRepository, Mockito.times(1)).save(proposalCaptor.capture());

        Assertions.assertThat(proposalCaptor.getValue().getId()).isEqualTo(1L);
        Assertions.assertThat(proposalCaptor.getValue().getName()).isEqualTo("name");
        Assertions.assertThat(proposalCaptor.getValue().getContent()).isEqualTo("content");
        Assertions.assertThat(proposalCaptor.getValue().getProposalState()).isEqualTo(ProposalState.PUBLISHED);
        Assertions.assertThat(proposalCaptor.getValue().getStateNote()).isEqualTo("newNote");
        Assertions.assertThat(proposalCaptor.getValue().getPublicationNumber()).isEqualTo(2L);
    }
}
