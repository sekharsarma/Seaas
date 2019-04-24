package com.oracle.oal.seaas.crm.apiclient;

import org.junit.jupiter.api.Test;

public class CRMAPIClientImplTest {

    private CRMAPIClientImpl crmAPIClient = null;

    public CRMAPIClientImplTest(){
        crmAPIClient = new CRMAPIClientImpl();
    }

    @Test
    public void getServiceRequestStatuses(){
        crmAPIClient.getServiceRequestStatuses();
    }

    @Test
    public void getResources(){
        crmAPIClient.getResource("parashar.gupta%40oracle.com");
    }
}
