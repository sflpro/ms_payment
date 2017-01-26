package com.sfl.pms.services.http.impl;

import org.apache.http.client.HttpClient;

/**
 * User: Ruben Dilanyan
 * Company: SFL LLC
 * Date: 1/22/16
 * Time: 5:55 PM
 */
public interface HttpClientBuilderAdapter {

    /**
     * Build and returns HTTP client
     *
     * @return httpClient
     */
    HttpClient build();
}
