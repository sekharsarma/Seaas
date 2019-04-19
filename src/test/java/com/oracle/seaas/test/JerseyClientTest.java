package com.oracle.seaas.test;

import com.oracle.seaas.util.JerseyClient;
import com.oracle.seaas.util.TokenProvider;

import javax.ws.rs.core.MultivaluedHashMap;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;
import java.net.URI;
import java.net.URISyntaxException;

public class JerseyClientTest {

    private static String defaultUrl = "https://eeho-dev5.fa.us2.oraclecloud.com/crmRestApi/resources/latest/resources?q=EmailAddress%3Dparashar.gupta%40oracle.com";

    public static void main(String[] args) {
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
}
