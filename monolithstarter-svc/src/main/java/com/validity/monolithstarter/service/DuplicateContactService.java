package com.validity.monolithstarter.service;

import com.validity.monolithstarter.domain.ContactModel;

import org.checkerframework.checker.units.qual.C;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class DuplicateContactService {

    public List<List<ContactModel>> getDuplicateContacts(List<ContactModel> contacts) {

        Set<ContactModel> found = new HashSet<>();

        // TODO: change this for a cross-product set, so not to include the same value both ways

        List<List<ContactModel>> duplicates = new ArrayList<>();

        int MIN_DIFF = 5;

        // We  cross-check each contact to every other. // TODO: parallelize
        for (ContactModel a : contacts) {
            for (ContactModel b : contacts) {
                if (a != b // Skip if same intance
                    && !(found.contains(a) && found.contains(b)) // Skips if we already found both
                    && duplicate(a, b, 5)) {
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
    private boolean duplicate(ContactModel a, ContactModel b, int minDiff) {
        if (a != b) { // Simple check that a and b are not the same instance
            int completeLev = levenshtein(a.toString(), b.toString());

            return completeLev < minDiff;

            // TODO: use heuristicDistance
        }
        return true;
    }

    private int heuristicDistance(ContactModel a, ContactModel b) {
        return Integer.MAX_VALUE; // TODO: assign different weights to each field on Contact Model
        // Thinking: if some values being similar might be more important for duplicates than others
        // Examples: 2 contacts with the exact same address but completely different names/emails
        // could live (or work if its work address) in the same place and not be duplicates.
    }

    private int levenshtein(String a, String b) {
        int[][] dp = new int[a.length() + 1][b.length() + 1];

        for (int i = 0; i <= a.length(); i++) {
            for (int j = 0; j <= b.length(); j++) {
                if (i == 0) {
                    dp[i][j] = j;
                }
                else if (j == 0) {
                    dp[i][j] = i;
                }
                else {
                    if (a.charAt(i - 1) == b.charAt(j - 1)) {
                        dp[i][j] = dp[i - 1][j - 1];
                    } else {
                        dp[i][j] = min(dp[i - 1][j - 1], dp[i - 1][j], dp[i][j - 1]) + 1;
                    }
                }
            }
        }

        return dp[a.length()][b.length()];
    }

    private static int min(int... numbers) {
        return Arrays.stream(numbers).min().orElse(Integer.MAX_VALUE);
    }
}
