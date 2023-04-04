package com.project.imp;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.imp.controller.InsurancePolicyController;
import com.project.imp.entity.Claim;
import com.project.imp.entity.InsurancePolicy;
import com.project.imp.service.InsurancePolicyService;
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

@WebMvcTest(InsurancePolicyController.class)
public class InsurancePolicyControllerTests {

    @MockBean
    private InsurancePolicyService insurancePolicyService;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @WithMockUser(username = "user", roles = "USER")
    void shouldReturnListOfInsurancePolicies() throws Exception {
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        List<Claim> claimList1 = new ArrayList<>(Arrays.asList(
                new Claim(1, "claim1", "description1", LocalDate.parse("04-04-2023", dateTimeFormatter), false),
                new Claim(2, "claim2", "description2", LocalDate.parse("05-04-2023", dateTimeFormatter), true)));
        List<Claim> claimList2 = new ArrayList<>(Arrays.asList(
                new Claim(3, "claim3", "description3", LocalDate.parse("06-04-2023", dateTimeFormatter), false),
                new Claim(4, "claim4", "description4", LocalDate.parse("07-04-2023", dateTimeFormatter), true)));
        List<InsurancePolicy> insurancePolicies = new ArrayList<>(Arrays.asList(
                new InsurancePolicy(1, "policy1", "type1", 10000, 1000, LocalDate.parse("04-04-2023", dateTimeFormatter), LocalDate.parse("05-04-2023", dateTimeFormatter), claimList1),
                new InsurancePolicy(2, "policy2", "type2", 9000, 900, LocalDate.parse("05-04-2023", dateTimeFormatter), LocalDate.parse("06-04-2023", dateTimeFormatter), claimList2)));

        when(insurancePolicyService.findAllInsurancePolicies()).thenReturn(insurancePolicies);
        mockMvc.perform(get("/api/v1/policies"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(insurancePolicies.size()))
                .andDo(print());
    }

    @Test
    @WithMockUser(username = "user", roles = "USER")
    void shouldReturnInsurancePolicy() throws Exception {
        long id = 1;
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        List<Claim> claimList1 = new ArrayList<>(Arrays.asList(
                new Claim(1, "claim1", "description1", LocalDate.parse("04-04-2023", dateTimeFormatter), false),
                new Claim(2, "claim2", "description2", LocalDate.parse("05-04-2023", dateTimeFormatter), true)));

        InsurancePolicy insurancePolicy = new InsurancePolicy(id, "policy1", "type1", 10000, 1000, LocalDate.parse("04-04-2023", dateTimeFormatter), LocalDate.parse("05-04-2023", dateTimeFormatter), claimList1);

        when(insurancePolicyService.findInsurancePolicyById(id)).thenReturn(insurancePolicy);
        mockMvc.perform(get("/api/v1/policies/{id}", id))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(id))
                .andExpect(jsonPath("$.type").value(insurancePolicy.getType()))
                .andExpect(jsonPath("$.premium").value(insurancePolicy.getPremium()))
                .andDo(print());
    }

    @Test
    @WithMockUser(username = "user", roles = "USER")
    void shouldCreateInsurancePolicy() throws Exception {
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        List<Claim> claimList1 = new ArrayList<>(Arrays.asList(
                new Claim(1, "claim1", "description1", LocalDate.parse("04-04-2023", dateTimeFormatter), false),
                new Claim(2, "claim2", "description2", LocalDate.parse("05-04-2023", dateTimeFormatter), true)));
        InsurancePolicy insurancePolicy = new InsurancePolicy(100, "policy1", "type1", 10000, 1000, LocalDate.parse("04-04-2023", dateTimeFormatter), LocalDate.parse("05-04-2023", dateTimeFormatter), claimList1);

        mockMvc.perform(post("/api/v1/policies").with(csrf()).contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(insurancePolicy)))
                .andExpect(status().isCreated())
                .andDo(print());
    }

    @Test
    @WithMockUser(username = "user", roles = "USER")
    void shouldDeleteInsurancePolicy() throws Exception {
        long id = 1;
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        List<Claim> claimList1 = new ArrayList<>(Arrays.asList(
                new Claim(1, "claim1", "description1", LocalDate.parse("04-04-2023", dateTimeFormatter), false),
                new Claim(2, "claim2", "description2", LocalDate.parse("05-04-2023", dateTimeFormatter), true)));
        InsurancePolicy insurancePolicy = new InsurancePolicy(id, "policy1", "type1", 10000, 1000, LocalDate.parse("04-04-2023", dateTimeFormatter), LocalDate.parse("05-04-2023", dateTimeFormatter), claimList1);

        doNothing().when(insurancePolicyService).deleteInsurancePolicy(id);
        mockMvc.perform(delete("/api/v1/policies/{id}", id).with(csrf()))
                .andExpect(status().isNoContent())
                .andDo(print());
    }
}
