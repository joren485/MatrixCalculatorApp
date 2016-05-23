package com.asserttrue.matrixcalculator.model.computations;

import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;

public abstract class Computation{

    protected final List<Step> steps = new LinkedList<>();
    protected ListIterator iterSteps;
    protected String errorMessage = "";

    protected void execute(){
        if (validate()){
            compute();
        }
        else{
            steps.add(new ErrorStep(errorMessage));
        }

        iterSteps = steps.listIterator();
    }

    public abstract List<Step> getSteps();

    protected abstract boolean validate();

    protected abstract void compute();

    public Step nextStep(){ return (Step) iterSteps.next(); }

    public Step previousStep(){
        return (Step) iterSteps.previous();
    }

    public boolean isLastStep(){
        return iterSteps.hasNext();
    }

    public int getLength(){
        return steps.size();
    }
}
