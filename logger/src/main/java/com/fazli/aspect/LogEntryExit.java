package com.fazli.aspect;

import org.springframework.boot.logging.LogLevel;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;


import java.time.temporal.ChronoUnit;

@Retention(RetentionPolicy.RUNTIME)

@Target(ElementType.METHOD)

public @interface LogEntryExit {
    // logging level
    LogLevel level() default LogLevel.INFO;
    // dauer
    ChronoUnit dauer() default ChronoUnit.SECONDS;
    // ob dauer angezeigt werden soll
    boolean showDauer() default true;
    // ob method parametern angezeigt werden sollen
    boolean showArgs() default false;
    // ob ausgabe angezeigt werden soll
    boolean showResult() default false;

}
