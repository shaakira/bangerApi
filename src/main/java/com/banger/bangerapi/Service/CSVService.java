package com.banger.bangerapi.Service;

import com.banger.bangerapi.Exception.CustomException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class CSVService {

    public boolean checkLicense(String license){
        String delimiters = "\\s+|,\\s*|\\.\\s*";
        boolean flag=true;
        List<List<String>> records = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader("csv.csv"))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] values = line.split(delimiters);
                if(values[1].equals(license)){
                    flag=false;
                }
                records.add(Arrays.asList(values));
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return flag;
    }
}
