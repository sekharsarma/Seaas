package com.oracle.oal.seaas.crm.apiclient;

import com.oracle.oal.seaas.crm.apiclient.model.Lookup;
import com.oracle.oal.seaas.crm.apiclient.model.Resource;
import lombok.NonNull;

import java.util.List;

// todo: Add javadoc
public interface CRMAPIClient {

    /**
     * Returns service request statuses.
     * @return List<Lookup>
     */
    List<Lookup> getServiceRequestStatuses();

    /**
     * Returns product pillars
     * @return List<Lookup>
     */
    List<Lookup> getProductPillars();

    /**
     * Returns platforms
     * @return List<Lookup>
     */
    List<Lookup> getPlatforms();

    /**
     * Returns languages
     * @return List<Lookup>
     */
    List<Lookup> getLanguages();

    /**
     * Returns resource for given email address
     * @param emailAddress
     * @return Resource
     */
    Resource getResource(@NonNull String emailAddress);
}
