package com.project.imp.service;

import com.project.imp.entity.Client;
import com.project.imp.exception.ResourceNotException;
import com.project.imp.repository.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ClientService {

    @Autowired
    private final ClientRepository clientRepository;

    public ClientService(ClientRepository clientRepository) {
        this.clientRepository = clientRepository;
    }

    public List<Client> findAllClients() {
        return clientRepository.findAll();
    }

    public Client fetchClientById(Long id) {
        return clientRepository.findById(id)
                .orElseThrow(() -> new ResourceNotException("Not found client with id: " + id));
    }

    public Client createClient(Client client) {
        return clientRepository.save(client);
    }

    public Client updateClient(long id, Client client) {
        Client newClient = clientRepository.findById(id)
                .orElseThrow(() -> new ResourceNotException("Not found client with id: " + id));
        newClient.setName(client.getName());
        newClient.setDateOfBirth(client.getDateOfBirth());
        newClient.setAddress(client.getAddress());
        newClient.setEmail(client.getEmail());
        newClient.setPhoneNumber(client.getPhoneNumber());
        newClient.setInsurancePolicies(client.getInsurancePolicies());
        return newClient;
    }

    public void deleteClient(long id) {
        clientRepository.deleteById(id);
    }
}
