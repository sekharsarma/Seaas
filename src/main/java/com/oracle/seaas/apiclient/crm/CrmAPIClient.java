package com.oracle.seaas.apiclient.crm;

import com.oracle.seaas.model.Lookup;

import java.util.List;

public interface CrmAPIClient {

    public enum lookupType {SR_STATUS, PRODUCT_PILLARS, PLATFORMS, LANGUAGES};

    List<Lookup> getServiceRequestStatuses();

    List<Lookup> getProductPillars();

    List<Lookup> getPlatforms();

    List<Lookup> getLanguages();
}
