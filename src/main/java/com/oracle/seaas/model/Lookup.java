package com.oracle.seaas.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.ToString;


@NoArgsConstructor
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
@ToString
public class Lookup {
    @JsonProperty("LookupCode")
    private String code;

    @JsonProperty("Meaning")
    private String name;

}
