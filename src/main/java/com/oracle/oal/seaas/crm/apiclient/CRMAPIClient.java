package com.oracle.oal.seaas.crm.apiclient;

import com.oracle.oal.seaas.crm.apiclient.model.Lookup;
import com.oracle.oal.seaas.crm.apiclient.model.Resource;

import java.util.List;

// todo: Add javadoc
public interface CRMAPIClient {

    /**
     * Returns service request statuses.
     * @return
     */
    List<Lookup> getServiceRequestStatuses();

    /**
     * Returns product pillars
     * @return
     */
    List<Lookup> getProductPillars();

    /**
     * Returns platforms
     * @return
     */
    List<Lookup> getPlatforms();

    /**
     * Returns languages
     * @return
     */
    List<Lookup> getLanguages();

    /**
     * Returns resources for given email address
     * @param emailAddress
     * @return
     */
    List<Resource> getResources(String emailAddress);
}
