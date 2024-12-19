package com.fazli;

import java.util.stream.Stream;

public enum Branche {
    SOFTWARE("SOFTWARE"),
    ARZT("ARZT"),
    APOTHEKE("APOTHEKE"),
    VERSICHERUNG("VERSICHERUNG"),
    WOHNUNGSWIRTSCHAFT("WOHNUNGSWIRTSCHAFT"),
    ANWALT("ANWALT");

     private final String name;

     Branche(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public static Stream<Branche> stream(){
         return Stream.of(Branche.values());
    }
}
