package com.oracle.oal.seaas.crm.apiclient.model;

import com.fasterxml.jackson.annotation.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@NoArgsConstructor
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
@ToString
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "LookupType",
        "LookupCode",
        "Meaning",
        "Description",
        "EnabledFlag",
        "StartDateActive",
        "EndDateActive",
        "DisplaySequence",
        "CreatedBy",
        "CreationDate",
        "LastUpdateDate",
        "LastUpdateLogin",
        "LastUpdatedBy",
        "links"
})
public class Lookup {

    @JsonProperty("LookupType")
    private String lookupType;
    @JsonProperty("LookupCode")
    private String code;
    @JsonProperty("Meaning")
    private String name;
    @JsonProperty("Description")
    private Object description;
    @JsonProperty("EnabledFlag")
    private String enabledFlag;
    @JsonProperty("StartDateActive")
    private Object startDateActive;
    @JsonProperty("EndDateActive")
    private Object endDateActive;
    @JsonProperty("DisplaySequence")
    private Object displaySequence;
    @JsonProperty("CreatedBy")
    private String createdBy;
    @JsonProperty("CreationDate")
    private String creationDate;
    @JsonProperty("LastUpdateDate")
    private String lastUpdateDate;
    @JsonProperty("LastUpdateLogin")
    private String lastUpdateLogin;
    @JsonProperty("LastUpdatedBy")
    private String lastUpdatedBy;
    @JsonProperty("links")
    private List<Link> links = null;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();
}