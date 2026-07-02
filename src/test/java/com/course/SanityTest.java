package com.course;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class SanityTest {

    @Test
    public void verifySetupWorks() {
        int result = 2 + 2;
        assertEquals(4, result, "Basic math should work!");
        System.out.println("✅ Setup is working correctly!");
    }
}