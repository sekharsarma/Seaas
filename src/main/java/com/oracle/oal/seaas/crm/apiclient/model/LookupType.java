package com.oracle.oal.seaas.crm.apiclient.model;

public enum LookupType {
    // todo: get valid lookup type codes from requirement
    SR_STATUS("ORA_SVC_SR_STATUS_CD"),
    PRODUCT_PILLARS("ORA_SVC_SR_STATUS_CD"),
    PLATFORMS("ORA_SVC_SR_STATUS_CD"),
    LANGUAGES("ORA_SVC_SR_STATUS_CD");

    private final String lookupTypeCode;

    LookupType(String lookupTypeCode) {
        this.lookupTypeCode = lookupTypeCode;
    }

    public String getLookupTypeCode(){
        return this.lookupTypeCode;
    }
}
