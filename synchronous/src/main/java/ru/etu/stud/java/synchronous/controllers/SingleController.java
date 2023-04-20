package ru.etu.stud.java.synchronous.controllers;

import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import ru.etu.stud.java.synchronous.domain.DatabaseEntity;
import ru.etu.stud.java.synchronous.services.SingleService;

@Controller
public class SingleController {
    private final SingleService singleService;
    public  SingleController(SingleService singleService) {
        this.singleService = singleService;
    }
    @GetMapping(value = "/{id}", produces = "application/json")
    public ResponseEntity<DatabaseEntity> getData(@PathVariable int id) {
        return new ResponseEntity<>(singleService.getData(id), HttpStatusCode.valueOf(200));
    }
}
