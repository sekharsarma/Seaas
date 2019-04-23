package com.oracle.oal.seaas.util;

import com.oracle.oal.seaas.crm.apiclient.CRMAPIClient;
import com.oracle.oal.seaas.crm.apiclient.CRMAPIClientImpl;

import java.util.logging.Logger;

public class Test {
    private static final Logger LOG = Logger.getLogger(Test.class.getName());

    public static void main(String args[]) throws Exception {
        CRMAPIClient client = CRMAPIClientImpl.newInstance();
        /*
        for (int i = 0; i < 20; i++) {
            System.out.println(client.getServiceRequestStatuses());
            Thread.sleep(10000);
        }*/

        LOG.info( "" + client.getServiceRequestStatuses());
        while (true) {
            Thread.sleep(1000);
            LOG.info(" "+ client.getServiceRequestStatuses());
        }


    }
}
