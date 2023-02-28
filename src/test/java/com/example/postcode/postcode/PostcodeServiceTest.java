package com.example.postcode.postcode;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class PostcodeServiceTest {

    @Mock
    private PostCodeRepository postcodeRepository;
    private PostcodeService underTest;

    @BeforeEach
    void setUp() {
        underTest = new PostcodeService(postcodeRepository);
    }

    @Test
    void create() {
        // given
        PostcodeDTO postcodeDTO = new PostcodeDTO(2567, "Mount Annan");

        //when
        Postcode postcode = underTest.create(postcodeDTO);

        //then
        ArgumentCaptor<Postcode> postcodeArgumentCaptor =
                ArgumentCaptor.forClass(Postcode.class);

        verify(postcodeRepository)
                .save(postcodeArgumentCaptor.capture());

        Postcode capturedPostcode = postcodeArgumentCaptor.getValue();

        assertThat(capturedPostcode).isEqualTo(postcode);

    }

    @Test
    void getAll() {
        // when
        underTest.getAll();
        //then
        verify(postcodeRepository).findAll();
    }

    @Test
    void findByPostcode() {
        // when
        Integer postcode = 2567;
        underTest.findByPostcode(postcode);
        //then
        verify(postcodeRepository).findByPostcode(postcode);
    }

    @Test
    void findBySuburb() {
        // when
        String suburb = "Mount Annan";
        underTest.findBySuburb(suburb);
        //then
        verify(postcodeRepository).findBySuburb(suburb);
    }
}