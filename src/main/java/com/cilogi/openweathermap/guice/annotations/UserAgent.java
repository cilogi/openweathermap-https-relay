// Copyright (c) 2016 Cilogi. All Rights Reserved.
//
// File:        UseCache.java  (9/9/16)
// Author:      tim
//
// This file is licensed under the
// GNU GENERAL PUBLIC LICENSE
// Version 3, 29 June 2007
// https://www.gnu.org/licenses/gpl-3.0.en.html
//


package com.cilogi.openweathermap.guice.annotations;

import com.google.inject.BindingAnnotation;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@BindingAnnotation
@Target({FIELD, PARAMETER, METHOD})
@Retention(RUNTIME)
public @interface UserAgent {
}
