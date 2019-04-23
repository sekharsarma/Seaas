package com.oracle.oal.seaas.crm.apiclient.model;

import java.util.List;
import com.fasterxml.jackson.annotation.*;
import lombok.Data;
import lombok.ToString;

@JsonIgnoreProperties(ignoreUnknown = true)
@Data
@ToString
public class LookupList {

    @JsonProperty("items")
    private List<Lookup> items;

    public LookupList(){

    }
}
