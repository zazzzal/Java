package com.example.springboot.controllers;

import com.example.springboot.Repositories.PostAllActionRep;
import com.example.springboot.bulk.BulkRequest;
import com.example.springboot.cache.MathOperationInMemoryCache;
import com.example.springboot.errors.CustomExceptionHandler;
import com.example.springboot.mathOperation.CounterOperations;
import com.example.springboot.mathOperation.MathOperations;
import com.example.springboot.models.PostAllAction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;


@RestController
public class MathController {
    private final Logger logger = LoggerFactory.getLogger(MathController.class);
    private final MathOperations mathOperations = new MathOperations();
    @Autowired
    private PostAllActionRep postAllActionRep;
    @Autowired
    private CounterOperations counterOperations;
    @Autowired
    private MathOperationInMemoryCache<String, String> cache;

    @GetMapping("/calculate")
    public ResponseEntity<Object> calculate(@RequestParam(name = "first") int first, @RequestParam(name = "second") int second) {
        counterOperations.add();
        if (second != 0) {
            String request = first + "@" + second;
            var cacheValue = cache.get(request);
            if (cacheValue != null) {
                return ResponseEntity.ok().body(cacheValue);
            } else {
                String response = mathOperations.resulting(first, second);
                cache.push(request, response);
                PostAllAction post = new PostAllAction(first, second, response);
                postAllActionRep.save(post);
                return ResponseEntity.ok().body(response);
            }
        } else {
            logger.info("Request got incorrectly");
            IllegalArgumentException ex;
            if (second == 0) {
                ex = new IllegalArgumentException("Second parameter cannot be zero");
            } else {
                ex = new IllegalArgumentException();
            }
            return CustomExceptionHandler.handleBadRequest(ex);
        }
    }

    @PostMapping("/calculate/async")
    public ResponseEntity<Object> calculateAsync(@RequestParam(name = "first") int first, @RequestParam(name = "second") int second) {
        counterOperations.add();
        if (second != 0) {
            CompletableFuture.runAsync(() -> mathOperations.resulting(first, second));
            String response = mathOperations.resulting(first, second);
            PostAllAction post = new PostAllAction(first, second, response);
            postAllActionRep.save(post);
            return new ResponseEntity<>("Calculating", HttpStatus.OK);
        } else {
            logger.info("Request got incorrectly");
            IllegalArgumentException ex;
            if (second == 0) {
                ex = new IllegalArgumentException("Second parameter cannot be zero");
            } else {
                ex = new IllegalArgumentException();
            }
            return CustomExceptionHandler.handleBadRequest(ex);
        }
    }

    @GetMapping("/stat")
    public int stat() {
        return counterOperations.getCount();
    }

    @GetMapping("/calculate/bulk")
    public ResponseEntity<Object> calculate(@RequestParam(name = "pairs") List<String> pairs, @RequestParam(name = "operation") String operation) {

        counterOperations.add();
        List<String> responses = new ArrayList<>();

        pairs.forEach(pair -> {
            String[] numbers = pair.split(",");
            if (numbers.length == 2) {
                int first = Integer.parseInt(numbers[0]);
                int second = Integer.parseInt(numbers[1]);
                String request = first + "@" + second + "@" + operation;
                var cacheValue = cache.get(request);
                if (cacheValue != null) {
                    responses.add(cacheValue);
                } else {
                    String response = mathOperations.bulkResulting(first, second, operation);
                    cache.push(request, response);
                    responses.add(response);
                }

            }
        });
        return ResponseEntity.ok().body(responses);
    }

    @PostMapping("/calculate/bulk/json")
    public ResponseEntity<Object> calculateBulk(@RequestBody BulkRequest bulkRequest) {

        String operation = bulkRequest.getOperation();
        List<String> pairs = bulkRequest.getPairs();

        counterOperations.add();
        List<String> responses = new ArrayList<>();

        pairs.forEach(pair -> {
            String[] numbers = pair.split(",");
            if (numbers.length == 2) {
                int first = Integer.parseInt(numbers[0]);
                int second = Integer.parseInt(numbers[1]);
                List<Integer> numberPair = new ArrayList<>();
                numberPair.add(first);
                numberPair.add(second);
                responses.add(mathOperations.aggregatingFunc(numberPair));
                String request = first + "@" + second + "@" + operation;
                var cacheValue = cache.get(request);
                if (cacheValue != null) {
                    responses.add(cacheValue);
                } else {
                    String response = mathOperations.bulkResulting(first, second, operation);
                    cache.push(request, response);
                    responses.add(response);
                }
            }
        });

        return ResponseEntity.ok().body(responses);
    }

}