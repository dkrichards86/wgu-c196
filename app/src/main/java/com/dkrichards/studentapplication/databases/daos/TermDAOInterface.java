package com.dkrichards.studentapplication.databases.daos;

import com.dkrichards.studentapplication.models.Term;

import java.util.List;

/**
 * This interface structure was largely derived from best practices laid out in:
 * https://wale.oyediran.me/2015/04/02/android-sqlite-dao-design/
 */
interface TermDAOInterface {
    boolean addTerm(Term term);

    Term getTermById(int termId);

    int getTermCount();

    List<Term> getTerms();

    boolean removeTerm(Term term);

    boolean updateTerm(Term term);
}
