package com.pointwise.util;

import java.util.Date;

/**
 * Created by wbatista on 2/13/16.
 */
public final class PointwiseUtil {
    public static boolean isDateEqual(final Date dateOne, final Date dateTwo) {
        if(dateOne == null || dateTwo == null) {
            throw new IllegalArgumentException("Invalid date format");
        }
        return dateOne.equals(dateTwo);
    }
}