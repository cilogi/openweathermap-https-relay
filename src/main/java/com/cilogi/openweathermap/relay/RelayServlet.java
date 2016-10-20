// Copyright (c) 2016 Cilogi. All Rights Reserved.
//
// File:        RelayServlet.java  (10/20/16)
// Author:      tim
//
// This file is licensed under the
// GNU GENERAL PUBLIC LICENSE
// Version 3, 29 June 2007
// https://www.gnu.org/licenses/gpl-3.0.en.html
//


package com.cilogi.openweathermap.relay;

import com.cilogi.openweathermap.guice.annotations.UserAgent;
import com.google.appengine.api.memcache.Expiration;
import com.google.appengine.api.memcache.MemcacheService;
import com.google.appengine.api.memcache.MemcacheServiceFactory;
import com.google.appengine.api.urlfetch.*;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import javax.inject.Singleton;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URL;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

@Singleton
public class RelayServlet extends HttpServlet {
    @SuppressWarnings("unused")
    static final Logger LOG = LoggerFactory.getLogger(RelayServlet.class);

    private static final Expiration CACHE_EXPIRATION = Expiration.byDeltaSeconds(300);
    private static final String OPENWEATHERMAP_SERVER = "http://api.openweathermap.org";

    private final String userAgent;
    private final LoadingCache<String, byte[]> urlCache;

    private final MemcacheService cache;
    private final URLFetchService fetch;

    @Inject
    public RelayServlet(@UserAgent String userAgent) {
        this.userAgent = userAgent;
        cache = MemcacheServiceFactory.getMemcacheService();
        fetch = URLFetchServiceFactory.getURLFetchService();
        urlCache = CacheBuilder.newBuilder()
                .concurrencyLevel(20)
                .maximumSize(1000)
                .expireAfterWrite(300L, TimeUnit.SECONDS)
                .build(new CacheLoader<String, byte[]>() {
                    public byte[] load(String fullUrl) throws IOException {
                        try {
                            return loadUrl(fullUrl);
                        } catch (IOException e) {
                            LOG.warn("Can't load URL " + fullUrl + ": " + e.getMessage());
                            throw e;
                        }
                    }
                });
    }

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        String uri = request.getRequestURI();
        String query = request.getQueryString();
        LOG.info("uri " + uri + " query " + query);
        String full = (query == null) ? uri : uri + "?" + query;
        try {
            byte[] data = urlCache.get(full);
            if (data != null && data.length > 0) {
                response.getOutputStream().write(data);
            }
            response.setContentType("application/json");
            response.setStatus(200);
        } catch (ExecutionException e) {
           response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }

    @SuppressWarnings({"unchecked"})
    private byte[] loadUrl(String fullUrl) throws IOException {
        byte[] data = (byte[]) cache.get(fullUrl);
        if (data == null) {
            URL url = new URL(OPENWEATHERMAP_SERVER + fullUrl);
            byte[] fetched = fetch(url);
            if (fetched != null && fetched.length > 0) {
                cache.put(fullUrl, fetched, CACHE_EXPIRATION);
            }
            data = fetched;
        }
        return data;
    }

    private byte[] fetch(URL url) throws IOException {
        HTTPRequest req = new HTTPRequest(url, HTTPMethod.GET, FetchOptions.Builder.withDeadline(30));
        req.addHeader(new HTTPHeader("User-Agent", userAgent));
        HTTPResponse response = fetch.fetch(req);
        return response.getContent();
    }

}

// try this to see if things are working OK
// localhost:8080/data/2.5/weather?q=Glasgow&APPID=<token>
