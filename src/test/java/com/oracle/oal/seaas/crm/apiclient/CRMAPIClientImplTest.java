package com.oracle.oal.seaas.crm.apiclient;

import org.junit.jupiter.api.Test;

public class CRMAPIClientImplTest {

    private CRMAPIClientImpl crmAPIClient = null;

    public CRMAPIClientImplTest(){
        crmAPIClient = new CRMAPIClientImpl();
    }

    @Test
    public void getServiceRequestStatuses(){
        // todo: assertions pending.
        crmAPIClient.getServiceRequestStatuses();
    }

    @Test
    public void getResources(){
        // todo: assertions pending.
        crmAPIClient.getResource("parashar.gupta%40oracle.com");
    }
}
