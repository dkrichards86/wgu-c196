package com.dkrichards.studentapplication.databases.daos;

import com.dkrichards.studentapplication.models.Course;

import java.util.List;

/**
 * This interface structure was largely derived from best practices laid out in:
 * https://wale.oyediran.me/2015/04/02/android-sqlite-dao-design/
 */
public interface CourseDAOInterface {
    boolean addCourse(Course course);

    Course getCourseById(int courseId);

    List<Course> getCoursesByTerm(int termId);

    int getCourseCount();

    List<Course> getCourses();

    boolean removeCourse(Course course);

    boolean updateCourse(Course course);
}
