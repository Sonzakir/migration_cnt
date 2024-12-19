package com.fazli;

import java.util.List;

public abstract class  TelefonbuchEintrag {


    protected List<String> name;
    protected List<Adresse> adresse;


    public TelefonbuchEintrag(){}


    public TelefonbuchEintrag(List<String> name, List<Adresse> adresse) {
        this.name = name;
        this.adresse = adresse;
    }

    public void setName(List<String> name) {
        this.name = name;
    }

    public List<String> getName() {
        return name;
    }


    public List<Adresse> getAdresse() {
        return adresse;
    }

    public void setAdresse(List<Adresse> adresse) {
        this.adresse = adresse;
    }

    /**
     * Eine neue Adresse zur ArrayList hinzufügen
     * @param adresse zuvor nicht vorhandene neue Adresse
     */
    public void adresseHinzufuegen(Adresse adresse){
        this.adresse.add(adresse);
    }



    public void setzePrimaereAdresse(int index){
        if(adresse!=null){
            Adresse primaer = adresse.remove(index);
            adresse.add(0, primaer);
        }
    }
    public void setzePrimaereAdresse(Adresse Adresse){
        if(adresse!=null){
            int index = adresse.indexOf(Adresse);
            setzePrimaereAdresse(index);
        }
    }

    /**
     * Ersetzen eine Adresse in der ArrayList durch eine andere
     * @param alteAdresse ersetzende Adresseintrag
     * @param neueAdresse
     */
    public void umziehen(Adresse alteAdresse, Adresse neueAdresse){
        adresse.remove(alteAdresse);
        adresseHinzufuegen(neueAdresse);
    }

}
