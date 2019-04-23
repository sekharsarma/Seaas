package com.oracle.oal.seaas.util;

import com.oracle.oal.seaas.crm.apiclient.CRMAPIClient;
import com.oracle.oal.seaas.crm.apiclient.CRMAPIClientImpl;

public class Test {


    public static void main(String args[]) throws Exception {
        CRMAPIClient client = CRMAPIClientImpl.newInstance();
        /*
        for (int i = 0; i < 20; i++) {
            System.out.println(client.getServiceRequestStatuses());
            Thread.sleep(10000);
        }*/

        System.out.println(client.getServiceRequestStatuses());
        while (true) {
            Thread.sleep(1000);
            System.out.println(client.getServiceRequestStatuses());
        }


    }
}
