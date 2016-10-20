// Copyright (c) 2016 Cilogi All Rights Reserved.
//
// File:        GuiceContextListener.java  (25-Oct-2016)
// Author:      tim

//
// This file is licensed under the
// GNU GENERAL PUBLIC LICENSE
// Version 3, 29 June 2007
// https://www.gnu.org/licenses/gpl-3.0.en.html.
//


package com.cilogi.openweathermap.guice;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.servlet.GuiceServletContextListener;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import java.util.logging.Logger;


public class GuiceContextListener extends GuiceServletContextListener {
    static final Logger LOG = Logger.getLogger(GuiceContextListener.class.getName());

    public GuiceContextListener() {
    }


    @Override
    protected Injector getInjector() {
        return Guice.createInjector(new GuiceBindings(), new GuiceRouting());
    }
    
}
