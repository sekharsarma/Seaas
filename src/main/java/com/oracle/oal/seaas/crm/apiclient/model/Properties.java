package com.oracle.oal.seaas.crm.apiclient.model;

import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.annotation.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "changeIndicator"
})
@NoArgsConstructor
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
@ToString
public class Properties {

    @JsonProperty("changeIndicator")
    private String changeIndicator;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

}

