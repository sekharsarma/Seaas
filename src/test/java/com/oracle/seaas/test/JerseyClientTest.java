package com.oracle.seaas.test;

import com.oracle.seaas.apiclient.crm.CRMAPIRESTService;
import com.oracle.seaas.model.LookupList;
import com.oracle.seaas.util.JerseyClient;
import com.oracle.seaas.util.TokenProvider;

import javax.ws.rs.core.MultivaluedHashMap;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;
import java.net.URI;
import java.net.URISyntaxException;

public class JerseyClientTest {


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

            System.out.println(response.readEntity(String.class));
        } catch (URISyntaxException e) {
            e.printStackTrace();
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
            System.out.println("result --->"+ result);

            //System.out.println(response.readEntity(String.class));
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
    }


}
