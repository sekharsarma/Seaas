package com.oracle.oal.seaas.crm.apiclient.model;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.annotation.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "ResourceProfileId",
        "PartyId",
        "PartyName",
        "PartyNumber",
        "StartDateActive",
        "EndDateActive",
        "LastUpdateDate",
        "LastUpdatedBy",
        "CreationDate",
        "CreatedBy",
        "LastUpdateLogin",
        "ResourceType",
        "PrimaryOrganization",
        "EmailAddress",
        "FormattedAddress",
        "FormattedPhoneNumber",
        "Usage",
        "Manager",
        "Url",
        "JobMeaning",
        "PersonFirstName",
        "PersonLastName",
        "PersonLastNamePrefix",
        "PersonMiddleName",
        "PersonNameSuffix",
        "PersonPreNameAdjunct",
        "PersonPreviousLastName",
        "PersonSecondLastName",
        "ManagerPartyId",
        "RecordSet",
        "UpdateFlag",
        "DeleteFlag",
        "links"
})
@NoArgsConstructor
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
@ToString
public class Resource {

    @JsonProperty("ResourceProfileId")
    private Integer resourceProfileId;
    @JsonProperty("PartyId")
    private Integer partyId;
    @JsonProperty("PartyName")
    private String partyName;
    @JsonProperty("PartyNumber")
    private String partyNumber;
    @JsonProperty("StartDateActive")
    private String startDateActive;
    @JsonProperty("EndDateActive")
    private String endDateActive;
    @JsonProperty("LastUpdateDate")
    private String lastUpdateDate;
    @JsonProperty("LastUpdatedBy")
    private String lastUpdatedBy;
    @JsonProperty("CreationDate")
    private String creationDate;
    @JsonProperty("CreatedBy")
    private String createdBy;
    @JsonProperty("LastUpdateLogin")
    private String lastUpdateLogin;
    @JsonProperty("ResourceType")
    private String resourceType;
    @JsonProperty("PrimaryOrganization")
    private String primaryOrganization;
    @JsonProperty("EmailAddress")
    private String emailAddress;
    @JsonProperty("FormattedAddress")
    private String formattedAddress;
    @JsonProperty("FormattedPhoneNumber")
    private String formattedPhoneNumber;
    @JsonProperty("Usage")
    private String usage;
    @JsonProperty("Manager")
    private String manager;
    @JsonProperty("Url")
    private Object url;
    @JsonProperty("JobMeaning")
    private String jobMeaning;
    @JsonProperty("PersonFirstName")
    private String personFirstName;
    @JsonProperty("PersonLastName")
    private String personLastName;
    @JsonProperty("PersonLastNamePrefix")
    private Object personLastNamePrefix;
    @JsonProperty("PersonMiddleName")
    private Object personMiddleName;
    @JsonProperty("PersonNameSuffix")
    private Object personNameSuffix;
    @JsonProperty("PersonPreNameAdjunct")
    private Object personPreNameAdjunct;
    @JsonProperty("PersonPreviousLastName")
    private Object personPreviousLastName;
    @JsonProperty("PersonSecondLastName")
    private Object personSecondLastName;
    @JsonProperty("ManagerPartyId")
    private Integer managerPartyId;
    @JsonProperty("RecordSet")
    private Object recordSet;
    @JsonProperty("UpdateFlag")
    private String updateFlag;
    @JsonProperty("DeleteFlag")
    private String deleteFlag;
    @JsonProperty("links")
    private List<Link> links = null;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();
}

