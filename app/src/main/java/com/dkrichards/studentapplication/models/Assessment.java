package com.dkrichards.studentapplication.models;

import java.text.ParseException;
import java.text.SimpleDateFormat;

/**
 * Assessment Model
 */
public class Assessment {
    private int id;
    private String name;
    private String type;
    private String date;
    private int courseId;

    private SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yy");

    /**
     * Build an Assessment instance
     *
     * @param id
     * @param name
     * @param type
     * @param date
     * @param courseId
     */
    public Assessment(int id, String name, String type, String date, int courseId) {
        this.id = id;
        this.name = name;
        this.type = type;
        this.date = date;
        this.courseId = courseId;
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
     * Get assessment name
     *
     * @return name
     */
    public String getName() {
        return this.name;
    }

    /**
     * Get the assessment type, either "Objective Assessment" or "Performance Assessment"
     *
     * @return type
     */
    public String getType() {
        return this.type;
    }

    /**
     * Get the assessment date
     *
     * @return date
     */
    public String getDate() {
        return this.date;
    }

    /**
     * Get the related course ID.
     *
     * @return associated course ID
     */
    public int getCourseId() {
        return this.courseId;
    }

    /**
     * Create a string representation
     *
     * @return string repr
     */
    @Override
    public String toString() {
        return this.name + " (" + this.type + ")";
    }

    /**
     * Validate the model
     *
     * @return is it valid?
     */
    public boolean isValid() {
        if (name.isEmpty() || type.isEmpty() || date.isEmpty()) {
            return false;
        }

        try {
            // Make sure the dates are in the correct format
            dateFormat.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
            return false;
        }

        return true;
    }
}