package com.sfl.pms.services.http.impl;

import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;

/**
 * User: Ruben Dilanyan
 * Company: SFL LLC
 * Date: 1/22/16
 * Time: 5:38 PM
 */
public class HttpClientBuilderAdapterImpl implements HttpClientBuilderAdapter {

    private static final Logger LOGGER = LoggerFactory.getLogger(HttpClientBuilderAdapterImpl.class);

    /* Properties */
    @Value("#{ appProperties['http.client.connections.max']}")
    private int maximumTotalConnectionsCount;

    /* Constructors */
    public HttpClientBuilderAdapterImpl() {
        LOGGER.debug("Initializing HTTP client builder adapter");
    }

    public HttpClient build() {
        LOGGER.debug("Building HTTP client");
        final HttpClientBuilder httpClientBuilder = HttpClientBuilder.create();
        httpClientBuilder.setMaxConnTotal(maximumTotalConnectionsCount);
        return httpClientBuilder.build();
    }

    /* Properties getters and setters */
    public int getMaximumTotalConnectionsCount() {
        return maximumTotalConnectionsCount;
    }

    public void setMaximumTotalConnectionsCount(final int maximumTotalConnectionsCount) {
        this.maximumTotalConnectionsCount = maximumTotalConnectionsCount;
    }
}
