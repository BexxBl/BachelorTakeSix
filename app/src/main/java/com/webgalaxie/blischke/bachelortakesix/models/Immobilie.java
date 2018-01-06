package com.webgalaxie.blischke.bachelortakesix.models;

import android.widget.CheckBox;

/**
 * Created by Bexx on 20.12.17.
 */

public class Immobilie {


    private String
            immo_ID,
            immo_name,
            immo_street,
            immo_housenumber,
            immo_city,
            immo_postcode,
            immo_country,
            immo_desc_text,
            immo_location_text,
            immo_ausstattung_text,
            immo_type,
            immo_number_of_rooms,
            immo_number_of_sleeping_rooms,
            immo_number_of_bathrooms,
            immo_size,
            immo_price_netto,
            immo_price_brutto,
            immo_rent_cold,
            immo_rent_warm,
            immo_heating_cost,
            immo_caution,
            immo_misc_cost,
            immo_rent_sum,
            immo_price_pro_sqm
    ;

    private String immo_heatingcost_included,
            immo_show_Adress,
            immo_price_with_mwst;



    public Immobilie(){}


    public String getImmo_ID() {
        return immo_ID;
    }

    public String getImmo_name() {
        return immo_name;
    }

    public String getImmo_street() {
        return immo_street;
    }

    public String getImmo_housenumber() {
        return immo_housenumber;
    }

    public String getImmo_city() {
        return immo_city;
    }

    public String getImmo_postcode() {
        return immo_postcode;
    }

    public String getImmo_country() {
        return immo_country;
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

    public String getImmo_type() {
        return immo_type;
    }

    public String getImmo_number_of_rooms() {
        return immo_number_of_rooms;
    }

    public String getImmo_number_of_sleeping_rooms() {
        return immo_number_of_sleeping_rooms;
    }

    public String getImmo_number_of_bathrooms() {
        return immo_number_of_bathrooms;
    }

    public String getImmo_size() {
        return immo_size;
    }

    public String getImmo_price_netto() {
        return immo_price_netto;
    }

    public String getImmo_price_brutto() {
        return immo_price_brutto;
    }

    public String getImmo_rent_cold() {
        return immo_rent_cold;
    }

    public String getImmo_rent_warm() {
        return immo_rent_warm;
    }

    public String getImmo_heating_cost() {
        return immo_heating_cost;
    }

    public String getImmo_caution() {
        return immo_caution;
    }

    public String getImmo_misc_cost() {
        return immo_misc_cost;
    }

    public String getImmo_rent_sum() {
        return immo_rent_sum;
    }

    public String getImmo_price_pro_sqm() {
        return immo_price_pro_sqm;
    }

    public String getImmo_heatingcost_included() {
        return immo_heatingcost_included;
    }

    public String getImmo_show_Adress() {
        return immo_show_Adress;
    }

    public String getImmo_price_with_mwst() {
        return immo_price_with_mwst;
    }

    public Immobilie(String immo_ID, String immo_name, String immo_street, String immo_housenumber,  String immo_postcode, String immo_city, String immo_country, String immo_desc_text, String immo_location_text, String immo_ausstattung_text, String immo_type, String immo_number_of_rooms, String immo_number_of_sleeping_rooms, String immo_number_of_bathrooms, String immo_size, String immo_price_netto, String immo_price_brutto, String immo_rent_cold, String immo_rent_warm, String immo_heating_cost, String immo_caution, String immo_misc_cost, String immo_rent_sum, String immo_price_pro_sqm, String immo_heatingcost_included, String immo_show_Adress, String immo_price_with_mwst) {

        this.immo_ID = immo_ID;
        this.immo_name = immo_name;
        this.immo_street = immo_street;
        this.immo_housenumber = immo_housenumber;
        this.immo_city = immo_city;
        this.immo_postcode = immo_postcode;
        this.immo_country = immo_country;
        this.immo_desc_text = immo_desc_text;
        this.immo_location_text = immo_location_text;
        this.immo_ausstattung_text = immo_ausstattung_text;
        this.immo_type = immo_type;
        this.immo_number_of_rooms = immo_number_of_rooms;
        this.immo_number_of_sleeping_rooms = immo_number_of_sleeping_rooms;
        this.immo_number_of_bathrooms = immo_number_of_bathrooms;
        this.immo_size = immo_size;
        this.immo_price_netto = immo_price_netto;
        this.immo_price_brutto = immo_price_brutto;
        this.immo_rent_cold = immo_rent_cold;
        this.immo_rent_warm = immo_rent_warm;
        this.immo_heating_cost = immo_heating_cost;
        this.immo_caution = immo_caution;
        this.immo_misc_cost = immo_misc_cost;
        this.immo_rent_sum = immo_rent_sum;
        this.immo_price_pro_sqm = immo_price_pro_sqm;
        this.immo_heatingcost_included = immo_heatingcost_included;
        this.immo_show_Adress = immo_show_Adress;
        this.immo_price_with_mwst = immo_price_with_mwst;
    }
}
