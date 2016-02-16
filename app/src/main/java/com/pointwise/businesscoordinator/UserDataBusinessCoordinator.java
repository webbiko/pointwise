package com.pointwise.businesscoordinator;

import com.pointwise.entity.OperationResult;
import com.pointwise.entity.UserData;

import java.util.Date;
import java.util.Random;

/**
 * Created by wbatista on 2/12/16.
 */
public class UserDataBusinessCoordinator extends BaseBusinessCoordinator {

    public OperationResult<UserData> generateUserData() {
        final OperationResult<UserData> result = new OperationResult<>();
        final UserData userData = new UserData();
        userData.setData(generateRandomString());
        userData.setWeight(generateRandomWeight());
        userData.setDate(new Date(System.currentTimeMillis()));

        result.setOperationCompletedSuccessfully(true);
        result.setResult(userData);
        return result;
    }

    private String generateRandomString() {
        final String[] data = new String[] {
                "Pointwise", "Interview", "Technical", "Android", "Programming", "Java"
        };
        return data[generateRandomIndex(data.length - 1, 0)];
    }

    private int generateRandomWeight() {
        return generateRandomIndex(10, 1) + 1;
    }

    private int generateRandomIndex(final int max, final int min) {
        final Random random = new Random();
        return random.nextInt(max - min) + min;
    }
}