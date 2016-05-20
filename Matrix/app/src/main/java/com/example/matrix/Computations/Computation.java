package com.example.matrix.Computations;


import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;

public abstract class Computation{

    protected List<Object> steps = new LinkedList<>();
    protected ListIterator iterSteps;

    protected void done(){
        iterSteps = steps.listIterator();
    }

    protected abstract boolean validate();

    protected abstract void compute();

    public Object nextStep(){ return iterSteps.next(); }

    public Object previousStep(){
        return iterSteps.previous();
    }

    public boolean isLastStep(){
        return iterSteps.hasNext();
    }

    public int getLength(){
        return steps.size();
    }
}
