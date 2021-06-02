package com.cloudservices.proposalservice.utils;

import com.cloudservices.proposalservice.repositories.ProposalRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

@SpringBootTest
class UniqueNumberGeneratorTest {


    @MockBean
    private ProposalRepository proposalRepository;

    private UniqueNumberGenerator uniqueNumberGenerator;


    @BeforeEach
    public void setup() {
        uniqueNumberGenerator = new UniqueNumberGeneratorImpl(proposalRepository);
    }

    @Test
    @DisplayName("Valid generated publication number")
    void generateUniqueNumber() {
        //Given
        Mockito.when(proposalRepository.getMaxPublicationNumberValue()).thenReturn(0L);
        //When
        Long generatedValue = uniqueNumberGenerator.generateUniqueNumber();
        //Then
        Assertions.assertThat(generatedValue).isEqualTo(1L);
    }
}