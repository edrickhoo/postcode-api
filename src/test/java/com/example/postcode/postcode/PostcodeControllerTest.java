package com.example.postcode.postcode;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@SpringBootTest()
@AutoConfigureMockMvc
class PostcodeControllerTest {

    @Autowired
    private PostCodeRepository postCodeRepository;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void create() throws Exception {
        // given
        PostcodeDTO postcodeDTO = new PostcodeDTO(2567, "Mount Annan");
        Postcode postcode = new Postcode("Mount Annan", 2567);

        // when
        ResultActions resultActions = mockMvc
                .perform(MockMvcRequestBuilders.post("/postcode")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(postcodeDTO)));
        // then
        resultActions.andExpect(status().isCreated());
        List<Postcode> postcodes = postCodeRepository.findAll();
        assertThat(postcodes)
                .usingElementComparatorIgnoringFields("id")
                .contains(postcode);
    }

    @Test
    void getAll() throws Exception {
        PostcodeDTO postcodeDTO = new PostcodeDTO(2567, "Mount Annan");
        PostcodeDTO postcodeDTO2 = new PostcodeDTO(2588, "WALLENDBEEN");

        mockMvc.perform(MockMvcRequestBuilders.post("/postcode")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(postcodeDTO)));

        mockMvc.perform(MockMvcRequestBuilders.post("/postcode")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(postcodeDTO2)));
        // when
        MvcResult getEmployeesResult = mockMvc.perform(get("/employees")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        String contentAsString = getEmployeesResult
                .getResponse().getContentAsString();

        List<Postcode> postcodeList = objectMapper.readValue(
                contentAsString,
                new TypeReference<>() {
                }
        );

        //then
        List<Postcode> employees = postCodeRepository.findAll();
        assertThat(postcodeList.size()).isEqualTo(employees.size());
    }

    @Test
    void getByPostcode() throws Exception {
        PostcodeDTO postcodeDTO = new PostcodeDTO(2567, "Mount Annan");

        mockMvc.perform(MockMvcRequestBuilders.post("/postcode")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(postcodeDTO)));

        MvcResult getEmployeesResult = mockMvc.perform(get("/postcode")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        String contentAsString = getEmployeesResult
                .getResponse().getContentAsString();

        List<Postcode> postcodeList = objectMapper.readValue(
                contentAsString,
                new TypeReference<>() {
                }
        );

        Integer postcodeToVerify = postcodeList.stream()
                .filter(e -> e.getPostcode().equals(postcodeDTO.getPostcode()))
                .map(Postcode::getPostcode)
                .findFirst()
                .orElseThrow(() ->
                        new IllegalStateException(
                                "Postcode not found"));

        // when
        ResultActions resultActions = mockMvc
                .perform(get("/postcode/postcode/" + postcodeToVerify));

        // then
        resultActions.andExpect(status().isOk());
        Optional<Postcode> postcodeFromRepo = postCodeRepository.findByPostcode(postcodeToVerify);
        assertThat(postcodeFromRepo.get().getPostcode()).isEqualTo(postcodeToVerify);


    }

    @Test
    void getBySuburb() throws Exception {
        PostcodeDTO postcodeDTO = new PostcodeDTO(2588, "WALLENDBEEN");

        mockMvc.perform(MockMvcRequestBuilders.post("/postcode")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(postcodeDTO)));

        MvcResult getEmployeesResult = mockMvc.perform(get("/postcode")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        String contentAsString = getEmployeesResult
                .getResponse().getContentAsString();

        List<Postcode> postcodeList = objectMapper.readValue(
                contentAsString,
                new TypeReference<>() {
                }
        );

        String suburbToVerify = postcodeList.stream()
                .filter(e -> e.getSuburb().equals(postcodeDTO.getSuburb()))
                .map(Postcode::getSuburb)
                .findFirst()
                .orElseThrow(() ->
                        new IllegalStateException(
                                "Postcode with suburb not found"));

        // when
        ResultActions resultActions = mockMvc
                .perform(get("/postcode/suburb/" + suburbToVerify));

        // then
        resultActions.andExpect(status().isOk());
        Optional<Postcode> postcodeFromRepo = postCodeRepository.findBySuburb(suburbToVerify);
        assertThat(postcodeFromRepo.get().getSuburb()).isEqualTo(suburbToVerify);

    }
}