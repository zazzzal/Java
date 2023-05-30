package com.example.springboot.bulk;

import java.util.List;

public class BulkRequest {
    private String operation;
    private List<String> pairs;

    public String getOperation() {
        return operation;
    }

    public List<String> getPairs() {
        return pairs;
    }


}

