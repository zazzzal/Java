package com.example.springboot.mathOperation;

import java.util.List;

public class MathOperations {
    public static int sum(int a, int b) {
        return a + b;
    }

    public static int sub(int a, int b) {
        return a - b;
    }

    public static int mul(int a, int b) {return a * b;}

    public static int div(int a, int b) {
        return a / b;
    }

    public String resulting(int a, int b) {
        String result = "Sum: " + this.sum(a, b) + "\n" +
                "Difference: " + this.sub(a, b) + "\n" +
                "Multiply: " + this.mul(a, b) + "\n" +
                "Quotient: " + (double) this.div(a, b);
        return result;
    }

    public String bulkResulting(int a, int b, String operation) {
        String result = "";
        switch (operation) {
            case "sum":
                 result = "Sum: " + this.sum(a, b);
                break;
            case "sub":
                 result = "Sum: " + this.sub(a, b) ;
                break;
            case "mul":
                 result = "Multiply: " + this.mul(a, b);
                break;
            case "div":
                 result = "Quotient: " + (double) this.div(a, b);
                break;
            default:
                throw new IllegalArgumentException("Unsupported operation: " + operation);
        }
        return result;
    }

    public int max(List<Integer> numbers) {
        return numbers.stream().mapToInt(i -> i).max().orElseThrow();
    }

    public int min(List<Integer> numbers) {
        return numbers.stream().mapToInt(i -> i).min().orElseThrow();
    }

    public double average(List<Integer> numbers) {
        return numbers.stream().mapToDouble(i -> i).average().orElseThrow();
    }

    public String aggregatingFunc(List<Integer> numbers)
    {
        String result = "Min: " + this.min(numbers) +
                        "  Max: " + this.max(numbers) +
                        "  Average: " + this.average(numbers);
        return result;
    }
}
