package com.dkrichards.studentapplication.models;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Term Model
 */
public class Term {
    private final int id;
    private final String title;
    private final String startDate;
    private final String endDate;

    private SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yy");

    /**
     * Build a term instance
     *
     * @param id
     * @param title
     * @param startDate
     * @param endDate
     */
    public Term(int id, String title, String startDate, String endDate) {
        this.id = id;
        this.title = title;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    /**
     * Get the ID
     *
     * @return id
     */
    public int getId() {
        return this.id;
    }

    /**
     * Get title
     *
     * @return title
     */
    public String getTitle() {
        return this.title;
    }

    /**
     * Get the start date
     *
     * @return
     */
    public String getStartDate() {
        return this.startDate;
    }

    /**
     * Get the end date
     *
     * @return endDate
     */
    public String getEndDate() {
        return this.endDate;
    }

    /**
     * Get a string representing the date range of this term
     * @return
     */
    public String getDates() {
        return startDate + " to " + endDate;
    }

    /**
     * Create a string representation
     *
     * @return string repr
     */
    @Override
    public String toString() {
        return title + " (" + getDates() + ")";
    }

    /**
     * Validate the model
     *
     * @return is it valid?
     */
    public boolean isValid() {
        if (title.isEmpty() || startDate.isEmpty() || endDate.isEmpty()) {
            return false;
        }

        try {
            // Make sure the dates are in the correct format
            Date start = dateFormat.parse(startDate);
            Date end = dateFormat.parse(endDate);

            // Start has to be before end
            if (!start.before(end)) {
                return false;
            }
        } catch (ParseException e) {
            e.printStackTrace();
            return false;
        }

        return true;
    }
}
