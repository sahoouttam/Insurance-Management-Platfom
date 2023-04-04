package com.project.imp;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.imp.controller.ClaimController;
import com.project.imp.entity.Claim;
import com.project.imp.service.ClaimService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ClaimController.class)
public class ClaimControllerTests {

    @MockBean
    private ClaimService claimService;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @WithMockUser(username = "user", roles = "USER")
    void shouldReturnListOfClaims() throws Exception {
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        List<Claim> claims = new ArrayList<>(Arrays.asList(
                new Claim(1, "claim1", "description1", LocalDate.parse("04-04-2023", dateTimeFormatter), false),
                new Claim(2, "claim2", "description2", LocalDate.parse("05-04-2023", dateTimeFormatter), true)));

        when(claimService.findAllClaims()).thenReturn(claims);
        mockMvc.perform(get("/api/v1/claims"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(claims.size()))
                .andDo(print());
    }

    @Test
    @WithMockUser(username = "user", roles = "USER")
    void shouldClaim() throws Exception {
        long id = 1;
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        Claim claim = new Claim(1, "claim1", "description1", LocalDate.parse("04-04-2023", dateTimeFormatter), false);

        when(claimService.findClaimById(id)).thenReturn(claim);
        mockMvc.perform(get("/api/v1/claims/{id}", id))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(id))
                .andExpect(jsonPath("$.claimNumber").value(claim.getClaimNumber()))
                .andExpect(jsonPath("$.description").value(claim.getDescription()))
                .andDo(print());
    }

    @Test
    @WithMockUser(username = "user", roles = "USER")
    void shouldCreateClaim() throws Exception {
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        Claim claim = new Claim(1, "claim1", "description1", LocalDate.parse("04-04-2023", dateTimeFormatter), false);

        mockMvc.perform(post("/api/v1/claims").with(csrf()).contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(claim)))
                .andExpect(status().isCreated())
                .andDo(print());
    }

    @Test
    @WithMockUser(username = "user", roles = "USER")
    void shouldDeleteClaim() throws Exception {
        long id = 1;

        doNothing().when(claimService).deleteClaim(id);
        mockMvc.perform(delete("/api/v1/claims/{id}", id).with(csrf()))
                .andExpect(status().isNoContent())
                .andDo(print());
    }
}
