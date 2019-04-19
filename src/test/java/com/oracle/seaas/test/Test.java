package com.oracle.seaas.test;

import com.oracle.seaas.apiclient.crm.CrmAPIClient;
import com.oracle.seaas.apiclient.crm.CrmAPIClientImpl;
import com.oracle.seaas.util.JerseyClient;
import com.oracle.seaas.util.TokenProvider;

import javax.ws.rs.core.MultivaluedHashMap;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;
import java.net.URI;

public class Test {


    public static void main(String args[]) throws Exception {
        CrmAPIClient client = CrmAPIClientImpl.newInstance();
        /*
        for (int i = 0; i < 20; i++) {
            System.out.println(client.getServiceRequestStatuses());
            Thread.sleep(10000);
        }*/

        System.out.println(client.getServiceRequestStatuses());
        while (true) {
            Thread.sleep(10000);
            System.out.println(client.getServiceRequestStatuses());
        }


    }
}
