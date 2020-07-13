package com.validity.monolithstarter.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.validity.monolithstarter.MonolithStarterApp;
import com.validity.monolithstarter.domain.ContactModel;
import com.validity.monolithstarter.service.DuplicateContactService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
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
import java.util.stream.Collectors;

import javax.inject.Inject;

@RestController
@RequestMapping("/api")
public class DuplicateContactController {

    private static final Logger log = LoggerFactory.getLogger(MonolithStarterApp.class);

    @Inject
    private DuplicateContactService duplicateContactService;

    @Autowired
    ObjectMapper mapper;

    @PostMapping("/parse-duplicate-contact")
    public ObjectNode getDuplicateContact(@RequestParam("file") MultipartFile file) {

        // ObjectNode and ArrayNode (from fasterxml.jackson) are used to create JSON when returning.

        // TODO: remove hardcoded keys and error messages.

        ObjectNode result = mapper.createObjectNode();
        if (file.isEmpty()) {
            result.put("status", "error");
            result.put("error", "The submitted file was empty. Please check and try again.");
        } else {

            // This parses the file into a list of ContactModel
            // Note: the delimiter could be configurable, maybe part of the POST request
            List<ContactModel> contacts = this.parseCSV(file, ",");

            List<List<ContactModel>> dup = duplicateContactService.getDuplicateContacts(contacts);

            ArrayNode duplicatesNode = result.putArray("duplicates");
            for (List<ContactModel> duplicates : dup) { // TODO: parallelize?
                ObjectNode node = mapper.createObjectNode();

                ArrayNode contactsNode = node.putArray("contacts");
                for (ContactModel c : duplicates) {
                    contactsNode.add(c.getNode(mapper.createObjectNode()));
                }

                node.put("distance-score", -1); // TODO: show tht distance score


                duplicatesNode.add(node);
            }
            result.put("duplicated-groups-count", duplicatesNode.size());


            ArrayNode nonDuplicates = result.putArray("deduplicated");

            List<ContactModel> flatDup = dup.stream().flatMap(List::stream).collect(Collectors.toList());

            for (ContactModel contact : contacts) { // TODO: parallelize?
                if (!flatDup.contains(contact)) {
                    nonDuplicates.add(contact.getNode(mapper.createObjectNode()));
                }
            }
            result.put("non-duplicated-count", nonDuplicates.size());

            result.put("status", "completed");

        }
        return result;
    }

    private List<ContactModel> parseCSV(MultipartFile file, String DELIMITER) {
        // This will parse each line into a ContactModel array.
        // This strictly assumes the CSV file is correctly formatted and has all and exactly the
        // right columns, in the right order. Using a library for CSV parsing, like OpenCSV would
        // be a good future improvement.

        // TODO: use OpenCSV?

        List<ContactModel> contacts = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(file.getInputStream()))) {
            String line = reader.readLine(); // This simply skips the first line (header)
            while ((line = reader.readLine()) != null) {
                // convert line into columns
                String[] columns = line.split(DELIMITER);

                contacts.add(new ContactModel( // TODO: move to constructor?
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
