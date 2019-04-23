package com.oracle.seaas.apiclient.crm;

import com.oracle.seaas.model.Lookup;
import com.oracle.seaas.model.LookupList;
import com.oracle.seaas.util.JerseyClient;
import com.oracle.seaas.util.TokenProvider;

import javax.ws.rs.core.MultivaluedHashMap;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

public class CRMAPIRESTService {
    /**
     * Returns static lookups.
     * @param type
     * @return
     */
    List<Lookup> getLookupCollection(CRMAPIClient.lookupType type) {
        String defaultUrl = "https://eeho-dev5.fa.us2.oraclecloud.com/crmRestApi/resources/latest/fndStaticLookups?finder=LookupTypeIsEnabledFinder;BindLookupType=" +
                type.getLookupTypeCode() +"&fields=LookupType,LookupCode,Meaning,Description,EnabledFlag,StartDateActive,EndDateActive,DisplaySequence,CreatedBy,CreationDate,LastUpdateDate,LastUpdateLogin,LastUpdatedBy";

        LookupList lookupList = null;
        System.out.println("Fetching lookups from Source");

        MultivaluedMap<String, String> headers = new MultivaluedHashMap<>();
        URI url = null;
        try {
            url = new URI(defaultUrl);

            Response response = new JerseyClient(TokenProvider
                    .builder()
                    .withDefaultAuthString()
                    .build())
                    .proxyGetCalls(url, headers);

            lookupList = response.readEntity(LookupList.class);
            System.out.println("getLookupCollection for " + "type: " +type + lookupList);
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }

        return lookupList.getItems();
    }

}