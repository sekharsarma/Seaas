package com.oracle.seaas.apiclient.crm;

import com.google.common.util.concurrent.ListenableFuture;
import com.google.common.util.concurrent.ListeningExecutorService;
import com.google.common.util.concurrent.MoreExecutors;
import com.oracle.seaas.model.Lookup;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.oracle.seaas.model.LookupList;
import com.oracle.seaas.util.JerseyClient;
import com.oracle.seaas.util.TokenProvider;

import javax.ws.rs.core.MultivaluedHashMap;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class CRMAPIClientImpl implements CRMAPIClient {

   private static CRMAPIClientImpl _apiClient = null;

   private  CacheLoader<String, List<Lookup>> lookupCacheLoader;

   LoadingCache<lookupType, List<Lookup>> lookupCache = null;


   private CRMAPIClientImpl() {
        initializeLookupCache();
   }

   public static synchronized CRMAPIClientImpl newInstance() {
        if(_apiClient == null) {
            _apiClient = new CRMAPIClientImpl();
        }
        return _apiClient;
   }


    public List<Lookup> getServiceRequestStatuses() {
       try {
           return lookupCache.get(lookupType.SR_STATUS);
       } catch (Exception e) {
           return null;
       }
    }

    public List<Lookup> getProductPillars() {
        try {
            return lookupCache.get(lookupType.PRODUCT_PILLARS);
        } catch (Exception e) {
            return null;
        }
    }

    public List<Lookup> getPlatforms() {
        try {
            return lookupCache.get(lookupType.PLATFORMS);
        } catch (Exception e) {
            return null;
        }
    }

    public List<Lookup> getLanguages() {
        try {
            return lookupCache.get(lookupType.LANGUAGES);
        } catch (Exception e) {
            return null;
        }
    }

    private void initializeLookupCache() {
        ListeningExecutorService pool = MoreExecutors.listeningDecorator(Executors.newFixedThreadPool(10));

        lookupCache = CacheBuilder.newBuilder()
                .maximumSize(100)
                .refreshAfterWrite(30, TimeUnit.SECONDS)
                .build(
                        new CacheLoader<lookupType, List<Lookup>>() {
                            @Override
                            public List<Lookup> load(lookupType key) throws Exception {
                                return getLookupCollection(key);
                            }

                            @Override
                            public ListenableFuture<List<Lookup>> reload(lookupType key, List<Lookup> oldValue){
                                ListenableFuture<List<Lookup>> listenableFuture = pool.submit(new Callable<List<Lookup>>() {
                                    @Override
                                    public List<Lookup> call() throws Exception {
                                        return getLookupCollection(key);
                                    }
                                });
                                return listenableFuture;
                            }
                        }
                );

    }

    /**
     * This method will be replaced by actual calls to CRM 
     * @param type
     * @return
     */
//    private List<Lookup> getLookupCollection(lookupType type) {
//       List<Lookup> lookups = new ArrayList<Lookup>();
//       System.out.println("Fetching lookups from Source");
//        switch (type) {
//            case SR_STATUS:
//                lookups.add(new Lookup("OPEN", "Open"));
//                lookups.add(new Lookup("PENDING", "Pending"));
//                lookups.add(new Lookup("CLOSED", "Closed"));
//                break;
//
//            case PRODUCT_PILLARS:
//                lookups.add(new Lookup("IaaS", "IaaS - Infrastructure"));
//                lookups.add(new Lookup("HCM", "SaaS - HCM"));
//                lookups.add(new Lookup("ERP", "SaaS - ERP"));
//                break;
//
//            case LANGUAGES:
//                lookups.add(new Lookup("EN", "English"));
//                lookups.add(new Lookup("FR", "French"));
//                lookups.add(new Lookup("DE", "German"));
//                break;
//
//            case PLATFORMS:
//                lookups.add(new Lookup("Database", "Database"));
//                lookups.add(new Lookup("OIC", "Integration Cloud"));
//                break;
//
//        }
//        return lookups;
//    }


    /**
     * This method will be replaced by actual calls to CRM
     * @param type
     * @return
     */
    private List<Lookup> getLookupCollection(lookupType type) {
        String defaultUrl = "https://eeho-dev5.fa.us2.oraclecloud.com/crmRestApi/resources/latest/fndStaticLookups?finder=LookupTypeIsEnabledFinder;BindLookupType=" +
        type.getLookupTypeCode() +"&fields=LookupType,LookupCode,Meaning,Description,EnabledFlag,StartDateActive,EndDateActive,DisplaySequence,CreatedBy,CreationDate,LastUpdateDate,LastUpdateLogin,LastUpdatedBy";

        LookupList lookupList = null;
        List<Lookup> lookups = new ArrayList<Lookup>();
        System.out.println("Fetching lookups from Source");

        MultivaluedMap<String, String> headers = new MultivaluedHashMap<>();
        URI url = null;
        try {
            url = new URI(defaultUrl);

            Response response = new JerseyClient(TokenProvider
                    .builder()
                    .withDefaultAuthString()
                    .build())
                    .proxyGetCalls(url, headers);

            lookupList = response.readEntity(LookupList.class);
            System.out.println("getLookupCollection for " + "type: " +type + lookupList);
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }

       return lookupList.getItems();
    }

}
