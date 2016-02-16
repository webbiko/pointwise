package com.pointwise.delegate;

import com.pointwise.entity.OperationError;

import java.util.List;

/**
 * Created by wbatista on 2/12/16.
 */
public abstract  class OperationListener<TResult> {

    public abstract void onOperationSuccess(TResult result);

    public void onOperationError(TResult result, List<OperationError> errors) {

    }

    public void onOperationCancelled() {

    }
}