// Copyright (c) 2011 Cilogi All Rights Reserved.
//
// File:        GuiceBindings.java  (12-Oct-2011)
// Author:      tim

// This file is licensed under the
// GNU GENERAL PUBLIC LICENSE
// Version 3, 29 June 2007
// https://www.gnu.org/licenses/gpl-3.0.en.html


package com.cilogi.openweathermap.guice;


import com.cilogi.openweathermap.guice.annotations.UserAgent;
import com.google.inject.AbstractModule;
import com.google.inject.name.Names;

import java.util.logging.Logger;


public class GuiceBindings extends AbstractModule {
    static final Logger LOG = Logger.getLogger(GuiceBindings.class.getName());


    public GuiceBindings() {}

    @Override
    protected void configure() {
        bind(String.class).annotatedWith(UserAgent.class).toInstance("OpenWeatherMap HTTPS Relay (Email:tim.niblett@cilogi.com)");
    }
}
