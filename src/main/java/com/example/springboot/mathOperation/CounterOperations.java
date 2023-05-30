package com.example.springboot.mathOperation;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component(value="counter")
@Scope("singleton")
public class CounterOperations {
    private static int count;

    public synchronized void add()
    {
        count++;
    }

    public int getCount()
    {
        return count;
    }
}