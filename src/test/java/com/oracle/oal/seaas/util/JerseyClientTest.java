package com.oracle.oal.seaas.util;

import com.oracle.oal.seaas.crm.apiclient.CRMAPIRESTService;
import com.oracle.oal.seaas.crm.apiclient.model.LookupList;

import javax.ws.rs.core.MultivaluedHashMap;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class JerseyClientTest {
    private static final Logger LOG = Logger.getLogger(JerseyClientTest.class.getName());

    public static void main(String[] args) {
        CRMAPIRESTService
                restService = new CRMAPIRESTService();
        //restService.getLookupCollection

    }

    private static void getResources(){
        String defaultUrl = "https://eeho-dev5.fa.us2.oraclecloud.com/crmRestApi/resources/latest/resources?q=EmailAddress%3Dparashar.gupta%40oracle.com";

        MultivaluedMap<String, String> headers = new MultivaluedHashMap<>();
        URI url = null;
        try {
            url = new URI(defaultUrl);

            Response response = new JerseyClient(TokenProvider
                    .builder()
                    .withDefaultAuthString()
                    .build())
                    .proxyGetCalls(url, headers);

            LOG.info(response.readEntity(String.class));
        } catch (URISyntaxException e) {
            LOG.log(Level.SEVERE, "Exception occurred while fetching resources collection", e);
        }
    }

    private static void getLookups(){
        String defaultUrl = "https://eeho-dev5.fa.us2.oraclecloud.com/crmRestApi/resources/latest/fndStaticLookups?finder=LookupTypeIsEnabledFinder;BindLookupType=ORA_SVC_SR_STATUS_CD&fields=LookupType,LookupCode,Meaning,Description,EnabledFlag,StartDateActive,EndDateActive,DisplaySequence,CreatedBy,CreationDate,LastUpdateDate,LastUpdateLogin,LastUpdatedBy";

        MultivaluedMap<String, String> headers = new MultivaluedHashMap<>();
        URI url = null;
        try {
            url = new URI(defaultUrl);
            Response response = new JerseyClient(TokenProvider
                    .builder()
                    .withDefaultAuthString()
                    .build())
                    .proxyGetCalls(url, headers);
            LookupList result = response.readEntity(LookupList.class);
            LOG.info("Lookups: " + result);
        } catch (URISyntaxException e) {
            LOG.log(Level.SEVERE, "Exception occurred while fetching lookups collection", e);
        }
    }
}
