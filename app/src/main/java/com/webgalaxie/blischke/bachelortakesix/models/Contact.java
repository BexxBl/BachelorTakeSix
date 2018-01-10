package com.webgalaxie.blischke.bachelortakesix.models;

/**
 * Created by Bexx on 09.01.18.
 */

public class Contact {

    String id;
    String lastname;
    String firstname;
    String title;
    String salutation;
    String firm;
    String contact_street;
    String contact_housenumber;
    String contact_postcode;
    String contact_city;
    String email;
    String phonenumber;
    String website;


    public Contact(String id, String lastname, String firstname, String title, String salutation, String firm, String contact_street, String contact_housenumber, String contact_postcode, String contact_city, String email, String phonenumber, String website) {
        this.lastname = lastname;
        this.firstname = firstname;
        this.title = title;
        this.salutation = salutation;
        this.firm = firm;
        this.contact_street = contact_street;
        this.contact_housenumber = contact_housenumber;
        this.contact_postcode = contact_postcode;
        this.contact_city = contact_city;
        this.email = email;
        this.phonenumber = phonenumber;
        this.website = website;
    }

    public String getLastname() {
        return lastname;
    }

    public String getFirstname() {
        return firstname;
    }

    public String getTitle() {
        return title;
    }

    public String getSalutation() {
        return salutation;
    }

    public String getFirm() {
        return firm;
    }

    public String getContact_street() {
        return contact_street;
    }

    public String getContact_housenumber() {
        return contact_housenumber;
    }

    public String getContact_postcode() {
        return contact_postcode;
    }

    public String getContact_city() {
        return contact_city;
    }


    public String getEmail() {
        return email;
    }

    public String getPhonenumber() {
        return phonenumber;
    }

    public String getWebsite() {
        return website;
    }

    public String getId() {
        return id;
    }

}
