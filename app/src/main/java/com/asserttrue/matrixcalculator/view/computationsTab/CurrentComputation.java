package com.asserttrue.matrixcalculator.view.computationsTab;

import com.asserttrue.matrixcalculator.model.computations.Computation;

/**
 * The matrix
 */
public class CurrentComputation {
    private static CurrentComputation ourInstance = new CurrentComputation();

    private Computation computation;

    public static CurrentComputation getInstance() {
        return ourInstance;
    }

    public Computation getComputation() {
        return computation;
    }

    public void setComputation(Computation current) {
        computation = current;
    }

    private CurrentComputation() {
    }
}
