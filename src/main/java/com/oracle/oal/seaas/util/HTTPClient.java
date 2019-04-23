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
public class HTTPClient {
    private Client client;

    // We should not print the following headers. It is case sensitive, give exact header name
    private final List<String> skipLoggingHeaders = ImmutableList.of("Authorization");
    private TokenProvider tokenProvider;


    public HTTPClient(TokenProvider tokenProvider) {
        this.tokenProvider = tokenProvider;

        ClientConfig config = new ClientConfig();
        this.client = ClientBuilder.newBuilder().withConfig(config).build();
        client.register(new CustomClientLoggingFilter(log, LoggingFeature.DEFAULT_MAX_ENTITY_SIZE,skipLoggingHeaders));
        client.register(new JacksonFeature());

        log.info("HttpClient instantiated");
    }

    /**
     * @param url URI
     * @param headers MultivaluedMap<String, String>
     * @return Response
     */
    public Response get(@NonNull URI url, @NonNull MultivaluedMap<String, String> headers) {
        log.debug("MethodEntry : HttpClientGet");
        log.info("Making get call with url - {}",url);
        WebTarget target = client.target(url);
        Invocation.Builder invocationBuilder = getBuilder(headers, target);
        Response response = invocationBuilder.accept(MediaType.APPLICATION_JSON).get();
        log.debug("MethodExit : HttpClientGet");
        return response;
    }

    /**
     *
     * @param url URI
     * @param postBody String
     * @param headers MultivaluedMap<String, String>
     * @return Response
     */
    public Response post(@NonNull URI url, @NonNull String postBody, @NonNull MultivaluedMap<String, String> headers) {
        log.debug("MethodEntry : HttpClientPost");
        log.info("Making post call with url - {}",url);
        WebTarget target = client.target(url);
        Invocation.Builder invocationBuilder = getBuilder(headers, target);
        Response response = invocationBuilder.post(Entity.entity(postBody, headers.getFirst(HttpHeaders.CONTENT_TYPE)));
        log.debug("MethodExit : HttpClientPost");
        return response;
    }


    /**
     *
     * @param url URI
     * @param postBody String
     * @param headers MultivaluedMap<String, String>
     * @return Response
     */
    public Response put(@NonNull URI url, @NonNull String postBody, @NonNull MultivaluedMap<String, String> headers) {
        log.debug("MethodEntry : HttpClientPut");
        log.info("Making put call with url - {}",url);
        WebTarget target = client.target(url);
        Invocation.Builder invocationBuilder = getBuilder(headers, target);
        Response response = invocationBuilder.put(Entity.entity(postBody, headers.getFirst(HttpHeaders.CONTENT_TYPE)));
        log.debug("MethodExit : HttpClientPut");
        return response;
    }

    /**
     *
     * @param url URI
     * @param headers MultivaluedMap<String, String>
     * @return Response
     */
    public Response delete(@NonNull URI url, @NonNull MultivaluedMap<String, String> headers) {
        log.debug("MethodEntry : HttpClientDelete");
        log.info("Making delete call with url - {}",url);
        WebTarget target = client.target(url);
        Invocation.Builder invocationBuilder = getBuilder(headers, target);
        Response response = invocationBuilder.delete();
        log.debug("MethodExit : HttpClientDelete");
        return response;
    }

    /**
     *
     * @param url URI
     * @param headers MultivaluedMap<String, String>
     * @return Response
     */
    public Response option(@NonNull URI url, @NonNull MultivaluedMap<String, String> headers) {
        log.debug("MethodEntry : HttpClientOption");
        log.info("Making option call with url - {}",url);
        WebTarget target = client.target(url);
        Invocation.Builder invocationBuilder = getBuilder(headers, target);
        Response response = invocationBuilder.options();
        log.debug("MethodExit : HttpClientOption");
        return response;
    }


    private Invocation.Builder getBuilder(MultivaluedMap<String, String> headers, WebTarget target) {

        Invocation.Builder invocationBuilder = target.request(MediaType.APPLICATION_JSON);
        headers.entrySet().stream().
                forEach(e-> invocationBuilder.header(e.getKey(), headers.getFirst(e.getKey())));
        addAuthorizationHeader(invocationBuilder);
        return invocationBuilder;
    }

    private void addAuthorizationHeader(Invocation.Builder builder)
    {
        String token = String.format("Basic %s",
                tokenProvider.getToken());
        log.info("Adding Authorization Header");
        builder.header(HttpHeaders.AUTHORIZATION,token);
    }

}
