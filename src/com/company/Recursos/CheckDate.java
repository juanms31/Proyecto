package com.company.Recursos;

import java.time.DateTimeException;
import java.time.LocalDate;

public class CheckDate {
    public CheckDate() {

    }

    public boolean isValidDate(String dateToCheck){

        dateToCheck = dateToCheck.trim();

        if(dateToCheck.length() < 10){
            return false;
        }

        processDate(dateToCheck);

        if(year < 1900){
            return false;
        }

        try {
            LocalDate today = LocalDate.of(year, month, dayOfMonth);
            return true;
        }catch (DateTimeException e){
            return false;
        }
    }

    private void processDate(String dateToCheck) {
        String[] splittedDate = dateToCheck.split("-");

        dayOfMonth = Integer.parseInt(splittedDate[0]);
        month = Integer.parseInt(splittedDate[1]);
        year = Integer.parseInt(splittedDate[2]);
    }


    private int year;
    private int month;
    private int dayOfMonth;
}
