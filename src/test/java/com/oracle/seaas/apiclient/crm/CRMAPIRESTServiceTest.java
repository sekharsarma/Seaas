package com.oracle.seaas.apiclient.crm;

import com.oracle.seaas.model.Lookup;
import org.junit.jupiter.api.Test;
import java.util.List;

public class CRMAPIRESTServiceTest {

    private CRMAPIRESTService restService = null;

    public CRMAPIRESTServiceTest(){
        restService = new CRMAPIRESTService();
    }

    @Test
    public void getLookupCollection(){
        List<Lookup> SRStatusLookupList = restService.getLookupCollection(CRMAPIClient.lookupType.SR_STATUS);
    }
}
