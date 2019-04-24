package com.oracle.oal.seaas.crm.apiclient;

import com.oracle.oal.seaas.crm.apiclient.model.Lookup;
import com.oracle.oal.seaas.crm.apiclient.model.LookupType;
import com.oracle.oal.seaas.crm.apiclient.model.Resource;
import org.junit.jupiter.api.Test;
import java.util.List;

public class CRMAPIRESTServiceTest {

    private CRMAPIRESTService restService = null;

    public CRMAPIRESTServiceTest(){
        restService = new CRMAPIRESTService();
    }

    @Test
    public void getLookupCollection(){
        // todo: assertions pending.
        List<Lookup> SRStatusLookupList = restService.getLookupCollection(LookupType.SR_STATUS);
    }

    @Test
    public void getResourceCollection(){
        // todo: assertions pending.
        Resource resource = restService.getResource("parashar.gupta%40oracle.com");
    }


}
