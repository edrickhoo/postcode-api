package com.example.postcode.postcode;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/postcode")
public class PostcodeController {
    Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private PostcodeService postcodeService;

    @PostMapping
    public ResponseEntity<Postcode> create(@RequestBody @Valid PostcodeDTO data) {
        try {
            Postcode newPostcode = this.postcodeService.create(data);
            if(newPostcode == null) {
                return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
            }
            return new ResponseEntity<>(newPostcode, HttpStatus.CREATED);
        } catch (Exception e) {
            logger.error("Internal Server Error occurred, message: " + e.getMessage(), e);
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping
    public ResponseEntity<List<Postcode>> getAll() {
        try {
            List<Postcode> allPostcode = this.postcodeService.getAll();

            return new ResponseEntity<>(allPostcode, HttpStatus.OK);
        } catch (Exception e) {
            logger.error("Internal Server Error occurred, message: " + e.getMessage(), e);
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/postcode/{postcode}")
    public ResponseEntity<Postcode> getByPostcode(@PathVariable Integer postcode) {
        try {
            Optional<Postcode> maybePostcode = this.postcodeService.findByPostcode(postcode);
            if(maybePostcode.isEmpty()) {
                return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
            }

            return new ResponseEntity<>(maybePostcode.get(), HttpStatus.OK);
        } catch (Exception e) {
            logger.error("Internal Server Error occurred, message: " + e.getMessage(), e);
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/suburb/{suburb}")
    public ResponseEntity<Postcode> getByPostcode(@PathVariable String suburb) {
        try {
            Optional<Postcode> maybeSuburb = this.postcodeService.findBySuburb(suburb);
            if(maybeSuburb.isEmpty()) {
                return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
            }

            return new ResponseEntity<>(maybeSuburb.get(), HttpStatus.OK);
        } catch (Exception e) {
            logger.error("Internal Server Error occurred, message: " + e.getMessage(), e);
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
