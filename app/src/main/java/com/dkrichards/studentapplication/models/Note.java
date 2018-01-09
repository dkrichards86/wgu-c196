package com.dkrichards.studentapplication.models;

/**
 * Note model
 */
public class Note {
    private int id;
    private String title;
    private String text;
    private int courseId;

    /**
     * Build a note instance
     *
     * @param id
     * @param title
     * @param text
     * @param courseId
     */
    public Note(int id, String title, String text, int courseId) {
        this.id = id;
        this.title = title;
        this.text = text;
        this.courseId = courseId;
    }

    /**
     * Get the ID
     *
     * @return id
     */
    public int getId() { return this.id; }

    /**
     * Get title
     *
     * @return title
     */
    public String getTitle() {
        return this.title;
    }


    /**
     * Get the text
     *
     * @return text
     */
    public String getText() {
        return this.text;
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
        return this.title;
    }

    /**
     * Validate the model
     *
     * @return is it valid?
     */
    public boolean isValid() {
        if (title.isEmpty() || text.isEmpty()) {
            return false;
        }

        return true;
    }
}