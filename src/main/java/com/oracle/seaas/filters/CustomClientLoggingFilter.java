package com.oracle.seaas.filters;

import org.glassfish.jersey.message.MessageUtils;
import org.slf4j.Logger;

import javax.annotation.Priority;
import javax.ws.rs.ConstrainedTo;
import javax.ws.rs.RuntimeType;
import javax.ws.rs.client.ClientRequestContext;
import javax.ws.rs.client.ClientRequestFilter;
import javax.ws.rs.client.ClientResponseContext;
import javax.ws.rs.client.ClientResponseFilter;
import javax.ws.rs.container.PreMatching;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

/**
 * Since there is no way to extend the ClientLoggingFilter provided by jersey code, adding
 * a custom implementation class by taking jersey code for ClientLoggingFilter class.
 *
 * The intention of this code is to log the request and response content in a single line.
 */

@ConstrainedTo(RuntimeType.CLIENT)
@PreMatching
@Priority(Integer.MAX_VALUE)
@SuppressWarnings("ClassWithMultipleLoggers")
public final class CustomClientLoggingFilter extends CustomLoggingInterceptor implements ClientRequestFilter, ClientResponseFilter {

    private Logger log;

    /**
     * Create a logging filter with custom logger and custom settings of entity
     * logging.
     *
     * @param maxEntitySize maximum number of entity bytes to be logged (and buffered) - if the entity is larger,
     *                      logging filter will print (and buffer in memory) only the specified number of bytes
     *                      and print "...more..." string at the end. Negative values are interpreted as zero.
     *
     * @param skipLoggingHeaders  to skip logging of some of the headers. It is case sensitive.
     */
    public CustomClientLoggingFilter(Logger log, final int maxEntitySize, List<String> skipLoggingHeaders) {
        super(maxEntitySize,skipLoggingHeaders);
        this.log = log;
    }

    @Override
    public void filter(final ClientRequestContext context) {

        final StringBuilder b = new StringBuilder();

        printRequestLine(b, "Sending client request", context.getMethod(), context.getUri());
        printPrefixedHeaders(b, REQUEST_PREFIX, context.getStringHeaders());

        if (context.hasEntity()) {
            final OutputStream stream = new LoggingStream(b, context.getEntityStream());
            context.setEntityStream(stream);
            context.setProperty(ENTITY_LOGGER_PROPERTY, stream);
            // not calling log(b) here - it will be called by the interceptor
        } else {
            log(b);
        }
    }

    @Override
    public void filter(final ClientRequestContext requestContext, final ClientResponseContext responseContext)
            throws IOException {

        final StringBuilder b = new StringBuilder();

        printResponseLine(b, "Client response received", responseContext.getStatus());
        printPrefixedHeaders(b, RESPONSE_PREFIX, responseContext.getHeaders());

        if (responseContext.hasEntity()) {
            responseContext.setEntityStream(logInboundEntity(b, responseContext.getEntityStream(),
                    MessageUtils.getCharset(responseContext.getMediaType())));
        }
        log(b);
    }

    @Override
    void log(StringBuilder logStmt) {
        log.info(logStmt.toString());
    }
}


