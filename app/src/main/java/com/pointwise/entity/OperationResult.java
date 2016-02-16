package com.pointwise.entity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wbatista on 2/12/16.
 */
public class OperationResult<TResult> {
    /**
     * Indicate if operate successful
     */
    private boolean operationCompletedSuccessfully;
    /**
     * Errors that occurred in this operation
     */
    private List<OperationError> operationErrors;
    /**
     * Operation result
     */
    private TResult result;

    /**
     * Default constructure
     */
    public OperationResult() {
        this.operationErrors = new ArrayList<>();
    }

    public boolean isOperationCompletedSuccessfully() {
        return operationCompletedSuccessfully;
    }

    public void setOperationCompletedSuccessfully(boolean operationCompletedSuccessfully) {
        this.operationCompletedSuccessfully = operationCompletedSuccessfully;
    }

    public List<OperationError> getOperationErrors() {
        return operationErrors;
    }

    public void setOperationErrors(List<OperationError> operationErrors) {
        this.operationErrors = operationErrors;
    }

    public TResult getResult() {
        return result;
    }

    public void setResult(TResult result) {
        this.result = result;
    }
}