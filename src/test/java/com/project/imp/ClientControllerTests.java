package com.project.imp;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.imp.controller.ClientController;
import com.project.imp.entity.Claim;
import com.project.imp.entity.Client;
import com.project.imp.entity.InsurancePolicy;
import com.project.imp.service.ClientService;
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

@WebMvcTest(ClientController.class)
public class ClientControllerTests {

    @MockBean
    private ClientService clientService;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @WithMockUser(username = "user", roles = "USER")
    void shouldReturnListOfClients() throws Exception {
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        List<Claim> claimList1 = new ArrayList<>(Arrays.asList(
                new Claim(1, "claim1", "description1", LocalDate.parse("04-04-2023", dateTimeFormatter), false),
                new Claim(2, "claim2", "description2", LocalDate.parse("05-04-2023", dateTimeFormatter), true)));
        List<Claim> claimList2 = new ArrayList<>(Arrays.asList(
                new Claim(3, "claim3", "description3", LocalDate.parse("06-04-2023", dateTimeFormatter), false),
                new Claim(4, "claim4", "description4", LocalDate.parse("07-04-2023", dateTimeFormatter), true)));
        List<InsurancePolicy> insurancePolicyList1 = new ArrayList<>(Arrays.asList(
                new InsurancePolicy(1, "policy1", "type1", 10000, 1000, LocalDate.parse("04-04-2023", dateTimeFormatter), LocalDate.parse("05-04-2023", dateTimeFormatter), claimList1),
                new InsurancePolicy(2, "policy2", "type2", 9000, 900, LocalDate.parse("05-04-2023", dateTimeFormatter), LocalDate.parse("06-04-2023", dateTimeFormatter), claimList2)));

        List<Claim> claimList3 = new ArrayList<>(Arrays.asList(
                new Claim(5, "claim5", "description5", LocalDate.parse("08-04-2023", dateTimeFormatter), false),
                new Claim(6, "claim6", "description6", LocalDate.parse("09-04-2023", dateTimeFormatter), true)));
        List<Claim> claimList4 = new ArrayList<>(Arrays.asList(
                new Claim(7, "claim7", "description7", LocalDate.parse("10-04-2023", dateTimeFormatter), false),
                new Claim(8, "claim8", "description8", LocalDate.parse("11-04-2023", dateTimeFormatter), true)));
        List<InsurancePolicy> insurancePolicyList2 = new ArrayList<>(Arrays.asList(
                new InsurancePolicy(1, "policy1", "type1", 10000, 1000, LocalDate.parse("04-04-2023", dateTimeFormatter), LocalDate.parse("05-04-2023", dateTimeFormatter), claimList3),
                new InsurancePolicy(2, "policy2", "type2", 9000, 900, LocalDate.parse("05-04-2023", dateTimeFormatter), LocalDate.parse("06-04-2023", dateTimeFormatter), claimList4)));

        List<Client> clients = new ArrayList<>(Arrays.asList(
                new Client(1, "name1", LocalDate.parse("04-04-2020", dateTimeFormatter), "address1", "client1@mail.com", "1234567890", insurancePolicyList1),
                new Client(2, "name2", LocalDate.parse("05-05-2020", dateTimeFormatter), "address1", "client2@mail.com", "0987456123", insurancePolicyList2)));

        when(clientService.findAllClients()).thenReturn(clients);
        mockMvc.perform(get("/api/v1/clients"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(clients.size()))
                .andDo(print());
    }

    @Test
    @WithMockUser(username = "user", roles = "USER")
    void shouldReturnClaim() throws Exception {
        long id = 1;
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        List<Claim> claimList1 = new ArrayList<>(Arrays.asList(
                new Claim(1, "claim1", "description1", LocalDate.parse("04-04-2023", dateTimeFormatter), false),
                new Claim(2, "claim2", "description2", LocalDate.parse("05-04-2023", dateTimeFormatter), true)));
        List<Claim> claimList2 = new ArrayList<>(Arrays.asList(
                new Claim(3, "claim3", "description3", LocalDate.parse("06-04-2023", dateTimeFormatter), false),
                new Claim(4, "claim4", "description4", LocalDate.parse("07-04-2023", dateTimeFormatter), true)));
        List<InsurancePolicy> insurancePolicyList1 = new ArrayList<>(Arrays.asList(
                new InsurancePolicy(1, "policy1", "type1", 10000, 1000, LocalDate.parse("04-04-2023", dateTimeFormatter), LocalDate.parse("05-04-2023", dateTimeFormatter), claimList1),
                new InsurancePolicy(2, "policy2", "type2", 9000, 900, LocalDate.parse("05-04-2023", dateTimeFormatter), LocalDate.parse("06-04-2023", dateTimeFormatter), claimList2)));
        Client client = new Client(id, "name1", LocalDate.parse("04-04-2020", dateTimeFormatter), "address1", "client1@mail.com", "1234567890", insurancePolicyList1);

        when(clientService.fetchClientById(id)).thenReturn(client);
        mockMvc.perform(get("/api/v1/clients/{id}", id))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(id))
                .andExpect(jsonPath("$.name").value(client.getName()))
                .andExpect(jsonPath("$.address").value(client.getAddress()))
                .andExpect(jsonPath("$.email").value(client.getEmail()))
                .andDo(print());
    }

    @Test
    @WithMockUser(username = "user", roles = "USER")
    void shouldCreateClient() throws Exception {
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        List<Claim> claimList1 = new ArrayList<>(Arrays.asList(
                new Claim(1, "claim1", "description1", LocalDate.parse("04-04-2023", dateTimeFormatter), false),
                new Claim(2, "claim2", "description2", LocalDate.parse("05-04-2023", dateTimeFormatter), true)));
        List<Claim> claimList2 = new ArrayList<>(Arrays.asList(
                new Claim(3, "claim3", "description3", LocalDate.parse("06-04-2023", dateTimeFormatter), false),
                new Claim(4, "claim4", "description4", LocalDate.parse("07-04-2023", dateTimeFormatter), true)));
        List<InsurancePolicy> insurancePolicyList1 = new ArrayList<>(Arrays.asList(
                new InsurancePolicy(1, "policy1", "type1", 10000, 1000, LocalDate.parse("04-04-2023", dateTimeFormatter), LocalDate.parse("05-04-2023", dateTimeFormatter), claimList1),
                new InsurancePolicy(2, "policy2", "type2", 9000, 900, LocalDate.parse("05-04-2023", dateTimeFormatter), LocalDate.parse("06-04-2023", dateTimeFormatter), claimList2)));
        Client client = new Client(1, "name1", LocalDate.parse("04-04-2020", dateTimeFormatter), "address1", "client1@mail.com", "1234567890", insurancePolicyList1);

        mockMvc.perform(post("/api/v1/clients").with(csrf()).contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(client)))
                .andExpect(status().isCreated())
                .andDo(print());
    }

    @Test
    @WithMockUser(username = "user", roles = "USER")
    void shouldDeleteClient() throws Exception {
        long id = 1;

        doNothing().when(clientService).deleteClient(id);
        mockMvc.perform(delete("/api/v1/clients/{id}", id).with(csrf()))
                .andExpect(status().isNoContent())
                .andDo(print());
    }
}
