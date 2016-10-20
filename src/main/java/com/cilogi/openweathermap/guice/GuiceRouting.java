// Copyright (c) 2016 Cilogi All Rights Reserved.
//
// File:        GuiceRouting.java  (05-Oct-2016)
// Author:      tim

//
// This file is licensed under the
// GNU GENERAL PUBLIC LICENSE
// Version 3, 29 June 2007
// https://www.gnu.org/licenses/gpl-3.0.en.html.
//


package com.cilogi.openweathermap.guice;

import com.cilogi.openweathermap.relay.RelayServlet;
import com.google.inject.Scopes;
import com.google.inject.servlet.ServletModule;
import com.thetransactioncompany.cors.CORSFilter;


import java.util.logging.Logger;


public class GuiceRouting extends ServletModule {
    @SuppressWarnings({"unused"})
    static final Logger LOG = Logger.getLogger(GuiceRouting.class.getName());


    public GuiceRouting() {
    }

    @Override
    protected void configureServlets() {
        bind(CORSFilter.class).in(Scopes.SINGLETON);
        filter("/data/*").through(CORSFilter.class);
        serve("/*").with(RelayServlet.class);
    }
}
