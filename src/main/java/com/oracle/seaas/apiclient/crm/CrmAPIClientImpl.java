package com.oracle.seaas.apiclient.crm;

import com.oracle.seaas.model.Lookup;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class CrmAPIClientImpl implements CrmAPIClient {

   private static CrmAPIClientImpl _apiClient = null;

   private  CacheLoader<String, List<Lookup>> lookupCacheLoader;

   LoadingCache<lookupType, List<Lookup>> lookupCache = null;


   private CrmAPIClientImpl() {
        initializeLookupCache();
   }

   public static synchronized CrmAPIClientImpl newInstance() {
        if(_apiClient == null) {
            _apiClient = new CrmAPIClientImpl();
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
        lookupCache = CacheBuilder.newBuilder()
                .maximumSize(100)
                .refreshAfterWrite(30, TimeUnit.SECONDS)
                .build(
                        new CacheLoader<lookupType, List<Lookup>>() {
                            @Override
                            public List<Lookup> load(lookupType key) throws Exception {
                                return getLookupCollection(key);
                            }
                        }
                );

    }

    /**
     * This method will be replaced by actual calls to CRM 
     * @param type
     * @return
     */
    private List<Lookup> getLookupCollection(lookupType type) {
       List<Lookup> lookups = new ArrayList<Lookup>();
       System.out.println("Fetching lookups from Source");
        switch (type) {
            case SR_STATUS:
                lookups.add(new Lookup("OPEN", "Open"));
                lookups.add(new Lookup("PENDING", "Pending"));
                lookups.add(new Lookup("CLOSED", "Closed"));
                break;

            case PRODUCT_PILLARS:
                lookups.add(new Lookup("IaaS", "IaaS - Infrastructure"));
                lookups.add(new Lookup("HCM", "SaaS - HCM"));
                lookups.add(new Lookup("ERP", "SaaS - ERP"));
                break;

            case LANGUAGES:
                lookups.add(new Lookup("EN", "English"));
                lookups.add(new Lookup("FR", "French"));
                lookups.add(new Lookup("DE", "German"));
                break;

            case PLATFORMS:
                lookups.add(new Lookup("Database", "Database"));
                lookups.add(new Lookup("OIC", "Integration Cloud"));
                break;

        }
        return lookups;
    }

}
