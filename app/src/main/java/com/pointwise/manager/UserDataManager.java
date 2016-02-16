package com.pointwise.manager;

import android.os.AsyncTask;

import com.pointwise.businesscoordinator.UserDataBusinessCoordinator;
import com.pointwise.delegate.OperationListener;
import com.pointwise.entity.OperationError;
import com.pointwise.entity.OperationResult;
import com.pointwise.entity.UserData;
import com.pointwise.infraestructure.ApplicationConfiguration;
import com.pointwise.infraestructure.Log;
import com.pointwise.util.Constants;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wbatista on 2/12/16.
 * This class is responsilble for run asynchronous task
 */
public class UserDataManager extends BaseManager {

    private UserDataBusinessCoordinator mUserDataBusinessCoordinator;

    public UserDataManager() {
        this.mUserDataBusinessCoordinator = new UserDataBusinessCoordinator();
    }

    @Override
    public void setApplicationConfiguration(ApplicationConfiguration applicationConfiguration) {
        super.setApplicationConfiguration(applicationConfiguration);
        this.mUserDataBusinessCoordinator.setApplicationConfiguration(applicationConfiguration);
    }

    @Override
    public void setLog(Log log) {
        super.setLog(log);
        this.mUserDataBusinessCoordinator.setLog(log);
    }

    /**
     * This method is responsible for simulating the collected user data
     *
     * @param operationListener Callback for operation result
     */
    public void generateUserData(final OperationListener<UserData> operationListener) {

        AsyncTask<Void, Void, OperationResult<UserData>> task =
                new AsyncTask<Void, Void, OperationResult<UserData>>() {

                    @Override
                    protected OperationResult<UserData> doInBackground(Void... params) {
                        return mUserDataBusinessCoordinator.generateUserData();
                    }

                    @Override
                    protected void onPostExecute(OperationResult<UserData> result) {
                        removeFromTaskList(this);
                        if (result == null) {
                            List<OperationError> errors = new ArrayList<>();
                            errors.add(new OperationError(Constants.ErrorCodes.Application.ERROR_CODE_NULL_RESULT,
                                    "O resultado da operacao nao pode ser nulo."));
                            operationListener.onOperationError(null, errors);
                        } else {
                            if (result.isOperationCompletedSuccessfully()) {
                                operationListener.onOperationSuccess(result.getResult());
                            } else {
                                operationListener.onOperationError(result.getResult(), result.getOperationErrors());
                            }
                        }
                    }

                    @Override
                    protected void onCancelled() {
                        removeFromTaskList(this);
                        operationListener.onOperationCancelled();
                        super.onCancelled();
                    }

                };
        addToTaskList(task);
        task.execute();
    }
}