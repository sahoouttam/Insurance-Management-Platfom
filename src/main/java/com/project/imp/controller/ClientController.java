package com.project.imp.controller;

import com.project.imp.entity.Client;
import com.project.imp.service.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/v1")
public class ClientController {

    @Autowired
    private final ClientService clientService;

    public ClientController(ClientService clientService) {
        this.clientService = clientService;
    }

    @GetMapping("/clients")
    //@PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<Client>> getAllClients() {
        List<Client> clients = clientService.findAllClients();
        return new ResponseEntity<>(clients, HttpStatus.OK);
    }

    @GetMapping("/clients/{id}")
    //@PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Client> getClientById(@PathVariable long id) {
        Client client = clientService.fetchClientById(id);
        return new ResponseEntity<>(client, HttpStatus.OK);
    }

    @PostMapping("/clients")
    //@PreAuthorize("hasRole('USER') or hasRole('USER') or hasRole('USER')")
    public ResponseEntity<Client> createClient(@Valid @RequestBody Client client) {
        Client newClient = clientService.createClient(client);
        return new ResponseEntity<>(newClient, HttpStatus.CREATED);
    }

    @PutMapping("/clients/{id}")
    //@PreAuthorize("hasRole('USER') or hasRole('USER') or hasRole('USER')")
    public ResponseEntity<Client> createClient(@PathVariable long id, @Valid @RequestBody Client client) {
        Client newClient = clientService.updateClient(id, client);
        return new ResponseEntity<>(newClient, HttpStatus.OK);
    }

    @DeleteMapping("/clients/{id}")
    //@PreAuthorize("hasRole('USER') or hasRole('USER') or hasRole('USER')")
    public ResponseEntity<HttpStatus> deleteClient(@PathVariable long id) {
        clientService.deleteClient(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
