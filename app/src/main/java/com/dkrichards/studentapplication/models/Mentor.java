package com.dkrichards.studentapplication.models;

/**
 * Mentor Model
 */
public class Mentor {
    private int id;
    private String name;
    private String phone;
    private String email;
    private int courseId;

    /**
     * Build a mentor instance
     *
     * @param id
     * @param name
     * @param phone
     * @param email
     * @param courseId
     */
    public Mentor(int id, String name, String phone, String email, int courseId) {
        this.id = id;
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.courseId = courseId;
    }

    /**
     * Get the ID
     *
     * @return id
     */
    public int getId() { return this.id; }

    /**
     * Get assessment email
     *
     * @return email
     */
    public String getEmail() {
        return this.email;
    }

    /**
     * Get mentor name
     *
     * @return name
     */
    public String getName() {
        return this.name;
    }

    /**
     * Get mentor phone number
     *
     * @return phone
     */
    public String getPhone() {
        return this.phone;
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
        return this.name;
    }

    /**
     * Validate the model
     *
     * @return is it valid?
     */
    public boolean isValid() {
        if (name.isEmpty() || email.isEmpty() || phone.isEmpty()) {
            return false;
        }

        return true;
    }
}