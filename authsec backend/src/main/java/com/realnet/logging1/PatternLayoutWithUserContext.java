package com.realnet.logging1;

import ch.qos.logback.classic.PatternLayout;

public class PatternLayoutWithUserContext extends PatternLayout {
    static {
        PatternLayout.defaultConverterMap.put(
            "user", UserConverter.class.getName());
        PatternLayout.defaultConverterMap.put(
            "session", SessionConverter.class.getName());
    }
}