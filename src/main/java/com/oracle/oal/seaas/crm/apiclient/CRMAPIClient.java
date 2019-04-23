package com.oracle.oal.seaas.crm.apiclient;

import com.oracle.oal.seaas.crm.apiclient.model.Lookup;

import java.util.List;

public interface CRMAPIClient {

    // todo: put the real lookup type codes.
    public enum lookupType
    {
        SR_STATUS("ORA_SVC_SR_STATUS_CD"),
        PRODUCT_PILLARS("ORA_SVC_SR_STATUS_CD"),
        PLATFORMS("ORA_SVC_SR_STATUS_CD"),
        LANGUAGES("ORA_SVC_SR_STATUS_CD");

        private final String lookupTypeCode;

        lookupType(String lookupTypeCode) {
            this.lookupTypeCode = lookupTypeCode;
        }

        public String getLookupTypeCode(){
            return this.lookupTypeCode;
        }
    };

    List<Lookup> getServiceRequestStatuses();

    List<Lookup> getProductPillars();

    List<Lookup> getPlatforms();

    List<Lookup> getLanguages();
}
