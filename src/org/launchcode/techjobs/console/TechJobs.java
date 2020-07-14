package org.launchcode.techjobs.console;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

/**
 * Created by LaunchCode
 */
public class TechJobs {

    private static Scanner in = new Scanner(System.in);
    public static void main (String[] args) {

        // key/name pairs
        //initialize field map
        HashMap<String, String> columnChoices = new HashMap<>();
        columnChoices.put("core competency", "Skill");
        columnChoices.put("employer", "Employer");
        columnChoices.put("location", "Location");
        columnChoices.put("position type", "Position Type");
        columnChoices.put("all", "All");

        //top-level opts
        HashMap<String, String> actionChoices = new HashMap<>();
        actionChoices.put("search", "Search");
        actionChoices.put("list", "List");

        System.out.println("Lifting Off to LaunchCode's TechJobs App!");

        //must quit manually
        while (true) {

            String actionChoice = getUserSelection("View jobs by:", actionChoices);

            if (actionChoice.equals("list")) {

                String columnChoice = getUserSelection("List", columnChoices);

                if (columnChoice.equals("all")) {
                    printJobs(JobData.findAll());
                } else {

                    ArrayList<String> results = JobData.findAll(columnChoice);

                    System.out.println(" columnChoices.get(columnChoice)");

                    // Print list (skills/names/...)
                    for (String item : results) {
                        System.out.println(item);
                    }
                } } else {

                // how you want to search
                String searchField = getUserSelection("Search by:", columnChoices);

                //search by skill, search by employer

                System.out.println("Search term:");
                String searchTerm = in.nextLine();

                // search all
                if (searchField.equals("All")) {

                    printJobs(JobData.findByValue(searchTerm));
                } else {
                    printJobs(JobData.findByColumnAndValue(searchField, searchTerm));
                }
            }
        }
    }

    //Dictionary choices
    private static String getUserSelection(String menuHeader, HashMap<String, String> choices) {

        Integer choiceIdx;
        Boolean validChoice = false;
        String[] choiceKeys = new String[choices.size()];

        //Integer attached to give order
        Integer i = 0;
        for (String choiceKey : choices.keySet()) {
            choiceKeys[i] = choiceKey;
            i++;
        }

        do {

            System.out.println("\n" + menuHeader);

            //available choices printed
            for (Integer j = 0; j < choiceKeys.length; j++) {
                System.out.println("" + j + " - " + choices.get(choiceKeys[j]));
            }

            choiceIdx = in.nextInt();
            in.nextLine();

            //Validate user's input

            if (choiceIdx < 0 || choiceIdx >= choiceKeys.length) {
                System.out.println("Invalid choice.");
            } else {
                validChoice = true;
            }

        } while(!validChoice);

        return choiceKeys[choiceIdx];
    }


}
    // Print list of jobs
    private static void printJobs(@NotNull ArrayList<HashMap<String, String>> someJobs) {


        //for each loop to access every HashMap in someJobs
        for (HashMap<String, String> map : someJobs) {

            //divider
            System.out.println("*****");

            //loop through and print each key
            for (Map.Entry<String, String> job : map.entrySet()) {

                System.out.println(job.getKey() + ": " + job.getValue());
            }

            //divider
            System.out.println("*****"+"\n");
        }

        if (someJobs.isEmpty()) {
            System.out.println("No results found for your search!");
        }

    }
}