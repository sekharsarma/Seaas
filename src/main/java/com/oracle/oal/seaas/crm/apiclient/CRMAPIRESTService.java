package com.oracle.oal.seaas.crm.apiclient;

import com.oracle.oal.seaas.crm.apiclient.model.*;
import com.oracle.oal.seaas.util.HTTPClient;
import com.oracle.oal.seaas.util.CredentialProvider;
import lombok.NonNull;

import javax.ws.rs.core.MultivaluedHashMap;
import javax.ws.rs.core.Response;
import java.net.URI;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Facade API which has methods to invoke CRM REST Service.
 *
 *
 */
class CRMAPIRESTService {
    private static final Logger LOG = Logger.getLogger(CRMAPIRESTService.class.getName());

    // todo: externalize the config
    public static final String SERVICE_URL = "https://eeho-dev5.fa.us2.oraclecloud.com/crmRestApi/resources/latest";

    private HTTPClient httpClient;

    CRMAPIRESTService(){
        httpClient = new HTTPClient(CredentialProvider
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
        LOG.info("Fetching lookups of type: " + type);

        // todo: simplify parameter usage as part of the url
        String defaultUrl = String.format("%s/fndStaticLookups?finder=LookupTypeIsEnabledFinder;BindLookupType=%s" +
                "&fields=LookupType,LookupCode,Meaning,Description,EnabledFlag,StartDateActive,EndDateActive," +
                "DisplaySequence,CreatedBy,CreationDate,LastUpdateDate,LastUpdateLogin,LastUpdatedBy", SERVICE_URL, type.getLookupTypeCode());

        try {
            URI url = new URI(defaultUrl);
            Response response = httpClient.get(url, new MultivaluedHashMap<>());

            LookupList lookupList = response.readEntity(LookupList.class);
            LOG.info("Completed fetching Lookups of type: " + type );
            return Optional.ofNullable(lookupList.getItems()).orElse(Collections.emptyList());

        } catch (Exception e) {
            // todo: dont suppress the exception.
            LOG.log(Level.SEVERE, "Exception occurred while fetching Lookup collection", e);
        }
        return Collections.emptyList();


    }

    /**
     * Returns resources list.
     * @param emailAddress email address
     * @return List<Resource>
     */
    Resource getResource(@NonNull String emailAddress) {
        String defaultUrl = String.format("%s/resources?q=EmailAddress=%s" , SERVICE_URL, emailAddress);

        LOG.info("Fetching Resources for email: " + emailAddress);
        Resource resource = null;
        try {
            URI url = new URI(defaultUrl);
            Response response = httpClient.get(url, new MultivaluedHashMap<>());

            ResourceList resourceList = response.readEntity(ResourceList.class);

            LOG.info("Completed fetching resources for email: " + emailAddress);

            if(resourceList != null && !resourceList.getItems().isEmpty()){
                resource = resourceList.getItems().get(0);
            }
        } catch (Exception e) {
            // todo: dont suppress the exception.
            LOG.log(Level.SEVERE, "Exception occurred while fetching Lookup collection", e);
        }

        return resource;
    }

}
