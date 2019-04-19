package com.oracle.seaas.filters;

import org.apache.commons.collections.CollectionUtils;
import org.glassfish.jersey.message.MessageUtils;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.ext.WriterInterceptor;
import javax.ws.rs.ext.WriterInterceptorContext;
import java.io.*;
import java.net.URI;
import java.nio.charset.Charset;
import java.util.*;


/**
 * Since there is no way to extend the LoggingInterceptor provided by jersey code, adding
 * a custom implementation class by taking jersey code for LoggingFilter class.
 *
 * The intention of this code is to log the request and response content in a single line.
 */
abstract class CustomLoggingInterceptor implements WriterInterceptor {

    /**
     * Prefix will be printed before requests
     */
    static final String REQUEST_PREFIX = "> ";
    /**
     * Prefix will be printed before response
     */
    static final String RESPONSE_PREFIX = "< ";

    // Since we want the entire request/response logging on the same line, using space as line separator
    static final String LINE_SEPARATOR = "::";

    // To skip logging of some of the headers. It is case sensitive.
    private List<String> skipLoggingHeaders = null;

    /**
     * The entity stream property
     */
    static final String ENTITY_LOGGER_PROPERTY = CustomLoggingInterceptor.class.getName() + ".entityLogger";

    private static final String NOTIFICATION_PREFIX = "* ";

    private static final Comparator<Map.Entry<String, List<String>>> COMPARATOR =
            new Comparator<Map.Entry<String, List<String>>>() {

                @Override
                public int compare(final Map.Entry<String, List<String>> o1, final Map.Entry<String, List<String>> o2) {
                    return o1.getKey().compareToIgnoreCase(o2.getKey());
                }
            };


    final int maxEntitySize;

    /**
     * Creates a logging filter with custom logger and entity logging turned on, but potentially limiting the size
     * of entity to be buffered and logged.
     *
     * @param maxEntitySize maximum number of entity bytes to be logged (and buffered) - if the entity is larger,
     *                      logging filter will print (and buffer in memory) only the specified number of bytes
     *                      and print "...more..." string at the end. Negative values are interpreted as zero.
     *
     * @param skipLoggingHeaders  to skip logging of some of the headers. It is case sensitive.
     */
    CustomLoggingInterceptor(final int maxEntitySize, List<String> skipLoggingHeaders) {
        this.maxEntitySize = Math.max(0, maxEntitySize);
        if(CollectionUtils.isNotEmpty(skipLoggingHeaders))
            this.skipLoggingHeaders = skipLoggingHeaders;
        else
            this.skipLoggingHeaders = new ArrayList<String>();
    }




    void printRequestLine(final StringBuilder b, final String note, final String method, final URI uri) {
        b.append(NOTIFICATION_PREFIX)
                .append(note)
                .append(LINE_SEPARATOR);
        b.append(REQUEST_PREFIX).append(method).append(" ")
                .append(uri.toASCIIString()).append(LINE_SEPARATOR);
    }

    void printResponseLine(final StringBuilder b, final String note, final int status) {
        b.append(NOTIFICATION_PREFIX)
                .append(note);
        b.append(RESPONSE_PREFIX)
                .append(Integer.toString(status))
                .append(LINE_SEPARATOR);
    }

    void printPrefixedHeaders(final StringBuilder b,
                              final String prefix,
                              final MultivaluedMap<String, String> headers) {

        /*if (!headers.isEmpty())
            b.append(prefix).append("Headers ");*/
        for (final Map.Entry<String, List<String>> headerEntry : getSortedHeaders(headers.entrySet())) {
            final List<?> val = headerEntry.getValue();
            final String header = headerEntry.getKey();



            if(skipLoggingHeaders.contains(header)){
                log( new StringBuilder("Header '"+header+"' is skipped from printing, as it is in skipLoggingHeaders"));
                continue;
            }

            if (val.size() == 1) {
                b.append(prefix).append(header).append(": ").append(val.get(0)).append(LINE_SEPARATOR);
            } else {
                final StringBuilder sb = new StringBuilder();
                boolean add = false;
                for (final Object s : val) {
                    if (add) {
                        sb.append(',');
                    }
                    add = true;
                    sb.append(s);
                }
                b.append(prefix).append(header).append(": ").append(sb.toString()).append(LINE_SEPARATOR);
            }
        }
    }

    Set<Map.Entry<String, List<String>>> getSortedHeaders(final Set<Map.Entry<String, List<String>>> headers) {
        final TreeSet<Map.Entry<String, List<String>>> sortedHeaders = new TreeSet<Map.Entry<String, List<String>>>(COMPARATOR);
        sortedHeaders.addAll(headers);
        return sortedHeaders;
    }

    InputStream logInboundEntity(final StringBuilder b, InputStream stream, final Charset charset) throws IOException {
        if (!stream.markSupported()) {
            stream = new BufferedInputStream(stream);
        }
        stream.mark(maxEntitySize + 1);
        final byte[] entity = new byte[maxEntitySize + 1];
        final int entitySize = stream.read(entity);
        b.append(new String(entity, 0, Math.min(entitySize, maxEntitySize), charset));
        if (entitySize > maxEntitySize) {
            b.append("...more...");
        }
        b.append(LINE_SEPARATOR);
        stream.reset();
        return stream;
    }

    @Override
    public void aroundWriteTo(final WriterInterceptorContext writerInterceptorContext)
            throws IOException, WebApplicationException {
        LoggingStream stream = (LoggingStream) writerInterceptorContext.getProperty(ENTITY_LOGGER_PROPERTY);
        writerInterceptorContext.proceed();

        if (stream != null) {
            log(stream.getStringBuilder(MessageUtils.getCharset(writerInterceptorContext.getMediaType())));
        }

    }

    abstract void log(StringBuilder logStmt);

    /**
     * Helper class used to log an entity to the output stream up to the specified maximum number of bytes.
     */
    class LoggingStream extends FilterOutputStream {

        private final StringBuilder b;
        private final ByteArrayOutputStream baos = new ByteArrayOutputStream();

        /**
         * Creates {@code LoggingStream} with the entity and the underlying output stream as parameters.
         *
         * @param b     contains the entity to log.
         * @param inner the underlying output stream.
         */
        LoggingStream(final StringBuilder b, final OutputStream inner) {
            super(inner);

            this.b = b;
        }

        StringBuilder getStringBuilder(final Charset charset) {
            // write entity to the builder
            final byte[] entity = baos.toByteArray();

            b.append(new String(entity, 0, Math.min(entity.length, maxEntitySize), charset));
            if (entity.length > maxEntitySize) {
                b.append("...more...");
            }
            b.append(LINE_SEPARATOR);

            return b;
        }

        @Override
        public void write(final int i) throws IOException {
            if (baos.size() <= maxEntitySize) {
                baos.write(i);
            }
            out.write(i);
        }
    }

}
