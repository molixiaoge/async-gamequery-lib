/***************************************************************************************************
 * MIT License
 *
 * Copyright (c) 2016 Rafael Luis Ibasco
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 **************************************************************************************************/

package org.ribasco.asyncgamequerylib.core;

import org.apache.commons.lang3.text.StrSubstitutor;
import org.asynchttpclient.Request;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

abstract public class AbstractWebApiRequest extends AbstractWebRequest {
    public static final Logger log = LoggerFactory.getLogger(AbstractWebApiRequest.class);
    private Map<String, String> baseUrlParams;
    private StrSubstitutor substitutor;
    private int apiVersion;
    private String apiToken;
    private String baseUrlFormat;

    public AbstractWebApiRequest(int apiVersion) {
        this.apiVersion = apiVersion;
        this.baseUrlParams = new HashMap<>();
        this.substitutor = new StrSubstitutor(this.baseUrlParams);
    }

    public int apiVersion() {
        return apiVersion;
    }

    public void apiVersion(int apiVersion) {
        this.apiVersion = apiVersion;
    }

    String apiToken() {
        return apiToken;
    }

    void apiToken(String apiToken) {
        this.apiToken = apiToken;
    }

    public void baseUrlProperty(String property, Object value) {
        baseUrlParams.put(property, String.valueOf(value));
    }

    public String baseUrlProperty(String property) {
        return baseUrlParams.get(property);
    }

    public String resolveBaseUrl() {
        return substitutor.replace(this.baseUrlFormat);
    }

    public String baseUrlFormat() {
        return baseUrlFormat;
    }

    public void baseUrlFormat(String baseUrlFormat) {
        this.baseUrlFormat = baseUrlFormat;
    }

    @Override
    public Request getMessage() {
        log.info("Using Resolved URL: {}", resolveBaseUrl());
        baseUrl(resolveBaseUrl());
        return super.getMessage();
    }
}
