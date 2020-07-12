package com.validity.monolithstarter.service;

import org.springframework.stereotype.Service;

@Service
public class DuplicateContactService {
    public String getDuplicateContact() {
        String filepath = "./../normal.csv";

        return "Hello from the server - Pabloooo!";
    }
}
