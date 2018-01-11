package com.webgalaxie.blischke.bachelortakesix.models;

/**
 * Created by Bexx on 20.12.17.
 */

public class Immobilie {

    private String immoID, immo_name, image_name, immo_street, immo_housenumber, immo_postcode,
            immo_city, immo_state, immo_country, immo_etage,
            immo_etage_total,
            immo_kaufpreis, immo_preis_pro_qm, immo_warmmiete, immo_kaltmiete, immo_nebenkosten,
            immo_heizkosten, immo_mietzuschläge, immo_gesamtmiete, immo_sonstige_kosten, immo_kaution_kosten, immo_kaution_text,
            immo_provision, immo_baujahr, immo_sanierung, immo_wohnfläche, immo_grundstücksfläche, immo_nutzfläche,
            immo_gesamtfläche, immo_zimmer_gesamt, immo_schlafzimmer, immo_badezimmer,
            immo_desc_text, immo_location_text, immo_ausstattung_text, immo_ausgewählte_ausstattung,
            immo_art, immo_lage, immo_nutzungsart, immo_vermarktung, immo_alter, immo_heizungsart, immo_energieträger,
            immo_objektzustand, immo_ausstattung_quality, immo_energieausweis_yes_no, immo_energieausweis_type, immo_image_url;

    public Immobilie() {
    }


    public Immobilie(String immoID, String immo_name, String image_name, String immo_street,
                     String immo_housenumber, String immo_postcode, String immo_city,
                     String immo_state, String immo_country, String immo_etage,
                     String immo_etage_total, String immo_kaufpreis, String immo_preis_pro_qm,
                     String immo_warmmiete, String immo_kaltmiete, String immo_nebenkosten,
                     String immo_heizkosten, String immo_mietzuschläge, String immo_gesamtmiete,
                     String immo_sonstige_kosten, String immo_kaution_kosten, String immo_kaution_text,
                     String immo_provision, String immo_baujahr, String immo_sanierung,
                     String immo_wohnfläche, String immo_grundstücksfläche, String immo_nutzfläche,
                     String immo_gesamtfläche, String immo_zimmer_gesamt, String immo_schlafzimmer,
                     String immo_badezimmer, String immo_desc_text, String immo_location_text,
                     String immo_ausstattung_text, String immo_ausgewählte_ausstattung,
                     String immo_art, String immo_lage, String immo_nutzungsart,
                     String immo_vermarktung, String immo_alter, String immo_heizungsart, String immo_energieträger,
                     String immo_objektzustand, String immo_ausstattung_quality, String immo_energieausweis_yes_no,
                     String immo_energieausweis_type, String immo_image_url) {
        this.immoID = immoID;
        this.immo_name = immo_name;
        this.image_name = image_name;
        this.immo_street = immo_street;
        this.immo_housenumber = immo_housenumber;
        this.immo_postcode = immo_postcode;
        this.immo_city = immo_city;
        this.immo_state = immo_state;
        this.immo_country = immo_country;
        this.immo_etage = immo_etage;
        this.immo_etage_total = immo_etage_total;
        this.immo_kaufpreis = immo_kaufpreis;
        this.immo_preis_pro_qm = immo_preis_pro_qm;
        this.immo_warmmiete = immo_warmmiete;
        this.immo_kaltmiete = immo_kaltmiete;
        this.immo_nebenkosten = immo_nebenkosten;
        this.immo_heizkosten = immo_heizkosten;
        this.immo_mietzuschläge = immo_mietzuschläge;
        this.immo_gesamtmiete = immo_gesamtmiete;
        this.immo_sonstige_kosten = immo_sonstige_kosten;
        this.immo_kaution_kosten = immo_kaution_kosten;
        this.immo_kaution_text = immo_kaution_text;
        this.immo_provision = immo_provision;
        this.immo_baujahr = immo_baujahr;
        this.immo_sanierung = immo_sanierung;
        this.immo_wohnfläche = immo_wohnfläche;
        this.immo_grundstücksfläche = immo_grundstücksfläche;
        this.immo_nutzfläche = immo_nutzfläche;
        this.immo_gesamtfläche = immo_gesamtfläche;
        this.immo_zimmer_gesamt = immo_zimmer_gesamt;
        this.immo_schlafzimmer = immo_schlafzimmer;
        this.immo_badezimmer = immo_badezimmer;
        this.immo_desc_text = immo_desc_text;
        this.immo_location_text = immo_location_text;
        this.immo_ausstattung_text = immo_ausstattung_text;
        this.immo_ausgewählte_ausstattung = immo_ausgewählte_ausstattung;
        this.immo_art = immo_art;
        this.immo_lage = immo_lage;
        this.immo_nutzungsart = immo_nutzungsart;
        this.immo_vermarktung = immo_vermarktung;
        this.immo_alter = immo_alter;
        this.immo_heizungsart = immo_heizungsart;
        this.immo_energieträger = immo_energieträger;
        this.immo_objektzustand = immo_objektzustand;
        this.immo_ausstattung_quality = immo_ausstattung_quality;
        this.immo_energieausweis_yes_no = immo_energieausweis_yes_no;
        this.immo_energieausweis_type = immo_energieausweis_type;
        this.immo_image_url = immo_image_url;

    }

    public String getImmo_image_url() {
        return immo_image_url;
    }

    public String getImmoID() {
        return immoID;
    }

    public String getImmo_name() {
        return immo_name;
    }

    public String getImage_name() {
        return image_name;
    }

    public String getImmo_street() {
        return immo_street;
    }

    public String getImmo_housenumber() {
        return immo_housenumber;
    }

    public String getImmo_postcode() {
        return immo_postcode;
    }

    public String getImmo_city() {
        return immo_city;
    }

    public String getImmo_state() {
        return immo_state;
    }

    public String getImmo_country() {
        return immo_country;
    }


    public String getImmo_etage() {
        return immo_etage;
    }

    public String getImmo_etage_total() {
        return immo_etage_total;
    }


    public String getImmo_kaufpreis() {
        return immo_kaufpreis;
    }

    public String getImmo_preis_pro_qm() {
        return immo_preis_pro_qm;
    }

    public String getImmo_warmmiete() {
        return immo_warmmiete;
    }

    public String getImmo_kaltmiete() {
        return immo_kaltmiete;
    }

    public String getImmo_nebenkosten() {
        return immo_nebenkosten;
    }

    public String getImmo_heizkosten() {
        return immo_heizkosten;
    }

    public String getImmo_mietzuschläge() {
        return immo_mietzuschläge;
    }

    public String getImmo_gesamtmiete() {
        return immo_gesamtmiete;
    }

    public String getImmo_sonstige_kosten() {
        return immo_sonstige_kosten;
    }

    public String getImmo_kaution_kosten() {
        return immo_kaution_kosten;
    }

    public String getImmo_provision() {
        return immo_provision;
    }

    public String getImmo_baujahr() {
        return immo_baujahr;
    }

    public String getImmo_sanierung() {
        return immo_sanierung;
    }

    public String getImmo_wohnfläche() {
        return immo_wohnfläche;
    }

    public String getImmo_grundstücksfläche() {
        return immo_grundstücksfläche;
    }

    public String getImmo_nutzfläche() {
        return immo_nutzfläche;
    }

    public String getImmo_gesamtfläche() {
        return immo_gesamtfläche;
    }

    public String getImmo_zimmer_gesamt() {
        return immo_zimmer_gesamt;
    }

    public String getImmo_schlafzimmer() {
        return immo_schlafzimmer;
    }

    public String getImmo_badezimmer() {
        return immo_badezimmer;
    }

    public String getImmo_desc_text() {
        return immo_desc_text;
    }

    public String getImmo_location_text() {
        return immo_location_text;
    }

    public String getImmo_ausstattung_text() {
        return immo_ausstattung_text;
    }

    public String getImmo_ausgewählte_ausstattung() {
        return immo_ausgewählte_ausstattung;
    }

    public String getImmo_art() {
        return immo_art;
    }

    public String getImmo_lage() {
        return immo_lage;
    }

    public String getImmo_nutzungsart() {
        return immo_nutzungsart;
    }

    public String getImmo_vermarktung() {
        return immo_vermarktung;
    }

    public String getImmo_alter() {
        return immo_alter;
    }

    public String getImmo_kaution_text() {
        return immo_kaution_text;
    }

    public String getImmo_heizungsart() {
        return immo_heizungsart;
    }

    public String getImmo_energieträger() {
        return immo_energieträger;
    }

    public String getImmo_objektzustand() {
        return immo_objektzustand;
    }

    public String getImmo_ausstattung_quality() {
        return immo_ausstattung_quality;
    }

    public String getImmo_energieausweis_yes_no() {
        return immo_energieausweis_yes_no;
    }

    public String getImmo_energieausweis_type() {
        return immo_energieausweis_type;
    }
}