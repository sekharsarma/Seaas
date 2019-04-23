package com.oracle.oal.seaas.crm.apiclient;

import com.oracle.oal.seaas.crm.apiclient.model.Lookup;

import java.util.List;

public interface CRMAPIClient {

    List<Lookup> getServiceRequestStatuses();

    List<Lookup> getProductPillars();

    List<Lookup> getPlatforms();

    List<Lookup> getLanguages();
}
