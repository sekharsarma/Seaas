package com.oracle.oal.seaas.crm.apiclient;

import com.google.common.base.Function;
import com.google.common.util.concurrent.ListenableFuture;
import com.google.common.util.concurrent.ListeningExecutorService;
import com.google.common.util.concurrent.MoreExecutors;
import com.google.common.util.concurrent.UncheckedExecutionException;
import com.oracle.oal.seaas.crm.apiclient.model.Lookup;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.oracle.oal.seaas.crm.apiclient.model.LookupType;
import com.oracle.oal.seaas.crm.apiclient.model.Resource;
import lombok.NonNull;

import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

// todo: provide log statements.
public class CRMAPIClientImpl implements CRMAPIClient {

    LoadingCache<LookupType, List<Lookup>> lookupCache;
    LoadingCache<String, Resource> resourceCache;

    CRMAPIRESTService restService;

    private CRMAPIClientImpl()
    {
       lookupCache = buildCache(this::getLookupsFromService);
       resourceCache = buildCache(this::getResourcesFromService);
       restService = new CRMAPIRESTService();
    }

    public static CRMAPIClientImpl getInstance() {
       return CRMAPIClientInstance.INSTANCE;
    }

    public List<Lookup> getServiceRequestStatuses() {
       return getFromCache(lookupCache, LookupType.SR_STATUS);
    }

    public List<Lookup> getProductPillars() {
        return getFromCache(lookupCache, LookupType.PRODUCT_PILLARS);
    }

    public List<Lookup> getPlatforms() {
        return getFromCache(lookupCache, LookupType.PLATFORMS);
    }

    public List<Lookup> getLanguages() {
        return getFromCache(lookupCache, LookupType.LANGUAGES);
    }

    public Resource getResource(@NonNull final String emailAddress) {
        return getFromCache(resourceCache, emailAddress);
    }

    private List<Lookup> getLookupsFromService(LookupType lookupType){
       return restService.getLookupCollection(lookupType);
    }

    private Resource getResourcesFromService(String emailAddress){
        return restService.getResource(emailAddress);
    }

    private <K, V> LoadingCache<K, V> buildCache(Function<K, V> loadFunction) {
        // todo: check the number of defualt threads and access scope of the pool variable
        ListeningExecutorService pool = MoreExecutors.listeningDecorator(Executors.newFixedThreadPool(10));

        final CacheLoader<K, V> loader =
                new CacheLoader<K, V>() {
                    @Override
                    public V load(K key) throws Exception {
                        return loadFunction.apply(key);
                    }

                    // todo: test the async method with multiple user threads
                    @Override
                    public ListenableFuture<V> reload(K key, V oldValue){
                        ListenableFuture<V> listenableFuture = pool.submit(new Callable<V>() {
                            @Override
                            public V call() throws Exception {
                                return loadFunction.apply(key);
                            }
                        });
                        return listenableFuture;
                    }
                };

        //todo: externalize the configurations.
        return CacheBuilder.newBuilder()
                .maximumSize(100)
                .refreshAfterWrite(30, TimeUnit.SECONDS)
                .build(loader);
    }

    private <K, V> V getFromCache(LoadingCache<K, V> cache, K cacheKey) {
        try {
            return cache.getUnchecked(cacheKey);
        } catch (UncheckedExecutionException e) {
            //
            throw e;
        }
    }

    // preferred way to create a singleton instance for lazy fetch.
    private static class CRMAPIClientInstance{
        private static final CRMAPIClientImpl INSTANCE = new CRMAPIClientImpl();
    }

}
