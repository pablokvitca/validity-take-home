package com.validity.monolithstarter.service;

import com.validity.monolithstarter.MonolithStarterApp;
import com.validity.monolithstarter.domain.ContactModel;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class DuplicateContactService {

    private static final Logger log = LoggerFactory.getLogger(MonolithStarterApp.class);

    // NOTE: this should be move to some configuration file.
    // Values should be between 0 and 1.
    // Smaller values will give less tolerance to that field to be equal.

    private double WEIGHT_FIRST_NAME = 1;
    private double WEIGHT_LAST_NAME = 1;
    private double WEIGHT_COMPANY = 1;
    private double WEIGHT_EMAIL = 1.0/10000;
    private double WEIGHT_ADDRESS_1 = 1;
    private double WEIGHT_ADDRESS_2 = 1;
    private double WEIGHT_ZIP = 1;
    private double WEIGHT_STATE = 1;
    private double WEIGHT_STATE_LONG = 1;
    private double WEIGHT_PHONE = 1.0/10;

    private double MIN_DIFF = 15.0;
    // Values were chosen arbitrarily.

    public List<List<ContactModel>> getDuplicateContacts(List<ContactModel> contacts) {

        Set<ContactModel> found = new HashSet<>();

        // TODO: change this for a cross-product set, so not to include the same value both ways

        List<List<ContactModel>> duplicates = new ArrayList<>();



        // We  cross-check each contact to every other. // TODO: parallelize
        for (ContactModel a : contacts) {
            for (ContactModel b : contacts) {
                if (a != b // Skip if same intance
                    && !(found.contains(a) && found.contains(b)) // Skips if we already found both
                    && duplicate(a, b, MIN_DIFF)) {
                    duplicates.add(Arrays.asList(a, b));
                    found.add(a);
                    found.add(b);
                }
            }
        }

        return duplicates;
    }

    /**
     * Compares Contact a and b to each other and determine if they are duplicate. We use minDiff
     * to determine "how different" the data should be.
     *
     * @param a A ContactModel to compare
     * @param b A ContactModel to compare
     * @param minDiff the min difference
     * @return true is the 2 contacts (a & b) are duplicate (by this heuristic)
     */
    private boolean duplicate(ContactModel a, ContactModel b, double minDiff) {
        if (a != b) { // Simple check that a and b are not the same instance
            return this.heuristicDistance(a, b) < minDiff;
        }
        return true;
    }

    private double heuristicDistance(ContactModel a, ContactModel b) {
        // Thinking: if some values being similar might be more important for duplicates than others
        // Examples: 2 contacts with the exact same address but completely different names/emails
        // could live (or work if its work address) in the same place and not be duplicates.

        double val = 0;

        val += WEIGHT_FIRST_NAME * levenshtein(a.getFirstName(), b.getFirstName());
        val += WEIGHT_LAST_NAME * levenshtein(a.getLastName(), b.getLastName());
        val += WEIGHT_COMPANY * levenshtein(a.getCompany(), b.getCompany());
        val += WEIGHT_EMAIL * levenshtein(a.getEmail(), b.getEmail());
        val += WEIGHT_ADDRESS_1 * levenshtein(a.getAddress1(), b.getAddress1());
        val += WEIGHT_ADDRESS_2 * levenshtein(a.getAddress2(), b.getAddress2());
        val += WEIGHT_ZIP * levenshtein(a.getZip(), b.getZip());
        val += WEIGHT_STATE * levenshtein(a.getState(), b.getState());
        val += WEIGHT_STATE_LONG * levenshtein(a.getStateLong(), b.getStateLong());
        val += WEIGHT_PHONE * levenshtein(a.getPhone(), b.getPhone());

        return val;
    }

    private int levenshtein(String a, String b) {
        // TODO: consider using differnent weights for substitution, addition, deletion,
        // take in as parameters
        int[][] dp = new int[a.length() + 1][b.length() + 1];

        try {
            for (int i = 0; i <= a.length(); i++) {
                for (int j = 0; j <= b.length(); j++) {
                    if (i == 0) {
                        dp[i][j] = j;
                    } else if (j == 0) {
                        dp[i][j] = i;
                    } else {
                        if (a.charAt(i - 1) == b.charAt(j - 1)) {
                            dp[i][j] = dp[i - 1][j - 1];
                        } else {
                            dp[i][j] = min(dp[i - 1][j - 1], dp[i - 1][j], dp[i][j - 1]) + 1;
                        }
                    }
                }
            }
        } catch (Exception e) {
            log.info("EEEEH", e.getMessage());
        }

        return dp[a.length()][b.length()];
    }

    private static int min(int... numbers) {
        return Arrays.stream(numbers).min().orElse(Integer.MAX_VALUE);
    }
}
