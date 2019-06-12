package com.group4.sodacrazy;

import org.junit.Test;

import static junit.framework.TestCase.assertEquals;

public class PunchCardActivity_valid {

    @Test
    public void checkADD_PUNCH_REQUEST() {
        PunchCardActivity pca = new PunchCardActivity();
        int result = pca.ADD_PUNCH_REQUEST;
        assertEquals(result, 1);
    }
}
