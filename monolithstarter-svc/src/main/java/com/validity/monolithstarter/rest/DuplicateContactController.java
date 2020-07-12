package com.validity.monolithstarter.rest;

import com.validity.monolithstarter.MonolithStarterApp;
import com.validity.monolithstarter.domain.ContactModel;
import com.validity.monolithstarter.service.DuplicateContactService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

@RestController
@RequestMapping("/api")
public class DuplicateContactController {

    private static final Logger log = LoggerFactory.getLogger(MonolithStarterApp.class);

    @Inject
    private DuplicateContactService duplicateContactService;

    @PostMapping("/parse-duplicate-contact")
    public String getDuplicateContact(@RequestParam("file") MultipartFile file, Model model) {
        if (file.isEmpty()) {
            return "The submitted file was empty. Please check and try again.";
        } else {

            // This parses the file into a list of ContactModel
            // Note: the delimiter could be configurable, maybe part of the POST request
            List<ContactModel> contacts = this.parseCSV(file, ",");

            return contacts.toString();

        }
    }

    private List<ContactModel> parseCSV(MultipartFile file, String DELIMITER) {
        // This will parse each line into a ContactModel array.
        // This strictly assumes the CSV file is correctly formatted and has all and exactly the
        // right columns, in the right order. Using a library for CSV parsing, like OpenCSV would
        // be a good future improvement.

        List<ContactModel> contacts = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(file.getInputStream()))) {
            String line = reader.readLine(); // This simply skips the first line (header)
            while ((line = reader.readLine()) != null) {
                // convert line into columns
                String[] columns = line.split(DELIMITER);

                contacts.add(new ContactModel(
                    Integer.parseInt(columns[0]), // id
                    columns[1], // first_name
                    columns[2], // last_name
                    columns[3], // company
                    columns[4], // email
                    columns[5], // address1
                    columns[6], // address2
                    columns[7], // zip
                    columns[8], // city
                    columns[9], // state_long
                    columns[10], // state
                    columns[11] // phone
                ));
            }
            return contacts;
        } catch (Exception ex) {
            log.info(ex.getMessage());
            return contacts;
        }
    }
}
