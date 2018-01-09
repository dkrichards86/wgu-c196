package com.dkrichards.studentapplication.databases.daos;

import com.dkrichards.studentapplication.models.Mentor;

import java.util.List;

/**
 * This interface structure was largely derived from best practices laid out in:
 * https://wale.oyediran.me/2015/04/02/android-sqlite-dao-design/
 */
interface MentorDAOInterface {
    boolean addMentor(Mentor mentor);

    Mentor getMentorById(int mentorId);

    List<Mentor> getMentorsByCourse(int courseId);

    int getMentorCount();

    List<Mentor> getMentors();

    boolean removeMentor(Mentor mentor);

    boolean updateMentor(Mentor mentor);
}
