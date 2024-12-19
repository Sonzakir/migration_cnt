package com.fazli;

import java.util.List;

public class ListUril {
    private ListUril() {
        // nop
    }


    public static <T> List<T> setSafe(List<T> vorhanden, List<T> neu) {
        if(vorhanden == null){
            vorhanden = neu;
        } else {
            try {
                vorhanden.clear();
                vorhanden.addAll(neu);
            } catch (UnsupportedOperationException e) {
                // vorhanden ist immutable
                vorhanden = neu;
            }
        }
        return vorhanden;
    }
}
