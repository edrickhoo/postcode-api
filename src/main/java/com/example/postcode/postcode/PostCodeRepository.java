package com.example.postcode.postcode;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface PostCodeRepository extends JpaRepository<Postcode, Long> {
    Optional<Postcode> findByPostcode(Integer postcode);

    Optional<Postcode> findBySuburb(String suburb);
}
