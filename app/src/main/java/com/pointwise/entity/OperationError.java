package com.pointwise.entity;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by wbatista on 2/12/16.
 */
public class OperationError extends BaseEntity implements Parcelable {
    /**
     * Error code
     */
    private String errorCode;

    /**
     * Error message
     */
    private String errorMessage;

    /**
     * Default constructure
     */
    public OperationError() {
    }

    /**
     *
     * This constructure uses pre-populated data
     * Construtor padrao que utiliza os parametros para pre-popular os atributos da
     * instancia do erro que esta sendo criada.
     *
     * @param errorCode String
     * @param errorMessage String
     */
    public OperationError(String errorCode, String errorMessage) {
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
    }

    public String getErrorCode() {
        return errorCode;
    }
    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }
    public String getErrorMessage() {
        return errorMessage;
    }
    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel out, int flags) {
        out.writeString(this.errorCode);
        out.writeString(this.errorMessage);
    }

    public static final Parcelable.Creator<OperationError> CREATOR = new Parcelable.Creator<OperationError>() {
        public OperationError createFromParcel(Parcel in) {

            OperationError operationError = new OperationError();
            operationError.errorCode = in.readString();
            operationError.errorMessage = in.readString();

            return operationError;
        }

        public OperationError[] newArray(int size) {
            return new OperationError[size];
        }
    };
}