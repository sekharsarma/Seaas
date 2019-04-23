package com.oracle.oal.seaas.crm.apiclient.model;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.annotation.*;
import lombok.Data;
import lombok.ToString;

@JsonIgnoreProperties(ignoreUnknown = true)
@Data
@ToString
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "items",
        "count",
        "hasMore",
        "limit",
        "offset",
        "links"
})
public class LookupList {
    @JsonProperty("items")
    private List<Lookup> items;
    @JsonProperty("count")
    private Integer count;
    @JsonProperty("hasMore")
    private Boolean hasMore;
    @JsonProperty("limit")
    private Integer limit;
    @JsonProperty("offset")
    private Integer offset;
    @JsonProperty("links")
    private List<Link> links = null;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

}
