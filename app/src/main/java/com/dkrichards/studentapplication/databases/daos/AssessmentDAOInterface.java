package com.dkrichards.studentapplication.databases.daos;

import com.dkrichards.studentapplication.models.Assessment;

import java.util.List;

/**
 * This interface structure was largely derived from best practices laid out in:
 * https://wale.oyediran.me/2015/04/02/android-sqlite-dao-design/
 */
public interface AssessmentDAOInterface {
    boolean addAssessment(Assessment assessment);

    Assessment getAssessmentById(int assessmentId);

    List<Assessment> getAssessmentsByCourse(int courseId);

    int getAssessmentCount();

    List<Assessment> getAssessments();

    boolean removeAssessment(Assessment assessment);

    boolean updateAssessment(Assessment assessment);
}
