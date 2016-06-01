package com.asserttrue.matrixcalculator.model.computations;

public class ErrorStep implements Step {

    private final String errorMessage;

    public ErrorStep(String details){
        errorMessage = details;
    }

    @Override
    public int getLayoutType() {
        //TODO make a layout type devoted to errorsteps
        return -1;
    }

    @Override
    public String getExplanation() {
        return "This matrix yielded no result.";
    }
}
