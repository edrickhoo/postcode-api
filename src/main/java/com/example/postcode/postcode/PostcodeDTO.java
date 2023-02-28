package com.example.postcode.postcode;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class PostcodeDTO {
    @NotNull
    private Integer postcode;

    @NotBlank
    private String suburb;

    public PostcodeDTO(Integer postcode, String suburb) {
        this.postcode = postcode;
        this.suburb = suburb;
    }

    public Integer getPostcode() {
        return postcode;
    }

    public void setPostcode(Integer postcode) {
        this.postcode = postcode;
    }

    public String getSuburb() {
        return suburb;
    }

    public void setSuburb(String suburb) {
        this.suburb = suburb;
    }
}
