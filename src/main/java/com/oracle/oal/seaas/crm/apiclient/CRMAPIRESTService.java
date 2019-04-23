package com.oracle.oal.seaas.crm.apiclient;

import com.oracle.oal.seaas.crm.apiclient.model.Lookup;
import com.oracle.oal.seaas.crm.apiclient.model.LookupList;
import com.oracle.oal.seaas.util.HTTPClient;
import com.oracle.oal.seaas.util.TokenProvider;
import lombok.NonNull;

import javax.ws.rs.core.MultivaluedHashMap;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;
import java.net.URI;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * API REST Service
 * Package level internal service used by Cache API
 *
 */
class CRMAPIRESTService {
    private static final Logger LOG = Logger.getLogger(CRMAPIRESTService.class.getName());

    public static final String serviceURL = "https://eeho-dev5.fa.us2.oraclecloud.com/crmRestApi/resources/latest";

    private HTTPClient httpClient;

    CRMAPIRESTService(){
        httpClient = new HTTPClient(TokenProvider
                .builder()
                .withDefaultAuthString()
                .build());
    }

    /**
     * Returns static lookups.
     * @param type lookup type
     * @return List<Lookup>
     */
    List<Lookup> getLookupCollection(@NonNull LookupType type) {
        String defaultUrl = String.format("%s/fndStaticLookups?finder=LookupTypeIsEnabledFinder;BindLookupType=%s" +
                "&fields=LookupType,LookupCode,Meaning,Description,EnabledFlag,StartDateActive,EndDateActive," +
                "DisplaySequence,CreatedBy,CreationDate,LastUpdateDate,LastUpdateLogin,LastUpdatedBy", serviceURL, type.getLookupTypeCode());

        LookupList lookupList = null;
        LOG.info("Fetching lookups of type: " + type);

        MultivaluedMap<String, String> headers = new MultivaluedHashMap<>();
        try {
            URI url = new URI(defaultUrl);
            Response response = httpClient.get(url, headers);

            lookupList = response.readEntity(LookupList.class);
            LOG.info("Completed fetching Lookups of type: " + type + ", lookups size : " + lookupList);
        } catch (Exception e) {
            LOG.log(Level.SEVERE, "Exception occurred while fetching Lookup collection", e);
        }

        return Optional.ofNullable(lookupList.getItems()).orElse(Collections.emptyList());
    }

    /**
     * Returns static lookups.
     * @param type lookup type
     * @return List<Lookup>
     */
    List<Lookup> getResourceCollection(@NonNull LookupType type) {
        String defaultUrl = String.format("%s/fndStaticLookups?finder=LookupTypeIsEnabledFinder;BindLookupType=%s" +
                "&fields=LookupType,LookupCode,Meaning,Description,EnabledFlag,StartDateActive,EndDateActive," +
                "DisplaySequence,CreatedBy,CreationDate,LastUpdateDate,LastUpdateLogin,LastUpdatedBy", serviceURL, type.getLookupTypeCode());

        LookupList lookupList = null;
        LOG.info("Fetching lookups of type: " + type);

        MultivaluedMap<String, String> headers = new MultivaluedHashMap<>();
        try {
            URI url = new URI(defaultUrl);
            Response response = httpClient.get(url, headers);

            lookupList = response.readEntity(LookupList.class);
            LOG.info("Completed fetching Lookups of type: " + type + ", lookups size : " + lookupList);
        } catch (Exception e) {
            LOG.log(Level.SEVERE, "Exception occurred while fetching Lookup collection", e);
        }

        return Optional.ofNullable(lookupList.getItems()).orElse(Collections.emptyList());
    }

}
