package org.launchcode.techjobs.console;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by LaunchCode
 */
public class JobData {
    //comma seperated values file connected here
    private static final String DATA_FILE = "resources/job_data.csv";
    private static Boolean isDataLoaded = false;

    private static ArrayList<HashMap<String, String>> allJobs;


    public static ArrayList<String> findAll(String field) {

        loadData();

        //if not already loaded
        ArrayList<String> values = new ArrayList<>();

        for (HashMap<String, String> row : allJobs) {
            String aValue = row.get(field);

            if (!values.contains(aValue)) {
                values.add(aValue);
            }
        }

        return values;
    }

    public static ArrayList<HashMap<String, String>> findAll() {
        loadData();

        return allJobs;
    }


    //after returning alljobs, results returned for job key/value pairs
    public static ArrayList<HashMap<String, String>> findByColumnAndValue(String column, String value) {

        //JIC
        loadData();

        ArrayList<HashMap<String, String>> jobs = new ArrayList<>();

        for (HashMap<String, String> row : allJobs) {

            String aValue = row.get(column);


            if (aValue.toLowerCase().contains(value.toLowerCase())) {
                jobs.add(row);
            }
        }

        return jobs;
    }
    //After returning jobs job value is loaded, inclusion of the search



    public static ArrayList<HashMap<String,String>> findByValue(String value){
        loadData();
        //JIC

        // Gets a list of columns

        ArrayList<HashMap<String,String>> jobs = new ArrayList<>();

        //searchs each column

        for(HashMap<String,String> row : allJobs){

            for(Map.Entry<String,String> column : row.entrySet()){
                if(column.getValue().toLowerCase().contains(value.toLowerCase())){
                    jobs.add(row);
                    break;
                    //break should for-each loop stop searching for value if found in one column
                    //i.e from book: if a listing has position type “Web - Front End” and name “Front end web dev” then searching for “web” should not include the listing twice.
                    //case in-sensitive
                }
            }
        }
        return jobs;
    }

    private static void loadData() {

        //loaded only once
        if (isDataLoaded) {
            return;
        }

        try {

            // comma-separated values pull out column-header for info and records
            Reader in = new FileReader(DATA_FILE);
            CSVParser parser = CSVFormat.RFC4180.withFirstRecordAsHeader().parse(in);
            List<CSVRecord> records = parser.getRecords();
            //not sure if primitive????
            Integer numberOfColumns = records.get(0).size();
            String[] headers = parser.getHeaderMap().keySet().toArray(new String[numberOfColumns]);

            allJobs = new ArrayList<>();

            //formatting
            for (CSVRecord record : records) {
                HashMap<String, String> newJob = new HashMap<>();

                for (String headerLabel : headers) {
                    newJob.put(headerLabel, record.get(headerLabel));
                }

                allJobs.add(newJob);
            }

            //Dont wanna do it again
            isDataLoaded = true;

        } catch (IOException e) {
            System.out.println("Error: load job data failed");
            e.printStackTrace();
        }
    }

}