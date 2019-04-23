package com.oracle.oal.seaas.crm.apiclient.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import java.util.List;

@NoArgsConstructor
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
@ToString

public class ResourcesList {

    @JsonProperty("items")
    private List<Resource> items = null;
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

}
