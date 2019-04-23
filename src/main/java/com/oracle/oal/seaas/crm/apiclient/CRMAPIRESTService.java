package com.oracle.oal.seaas.crm.apiclient;

import com.oracle.oal.seaas.crm.apiclient.model.Lookup;
import com.oracle.oal.seaas.crm.apiclient.model.LookupList;
import com.oracle.oal.seaas.util.HTTPClient;
import com.oracle.oal.seaas.util.TokenProvider;

import javax.ws.rs.core.MultivaluedHashMap;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;
import java.net.URI;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class CRMAPIRESTService {
    private static final Logger LOG = Logger.getLogger(CRMAPIRESTService.class.getName());

    /**
     * Returns static lookups.
     * @param type
     * @return
     */
    List<Lookup> getLookupCollection(LookupType type) {
        String defaultUrl = "https://eeho-dev5.fa.us2.oraclecloud.com/crmRestApi/resources/latest/fndStaticLookups?finder=LookupTypeIsEnabledFinder;BindLookupType=" +
                type.getLookupTypeCode() +"&fields=LookupType,LookupCode,Meaning,Description,EnabledFlag,StartDateActive,EndDateActive,DisplaySequence,CreatedBy,CreationDate,LastUpdateDate,LastUpdateLogin,LastUpdatedBy";

        LookupList lookupList = null;
        LOG.info("Fetching lookups of type: " + type);

        MultivaluedMap<String, String> headers = new MultivaluedHashMap<>();
        URI url = null;
        try {
            url = new URI(defaultUrl);

            Response response = new HTTPClient(TokenProvider
                    .builder()
                    .withDefaultAuthString()
                    .build())
                    .get(url, headers);

            lookupList = response.readEntity(LookupList.class);
            LOG.info("Completed fetching Lookups of type: " + type + ", lookups size : " + lookupList.getCount());
        } catch (Exception e) {
            LOG.log(Level.SEVERE, "Exception occurred while fetching Lookup collection", e);
        }

        return lookupList.getItems();
    }
}
