package com.example.springboot;

import com.example.springboot.mathOperation.MathOperations;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(classes = Application.class)
class MathOperationTest {

    @Test
    public void testAdd() {
        int result = MathOperations.sum(2, 3);
        assertEquals(5, result);
    }

    @Test
    public void testSubtract() {
        int result = MathOperations.sub(5, 3);
        assertEquals(2, result);
    }

    @Test
    public void testMultiply() {
        int result = MathOperations.mul(2, 3);
        assertEquals(6, result);
    }

    @Test
    public void testDivide() {
        int result = MathOperations.div(6, 3);
        assertEquals(2, result);
    }
}
