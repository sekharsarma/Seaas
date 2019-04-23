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

import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class CRMAPIClientImpl implements CRMAPIClient {

    private static CRMAPIClientImpl _apiClient;

    private  CacheLoader<String, List<Lookup>> lookupCacheLoader;

    LoadingCache<LookupType, List<Lookup>> lookupCache;
    CRMAPIRESTService restService;

    private CRMAPIClientImpl()
    {
       lookupCache = buildCache(this::getLookups);
       restService = new CRMAPIRESTService();
    }

    public static CRMAPIClientImpl getInstance() {
       return SingletonHelper.INSTANCE;
    }

    private static class SingletonHelper{
        private static final CRMAPIClientImpl INSTANCE = new CRMAPIClientImpl();
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

    private List<Lookup> getLookups(LookupType lookupType){
       return restService.getLookupCollection(lookupType);
    }

    private <K, V> LoadingCache<K, V> buildCache(Function<K, V> loadFunction) {
        ListeningExecutorService pool = MoreExecutors.listeningDecorator(Executors.newFixedThreadPool(10));

        final CacheLoader<K, V> loader =
                new CacheLoader<K, V>() {
                    @Override
                    public V load(K key) throws Exception {
                        return loadFunction.apply(key);
                    }

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

        return CacheBuilder.newBuilder()
                .maximumSize(100)
                .refreshAfterWrite(30, TimeUnit.SECONDS)
                .build(loader);
    }

    private <K, V> V getFromCache(LoadingCache<K, V> cache, K cacheKey) {
        try {
            return cache.getUnchecked(cacheKey);
        } catch (UncheckedExecutionException e) {
            // Unpackaging any ClassicMarketplaceApiClientException and throwing it.
            //Throwables.propagateIfPossible(e.getCause(), ClassicMarketplaceApiClientException.class);
            throw e;
        }
    }
}
