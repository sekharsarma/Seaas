package com.oracle.oal.seaas.util;

import com.google.common.collect.ImmutableList;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.glassfish.jersey.client.ClientConfig;
import org.glassfish.jersey.logging.LoggingFeature;

import javax.ws.rs.client.*;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;
import java.net.URI;
import java.util.List;
import org.glassfish.jersey.jackson.JacksonFeature;

@Slf4j
public class JerseyClient {
    private Client client;

    // We need to remove the following headers from the incoming request.
    private final List<String> EXCLUDE_HEADERS = ImmutableList.of("authorization","host");

    // We should not print the following headers. It is case sensitive, give exact header name
    private final List<String> skipLoggingHeaders = ImmutableList.of("Authorization");

    private TokenProvider tokenProvider;


    public JerseyClient(TokenProvider tokenProvider) {
        this.tokenProvider = tokenProvider;

        ClientConfig config = new ClientConfig();
        this.client = ClientBuilder.newBuilder().withConfig(config).build();
        client.register(new CustomClientLoggingFilter(log, LoggingFeature.DEFAULT_MAX_ENTITY_SIZE,skipLoggingHeaders));
        client.register(new JacksonFeature());

        log.info("PartnerHttpClient instantiated");
    }

    /**
     * Delegates the incoming call to the underlying OPC Marketplace REST
     *
     * @param url URI
     * @param headers MultivaluedMap<String, String>
     * @return Response
     */
    public Response proxyGetCalls(@NonNull URI url, @NonNull MultivaluedMap<String, String> headers) {
        log.debug("MethodEntry : proxyGetCalls");
        log.info("Making get call with url - {}",url);
        WebTarget target = client.target(url);
        Invocation.Builder invocationBuilder = getBuilder(headers, target);
        Response response = invocationBuilder.accept(MediaType.APPLICATION_JSON).get();
        log.debug("MethodExit : proxyGetCalls");
        return response;
    }

    /**
     * Delegates the incoming call to the underlying OPC Marketplace REST
     *
     * @param url URI
     * @param postBody String
     * @param headers MultivaluedMap<String, String>
     * @return Response
     */
    public Response proxyPostCalls(@NonNull URI url, @NonNull String postBody, @NonNull MultivaluedMap<String, String> headers) {
        log.debug("MethodEntry : proxyPostCalls");
        log.info("Making post call with url - {}",url);
        WebTarget target = client.target(url);
        Invocation.Builder invocationBuilder = getBuilder(headers, target);
        Response response = invocationBuilder.post(Entity.entity(postBody, headers.getFirst(HttpHeaders.CONTENT_TYPE)));
        log.debug("MethodExit : proxyPostCalls");
        return response;
    }


    /**
     * Delegates the incoming call to the underlying OPC Marketplace REST
     *
     * @param url URI
     * @param postBody String
     * @param headers MultivaluedMap<String, String>
     * @return Response
     */
    public Response proxyPutCalls(@NonNull URI url, @NonNull String postBody, @NonNull MultivaluedMap<String, String> headers) {
        log.debug("MethodEntry : proxyPutCalls");
        log.info("Making put call with url - {}",url);
        WebTarget target = client.target(url);
        Invocation.Builder invocationBuilder = getBuilder(headers, target);
        Response response = invocationBuilder.put(Entity.entity(postBody, headers.getFirst(HttpHeaders.CONTENT_TYPE)));
        log.debug("MethodExit : proxyPutCalls");
        return response;
    }

    /**
     * Delegates the incoming call to the underlying OPC Marketplace REST
     *
     * @param url URI
     * @param headers MultivaluedMap<String, String>
     * @return Response
     */
    public Response proxyDeleteCalls(@NonNull URI url, @NonNull MultivaluedMap<String, String> headers) {
        log.debug("MethodEntry : proxyDeleteCalls");
        log.info("Making delete call with url - {}",url);
        WebTarget target = client.target(url);
        Invocation.Builder invocationBuilder = getBuilder(headers, target);
        Response response = invocationBuilder.delete();
        log.debug("MethodExit : proxyDeleteCalls");
        return response;
    }

    /**
     * Delegates the incoming call to the underlying OPC Marketplace REST
     *
     * @param url URI
     * @param headers MultivaluedMap<String, String>
     * @return Response
     */
    public Response proxyOptionCalls(@NonNull URI url, @NonNull MultivaluedMap<String, String> headers) {
        log.debug("MethodEntry : proxyOptionCalls");
        log.info("Making option call with url - {}",url);
        WebTarget target = client.target(url);
        Invocation.Builder invocationBuilder = getBuilder(headers, target);
        Response response = invocationBuilder.options();
        log.debug("MethodExit : proxyOptionCalls");
        return response;
    }


    private Invocation.Builder getBuilder(MultivaluedMap<String, String> headers, WebTarget target) {

        Invocation.Builder invocationBuilder = target.request(MediaType.APPLICATION_JSON);
        headers.entrySet().stream().
                filter(e-> !EXCLUDE_HEADERS.contains(e.getKey().toLowerCase())).
                forEach(e-> invocationBuilder.header(e.getKey(), headers.getFirst(e.getKey())));
        addOPCAuthorizationHeader(invocationBuilder);
        return invocationBuilder;
    }

    private void addOPCAuthorizationHeader(Invocation.Builder builder)
    {


        String token = String.format("Basic %s",
                tokenProvider.getToken());

        log.info("Adding OPC Authorization Header");
        builder.header(HttpHeaders.AUTHORIZATION,token);
    }

}
