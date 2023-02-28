package com.example.postcode.postcode;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
@AllArgsConstructor
public class PostcodeService {
    
    @Autowired
    private PostCodeRepository postcodeRepository;

    public Postcode create(PostcodeDTO data) {
        if(this.findByPostcode(data.getPostcode()).isPresent() || this.findBySuburb(data.getSuburb()).isPresent()) {
            return null;
        }

        Postcode newPostcode = new Postcode(data.getSuburb(), data.getPostcode());
        this.postcodeRepository.save(newPostcode);
        return newPostcode;
    }

    public List<Postcode> getAll() {
        return this.postcodeRepository.findAll();
    }

    public Optional<Postcode> findByPostcode(Integer postcode) {
        Optional<Postcode> maybePostcode = this.postcodeRepository.findByPostcode(postcode);
        return maybePostcode;
    }

    public Optional<Postcode> findBySuburb(String suburb) {
        Optional<Postcode> maybePostcode = this.postcodeRepository.findBySuburb(suburb);
        return maybePostcode;
    }
}
