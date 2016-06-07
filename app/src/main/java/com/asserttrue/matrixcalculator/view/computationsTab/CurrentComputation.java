package com.asserttrue.matrixcalculator.view.computationsTab;

import com.asserttrue.matrixcalculator.view.stepViews.Step;

import java.util.List;

/**
 * The matrix
 */
public class CurrentComputation {
    private static CurrentComputation ourInstance = new CurrentComputation();
    private List<Step> steps;

    public static CurrentComputation getInstance() {
        return ourInstance;
    }

    public List<Step> getSteps() {
        return steps;
    }

    public void setSteps(List<Step> current) {
        steps = current;
    }

    private CurrentComputation() {
    }
}
