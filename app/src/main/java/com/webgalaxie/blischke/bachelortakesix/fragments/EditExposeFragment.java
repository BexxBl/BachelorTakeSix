package com.webgalaxie.blischke.bachelortakesix.fragments;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.MimeTypeMap;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.webgalaxie.blischke.bachelortakesix.R;
import com.webgalaxie.blischke.bachelortakesix.models.Contact;
import com.webgalaxie.blischke.bachelortakesix.models.Immobilie;
import com.webgalaxie.blischke.bachelortakesix.models.PictureUpload;
import com.webgalaxie.blischke.bachelortakesix.other.Constants;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import static android.app.Activity.RESULT_OK;

/**
 * Created by Bexx on 03.01.18.
 */

public class EditExposeFragment extends Fragment implements View.OnClickListener {

    // inputs for the Immo
    private EditText input_immo_name, input_image_name, input_immo_street, input_immo_housenumber, input_immo_postcode,
            input_immo_city, input_immo_state, input_immo_country, input_immo_etage,
            input_immo_etage_total, input_immo_kaufpreis, input_immo_preis_pro_qm,
            input_immo_warmmiete, input_immo_kaltmiete, input_immo_nebenkosten, input_immo_heizkosten, input_immo_mietzuschläge,
            input_immo_gesamtmiete, input_immo_sonstige_kosten, input_immo_kaution_kosten, input_immo_kaution_text, input_immo_provision,
            input_immo_baujahr, input_immo_sanierung, input_immo_wohnfläche, input_immo_grundstücksfläche, input_immo_nutzfläche,
            input_immo_gesamtfläche, input_immo_zimmer_gesamt, input_immo_schlafzimmer, input_immo_badezimmer, input_immo_desc_text,
            input_immo_location_text, input_immo_ausstattung_text;


    // inputs for the contact informations
    private EditText input_contact_salutation, input_contact_title, input_contact_lastname, input_contact_firstname,
            input_contact_firma, input_contact_firma_street, input_contact_firma_housenumber, input_contact_firma_postcode,
            input_contact_firma_city, input_contact_firma_phonenumber, input_contact_firma_email, input_contact_firma_website;

    //define variable for dropdowns
    private Spinner spinner_immo_art, spinner_immo_nutzungsart, spinner_immo_vermarktung, spinner_immo_lage,
            spinner_immo_alter, spinner_immo_heizungsart, spinner_immo_energieträger,
            spinner_immo_objektzustand, spinner_immo_ausstattung_quality, spinner_immo_energieausweis_yes_no, spinner_immo_energieausweis_type;

    // variable for imageView
    private ImageView showPictureOfImmoView;


    //variables for buttons
    private Button choosePictureBTN, chooseAusstattungBTN;


    // define Strings to save the inputs from EditTexts
    private String string_immo_name, string_image_name, string_immo_street, string_immo_housenumber, string_immo_postcode,
            string_immo_city, string_immo_state, string_immo_country, string_immo_etage,
            string_immo_etage_total, string_immo_kaufpreis, string_immo_preis_pro_qm,
            string_immo_warmmiete, string_immo_kaltmiete, string_immo_nebenkosten, string_immo_heizkosten, string_immo_mietzuschläge,
            string_immo_gesamtmiete, string_immo_sonstige_kosten, string_immo_kaution_kosten, string_immo_kaution_text, string_immo_provision,
            string_immo_baujahr, string_immo_sanierung, string_immo_wohnfläche, string_immo_grundstücksfläche, string_immo_nutzfläche,
            string_immo_gesamtfläche, string_immo_zimmer_gesamt, string_immo_schlafzimmer, string_immo_badezimmer, string_immo_desc_text,
            string_immo_location_text, string_immo_ausstattung_text, string_immo_ausgewählte_ausstattung, string_immo_art, string_immo_lage,
            string_immo_nutzungsart, string_immo_vermarktung, string_immo_alter, string_immo_heizungsart, string_immo_energieträger,
            string_immo_objektzustand, string_immo_ausstattung_quality, string_immo_energieausweis_yes_no, string_immo_energieausweis_type, string_immo_image_url;


    private String string_contact_salutation, string_contact_title, string_contact_lastname, string_contact_firstname,
            string_contact_firma, string_contact_firma_street, string_contact_firma_housenumber, string_contact_firma_postcode,
            string_contact_firma_city, string_contact_firma_phonenumber, string_contact_firma_email, string_contact_firma_website;


    private TextView immo_ausgewählte_ausstattung;


    // variables for firebase
    private Uri filePath;
    private StorageReference pictureStorageReference;
    private DatabaseReference pictureDatabase, immoDatabase, contactDatabase;

    private FirebaseUser user;
    private String user_id;

    // variables for the Datamodels
    private Immobilie immobilie;
    private Contact contact;

    // bundle to recieve the immo id
    private Bundle bundle;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);

    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        // inflate the layout
        View v = inflater.inflate(R.layout.edit_expose_layout, container, false);

        // find the view elements
        input_immo_name = v.findViewById(R.id.input_immo_name);
        input_image_name = v.findViewById(R.id.input_image_name);
        input_immo_street = v.findViewById(R.id.input_immo_street);
        input_immo_housenumber = v.findViewById(R.id.input_immo_housenumber);
        input_immo_postcode = v.findViewById(R.id.input_immo_postcode);
        input_immo_city = v.findViewById(R.id.input_immo_city);
        input_immo_state = v.findViewById(R.id.input_immo_state);
        input_immo_country = v.findViewById(R.id.input_immo_country);
        input_immo_etage = v.findViewById(R.id.input_immo_etage);
        input_immo_etage_total = v.findViewById(R.id.input_immo_etage_total);
        input_immo_kaufpreis = v.findViewById(R.id.input_immo_kaufpreis);
        input_immo_preis_pro_qm = v.findViewById(R.id.input_immo_preis_pro_qm);
        input_immo_warmmiete = v.findViewById(R.id.input_immo_warmmiete);
        input_immo_kaltmiete = v.findViewById(R.id.input_immo_kaltmiete);
        input_immo_nebenkosten = v.findViewById(R.id.input_immo_nebenkosten);
        input_immo_heizkosten = v.findViewById(R.id.input_immo_heizkosten);
        input_immo_mietzuschläge = v.findViewById(R.id.input_immo_mietzuschläge);
        input_immo_gesamtmiete = v.findViewById(R.id.input_immo_gesamtmiete);
        input_immo_sonstige_kosten = v.findViewById(R.id.input_immo_sonstige_kosten);
        input_immo_kaution_kosten = v.findViewById(R.id.input_immo_kaution_kosten);
        input_immo_kaution_text = v.findViewById(R.id.input_immo_kaution_text);
        input_immo_provision = v.findViewById(R.id.input_immo_provision);
        input_immo_baujahr = v.findViewById(R.id.input_immo_baujahr);
        input_immo_sanierung = v.findViewById(R.id.input_immo_sanierung);
        input_immo_wohnfläche = v.findViewById(R.id.input_immo_wohnfläche);
        input_immo_grundstücksfläche = v.findViewById(R.id.input_immo_grundstücksfläche);
        input_immo_nutzfläche = v.findViewById(R.id.input_immo_nutzfläche);
        input_immo_gesamtfläche = v.findViewById(R.id.input_immo_gesamtfläche);
        input_immo_zimmer_gesamt = v.findViewById(R.id.input_immo_zimmer_gesamt);
        input_immo_schlafzimmer = v.findViewById(R.id.input_immo_schlafzimmer);
        input_immo_badezimmer = v.findViewById(R.id.input_immo_badezimmer);
        input_immo_desc_text = v.findViewById(R.id.input_immo_desc_text);
        input_immo_location_text = v.findViewById(R.id.input_immo_location_text);
        input_immo_ausstattung_text = v.findViewById(R.id.input_immo_ausstattung_text);


        input_contact_salutation = v.findViewById(R.id.input_contact_salutation);
        input_contact_title = v.findViewById(R.id.input_contact_title);
        input_contact_lastname = v.findViewById(R.id.input_contact_lastname);
        input_contact_firstname = v.findViewById(R.id.input_contact_firstname);
        input_contact_firma = v.findViewById(R.id.input_contact_firma);
        input_contact_firma_street = v.findViewById(R.id.input_contact_firma_street);
        input_contact_firma_housenumber = v.findViewById(R.id.input_contact_firma_housenumber);
        input_contact_firma_postcode = v.findViewById(R.id.input_contact_firma_postcode);
        input_contact_firma_city = v.findViewById(R.id.input_contact_firma_city);
        input_contact_firma_phonenumber = v.findViewById(R.id.input_contact_firma_phonenumber);
        input_contact_firma_email = v.findViewById(R.id.input_contact_firma_email);
        input_contact_firma_website = v.findViewById(R.id.input_contact_firma_website);


        showPictureOfImmoView = v.findViewById(R.id.showPictureOfImmoView);

        spinner_immo_art = v.findViewById(R.id.spinner_immo_art);
        spinner_immo_nutzungsart = v.findViewById(R.id.spinner_immo_nutzungsart);
        spinner_immo_vermarktung = v.findViewById(R.id.spinner_immo_vermarktung);
        spinner_immo_lage = v.findViewById(R.id.spinner_immo_lage);
        spinner_immo_alter = v.findViewById(R.id.spinner_immo_alter);

        spinner_immo_heizungsart = v.findViewById(R.id.spinner_immo_heizungsart);
        spinner_immo_energieträger = v.findViewById(R.id.spinner_immo_energieträger);
        spinner_immo_objektzustand = v.findViewById(R.id.spinner_immo_objektzustand);
        spinner_immo_ausstattung_quality = v.findViewById(R.id.spinner_immo_ausstattung_quality);
        spinner_immo_energieausweis_yes_no = v.findViewById(R.id.spinner_immo_energieausweis_yes_no);
        spinner_immo_energieausweis_type = v.findViewById(R.id.spinner_immo_energieausweis_type);


        choosePictureBTN = v.findViewById(R.id.choosePictureBTN);
        chooseAusstattungBTN = v.findViewById(R.id.chooseAusstattungBTN);


        immo_ausgewählte_ausstattung = v.findViewById(R.id.immo_ausgewählte_ausstattung);


        // set up the content of the spinners
        spinner_immo_art.setAdapter(ArrayAdapter.createFromResource(getContext(), R.array.expose_types, android.R.layout.simple_spinner_dropdown_item));
        spinner_immo_nutzungsart.setAdapter(ArrayAdapter.createFromResource(getContext(), R.array.nutzungsart, android.R.layout.simple_spinner_dropdown_item));
        spinner_immo_vermarktung.setAdapter(ArrayAdapter.createFromResource(getContext(), R.array.vermarktung, android.R.layout.simple_spinner_dropdown_item));
        spinner_immo_lage.setAdapter(ArrayAdapter.createFromResource(getContext(), R.array.lage, android.R.layout.simple_spinner_dropdown_item));
        spinner_immo_alter.setAdapter(ArrayAdapter.createFromResource(getContext(), R.array.alter, android.R.layout.simple_spinner_dropdown_item));


        spinner_immo_heizungsart.setAdapter(ArrayAdapter.createFromResource(getContext(), R.array.heizungsart, android.R.layout.simple_spinner_dropdown_item));
        spinner_immo_energieträger.setAdapter(ArrayAdapter.createFromResource(getContext(), R.array.energieträger, android.R.layout.simple_spinner_dropdown_item));
        spinner_immo_objektzustand.setAdapter(ArrayAdapter.createFromResource(getContext(), R.array.objektzustand, android.R.layout.simple_spinner_dropdown_item));
        spinner_immo_ausstattung_quality.setAdapter(ArrayAdapter.createFromResource(getContext(), R.array.ausstattung_quality, android.R.layout.simple_spinner_dropdown_item));
        spinner_immo_energieausweis_yes_no.setAdapter(ArrayAdapter.createFromResource(getContext(), R.array.energieausweis_yes_no, android.R.layout.simple_spinner_dropdown_item));
        spinner_immo_energieausweis_type.setAdapter(ArrayAdapter.createFromResource(getContext(), R.array.energieausweistype, android.R.layout.simple_spinner_dropdown_item));


        // get an reference to the current user and his id
        user = FirebaseAuth.getInstance().getCurrentUser();
        user_id = user.getUid();

        // get the exposeID from the showFragment
        bundle = getArguments();
        String immoID = bundle.getString("exposeID");


        // get an reference to the database where the immo objects are stored
        immoDatabase = FirebaseDatabase.getInstance().getReference(Constants.DATABASE_PATH_IMMOBILIEN).child(user_id).child(immoID);
        contactDatabase = FirebaseDatabase.getInstance().getReference(Constants.DATABASE_PATH_CONTACTS).child(user_id).child(immoID);
        pictureDatabase = FirebaseDatabase.getInstance().getReference(Constants.DATABASE_PATH_UPLOADS).child(user_id).child(immoID);


        //set up the onClickListener for the Buttons
        choosePictureBTN.setOnClickListener(this);
        chooseAusstattungBTN.setOnClickListener(this);


        //get the data from the Database
        contactDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                input_contact_salutation.setText(String.valueOf(dataSnapshot.child("salutation").getValue()).toString());
                input_contact_title.setText(String.valueOf(dataSnapshot.child("title").getValue()).toString());
                input_contact_lastname.setText(String.valueOf(dataSnapshot.child("lastname").getValue()).toString());
                input_contact_firstname.setText(String.valueOf(dataSnapshot.child("firstname").getValue()).toString());
                input_contact_firma.setText(String.valueOf(dataSnapshot.child("firm").getValue()).toString());
                input_contact_firma_street.setText(String.valueOf(dataSnapshot.child("contact_street").getValue()).toString());
                input_contact_firma_housenumber.setText(String.valueOf(dataSnapshot.child("contact_housenumber").getValue()).toString());
                input_contact_firma_postcode.setText(String.valueOf(dataSnapshot.child("contact_postcode").getValue()).toString());
                input_contact_firma_city.setText(String.valueOf(dataSnapshot.child("contact_city").getValue()).toString());
                input_contact_firma_phonenumber.setText(String.valueOf(dataSnapshot.child("phonenumber").getValue()).toString());
                input_contact_firma_email.setText(String.valueOf(dataSnapshot.child("email").getValue()).toString());
                input_contact_firma_website.setText(String.valueOf(dataSnapshot.child("website").getValue()).toString());

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        immoDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                input_immo_name.setText(String.valueOf(dataSnapshot.child("immo_name").getValue()).toString());
                input_image_name.setText(String.valueOf(dataSnapshot.child("image_name").getValue()).toString());
                input_immo_street.setText(String.valueOf(dataSnapshot.child("immo_street").getValue()).toString());
                input_immo_housenumber.setText(String.valueOf(dataSnapshot.child("immo_housenumber").getValue()).toString());
                input_immo_postcode.setText(String.valueOf(dataSnapshot.child("immo_postcode").getValue()).toString());
                input_immo_city.setText(String.valueOf(dataSnapshot.child("immo_city").getValue()).toString());
                input_immo_state.setText(String.valueOf(dataSnapshot.child("immo_state").getValue()).toString());
                input_immo_country.setText(String.valueOf(dataSnapshot.child("immo_country").getValue()).toString());
                input_immo_etage.setText(String.valueOf(dataSnapshot.child("immo_etage").getValue()).toString());
                input_immo_etage_total.setText(String.valueOf(dataSnapshot.child("immo_etage_total").getValue()).toString());
                input_immo_kaufpreis.setText(String.valueOf(dataSnapshot.child("immo_kaufpreis").getValue()).toString());
                input_immo_preis_pro_qm.setText(String.valueOf(dataSnapshot.child("immo_preis_pro_qm").getValue()).toString());
                input_immo_warmmiete.setText(String.valueOf(dataSnapshot.child("immo_warmmiete").getValue()).toString());
                input_immo_kaltmiete.setText(String.valueOf(dataSnapshot.child("immo_kaltmiete").getValue()).toString());
                input_immo_nebenkosten.setText(String.valueOf(dataSnapshot.child("immo_nebenkosten").getValue()).toString());
                input_immo_heizkosten.setText(String.valueOf(dataSnapshot.child("immo_heizkosten").getValue()).toString());
                input_immo_mietzuschläge.setText(String.valueOf(dataSnapshot.child("immo_mietzuschläge").getValue()).toString());
                input_immo_gesamtmiete.setText(String.valueOf(dataSnapshot.child("immo_gesamtmiete").getValue()).toString());
                input_immo_sonstige_kosten.setText(String.valueOf(dataSnapshot.child("immo_sonstige_kosten").getValue()).toString());
                input_immo_kaution_kosten.setText(String.valueOf(dataSnapshot.child("immo_kaution_kosten").getValue()).toString());
                input_immo_kaution_text.setText(String.valueOf(dataSnapshot.child("immo_kaution_text").getValue()).toString());
                input_immo_provision.setText(String.valueOf(dataSnapshot.child("immo_provision").getValue()).toString());
                input_immo_baujahr.setText(String.valueOf(dataSnapshot.child("immo_baujahr").getValue()).toString());
                input_immo_sanierung.setText(String.valueOf(dataSnapshot.child("immo_sanierung").getValue()).toString());
                input_immo_wohnfläche.setText(String.valueOf(dataSnapshot.child("immo_wohnfläche").getValue()).toString());
                input_immo_grundstücksfläche.setText(String.valueOf(dataSnapshot.child("immo_grundstücksfläche").getValue()).toString());
                input_immo_nutzfläche.setText(String.valueOf(dataSnapshot.child("immo_nutzfläche").getValue()).toString());
                input_immo_gesamtfläche.setText(String.valueOf(dataSnapshot.child("immo_gesamtfläche").getValue()).toString());
                input_immo_zimmer_gesamt.setText(String.valueOf(dataSnapshot.child("immo_zimmer_gesamt").getValue()).toString());
                input_immo_schlafzimmer.setText(String.valueOf(dataSnapshot.child("immo_schlafzimmer").getValue()).toString());
                input_immo_badezimmer.setText(String.valueOf(dataSnapshot.child("immo_badezimmer").getValue()).toString());
                input_immo_desc_text.setText(String.valueOf(dataSnapshot.child("immo_desc_text").getValue()).toString());
                input_immo_location_text.setText(String.valueOf(dataSnapshot.child("immo_location_text").getValue()).toString());
                input_immo_ausstattung_text.setText(String.valueOf(dataSnapshot.child("immo_ausstattung_text").getValue()).toString());
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        //return the view
        return v;
    }


    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.editexposemenu, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        final String immoID = bundle.getString("exposeID");

        // Handle item selection
        switch (item.getItemId()) {
            case R.id.action_edit_expose:
                Toast.makeText(getContext(), "Geänderte Daten werden gespeichert.", Toast.LENGTH_SHORT).show();
                //Clicking on Save button will save Expose to database
                updateExpose();
                return true;

            case R.id.action_cancel_edit_expose:
                Toast.makeText(getContext(), "Bearbeiten des Exposes wird abgebrochen", Toast.LENGTH_SHORT).show();
                Fragment showAllExposeFragement = new ShowAllExposeFragment();
                Bundle newBundle = new Bundle();
                newBundle.putString("exposeID", immoID);
                showAllExposeFragement.setArguments(newBundle);
                FragmentManager manager = getFragmentManager();
                manager.beginTransaction().replace(R.id.content_frame, showAllExposeFragement).commit();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }


    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.choosePictureBTN:
                choosePicture();
                break;
            case R.id.chooseAusstattungBTN:
                chooseAusstattung();
                break;

        }
    }

    private void choosePicture() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), Constants.PICK_IMAGE_REQUEST);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == Constants.PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            filePath = data.getData();
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContext().getContentResolver(), filePath);
                showPictureOfImmoView.setImageBitmap(bitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public String getFileExtension(Uri uri) {
        ContentResolver cR = getContext().getContentResolver();
        MimeTypeMap mime = MimeTypeMap.getSingleton();
        return mime.getExtensionFromMimeType(cR.getType(uri));
    }

    private void changeFragment() {

        Fragment showAllExposeFragment = new ShowAllExposeFragment();
        FragmentManager fragmentManager = getFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.content_frame, showAllExposeFragment).commit();
    }

    private void chooseAusstattung() {

        // Build an AlertDialog
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());

        // String array for alert dialog multi choice items
        String[] features = new String[]{
                "WG geeignet",
                "Dusche",
                "Wanne",
                "offene Küche",
                "Kamin",
                "Fahrstuhl",
                "klimatisiert",
                "Stellplatz/Garage",
                "Garten",
                "Gartenmitbenutzung",
                "möbliert",
                "behindertengerecht",
                "seniorengerecht",
                "Abstellraum",
                "Fahrradraum",
                "Keller",
                "Gäste WC",
                "Rollladen",
                "Pool"
        };

        // Boolean array for initial selected items
        final boolean[] checkedFeatures = new boolean[]{
                false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false


        };

        // Convert the color array to list
        final List<String> colorsList = Arrays.asList(features);

        // Set multiple choice items for alert dialog

        builder.setMultiChoiceItems(features, checkedFeatures, new DialogInterface.OnMultiChoiceClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which, boolean isChecked) {

                // Update the current focused item's checked status
                checkedFeatures[which] = isChecked;

                // Get the current focused item
                String currentItem = colorsList.get(which);

            }
        });

        // Specify the dialog is not cancelable
        builder.setCancelable(false);

        // Set a title for alert dialog
        builder.setTitle("Ausstattung");

        // Set the positive/yes button click listener
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // Do something when click positive button
                for (int i = 0; i < checkedFeatures.length; i++) {
                    boolean checked = checkedFeatures[i];
                    if (checked) {
                        immo_ausgewählte_ausstattung.setText(immo_ausgewählte_ausstattung.getText() + colorsList.get(i) + "\n");
                    }
                }
            }
        });


        // Set the neutral/cancel button click listener
        builder.setNeutralButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // Do something when click the neutral button
                dialog.cancel();
            }
        });

        AlertDialog dialog = builder.create();
        // Display the alert dialog on interface
        dialog.show();
    }

    private void getStringsFromInputs() {

        string_immo_name = input_immo_name.getText().toString().trim();
        string_image_name = input_image_name.getText().toString().trim();
        string_immo_street = input_immo_street.getText().toString().trim();
        string_immo_housenumber = input_immo_housenumber.getText().toString().trim();
        string_immo_postcode = input_immo_postcode.getText().toString().trim();
        string_immo_city = input_immo_city.getText().toString().trim();
        string_immo_state = input_immo_state.getText().toString().trim();
        string_immo_country = input_immo_country.getText().toString().trim();
        string_immo_etage = input_immo_etage.getText().toString().trim();

        string_immo_etage_total = input_immo_etage_total.getText().toString().trim();
        string_immo_kaufpreis = input_immo_kaufpreis.getText().toString().trim();
        string_immo_preis_pro_qm = input_immo_preis_pro_qm.getText().toString().trim();
        string_immo_warmmiete = input_immo_warmmiete.getText().toString().trim();
        string_immo_kaltmiete = input_immo_kaltmiete.getText().toString().trim();
        string_immo_nebenkosten = input_immo_nebenkosten.getText().toString().trim();
        string_immo_heizkosten = input_immo_heizkosten.getText().toString().trim();
        string_immo_mietzuschläge = input_immo_mietzuschläge.getText().toString().trim();
        string_immo_gesamtmiete = input_immo_gesamtmiete.getText().toString().trim();
        string_immo_sonstige_kosten = input_immo_sonstige_kosten.getText().toString().trim();
        string_immo_kaution_kosten = input_immo_kaution_kosten.getText().toString().trim();
        string_immo_kaution_text = input_immo_kaution_text.getText().toString().trim();
        string_immo_provision = input_immo_provision.getText().toString().trim();
        string_immo_baujahr = input_immo_baujahr.getText().toString().trim();
        string_immo_sanierung = input_immo_sanierung.getText().toString().trim();
        string_immo_wohnfläche = input_immo_wohnfläche.getText().toString().trim();
        string_immo_grundstücksfläche = input_immo_grundstücksfläche.getText().toString().trim();
        string_immo_nutzfläche = input_immo_nutzfläche.getText().toString().trim();
        string_immo_gesamtfläche = input_immo_gesamtfläche.getText().toString().trim();
        string_immo_zimmer_gesamt = input_immo_zimmer_gesamt.getText().toString().trim();
        string_immo_schlafzimmer = input_immo_schlafzimmer.getText().toString().trim();
        string_immo_badezimmer = input_immo_badezimmer.getText().toString().trim();
        string_immo_desc_text = input_immo_desc_text.getText().toString().trim();
        string_immo_location_text = input_immo_location_text.getText().toString().trim();
        string_immo_ausstattung_text = input_immo_ausstattung_text.getText().toString().trim();
        string_contact_salutation = input_contact_salutation.getText().toString().trim();
        string_contact_title = input_contact_title.getText().toString().trim();
        string_contact_lastname = input_contact_lastname.getText().toString().trim();
        string_contact_firstname = input_contact_firstname.getText().toString().trim();
        string_contact_firma = input_contact_firma.getText().toString().trim();
        string_contact_firma_street = input_contact_firma_street.getText().toString().trim();
        string_contact_firma_housenumber = input_contact_firma_housenumber.getText().toString().trim();
        string_contact_firma_postcode = input_contact_firma_postcode.getText().toString().trim();
        string_contact_firma_city = input_contact_firma_city.getText().toString().trim();
        string_contact_firma_phonenumber = input_contact_firma_phonenumber.getText().toString().trim();
        string_contact_firma_email = input_contact_firma_email.getText().toString().trim();
        string_contact_firma_website = input_contact_firma_website.getText().toString().trim();
        string_immo_ausgewählte_ausstattung = immo_ausgewählte_ausstattung.getText().toString();

        string_immo_art = spinner_immo_art.getItemAtPosition(spinner_immo_art.getSelectedItemPosition()).toString();
        string_immo_alter = spinner_immo_alter.getItemAtPosition(spinner_immo_alter.getSelectedItemPosition()).toString();
        string_immo_lage = spinner_immo_lage.getItemAtPosition(spinner_immo_lage.getSelectedItemPosition()).toString();
        string_immo_vermarktung = spinner_immo_vermarktung.getItemAtPosition(spinner_immo_vermarktung.getSelectedItemPosition()).toString();


        string_immo_heizungsart = spinner_immo_heizungsart.getItemAtPosition(spinner_immo_heizungsart.getSelectedItemPosition()).toString();
        string_immo_energieträger = spinner_immo_energieträger.getItemAtPosition(spinner_immo_energieträger.getSelectedItemPosition()).toString();
        string_immo_objektzustand = spinner_immo_objektzustand.getItemAtPosition(spinner_immo_objektzustand.getSelectedItemPosition()).toString();
        string_immo_ausstattung_quality = spinner_immo_ausstattung_quality.getItemAtPosition(spinner_immo_ausstattung_quality.getSelectedItemPosition()).toString();
        string_immo_energieausweis_yes_no = spinner_immo_energieausweis_yes_no.getItemAtPosition(spinner_immo_energieausweis_yes_no.getSelectedItemPosition()).toString();
        string_immo_energieausweis_type = spinner_immo_energieausweis_type.getItemAtPosition(spinner_immo_energieausweis_type.getSelectedItemPosition()).toString();


    }


    private void updateExpose() {
        // get the inputs from the input fields
        getStringsFromInputs();


        // get immoid

        bundle = getArguments();
        String immoID = bundle.getString("exposeID");
        //checking if at least name value is provided

        //displaying progress dialog while image is uploading
        final ProgressDialog progressDialog = new ProgressDialog(getContext());
        progressDialog.setTitle("Expose wird veröffentlicht hochladen");
        progressDialog.setMessage("Die Daten werden der Datenbank hinzugefügt und das Bild hochegladen.");
        progressDialog.show();


        if (!TextUtils.isEmpty(string_immo_name)) {


            // get an reference to the database where the immo objects are stored
            immoDatabase = FirebaseDatabase.getInstance().getReference(Constants.DATABASE_PATH_IMMOBILIEN).child(user_id).child(immoID);
            contactDatabase = FirebaseDatabase.getInstance().getReference(Constants.DATABASE_PATH_CONTACTS).child(user_id).child(immoID);
            pictureDatabase = FirebaseDatabase.getInstance().getReference(Constants.DATABASE_PATH_UPLOADS).child(user_id).child(immoID);


            immoDatabase.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    immoDatabase.child("immo_name").setValue(string_immo_name);
                    immoDatabase.child("image_name").setValue(string_image_name);
                    immoDatabase.child("immo_street").setValue(string_immo_street);
                    immoDatabase.child("immo_housenumber").setValue(string_immo_housenumber);
                    immoDatabase.child("immo_postcode").setValue(string_immo_postcode);
                    immoDatabase.child("immo_city").setValue(string_immo_city);
                    immoDatabase.child("immo_state").setValue(string_immo_state);
                    immoDatabase.child("immo_country").setValue(string_immo_country);
                    immoDatabase.child("immo_etage").setValue(string_immo_etage);
                    immoDatabase.child("immo_etage_total").setValue(string_immo_etage_total);
                    immoDatabase.child("immo_kaufpreis").setValue(string_immo_kaufpreis);
                    immoDatabase.child("immo_preis_pro_qm").setValue(string_immo_preis_pro_qm);
                    immoDatabase.child("immo_warmmiete").setValue(string_immo_warmmiete);
                    immoDatabase.child("immo_kaltmiete").setValue(string_immo_kaltmiete);
                    immoDatabase.child("immo_nebenkosten").setValue(string_immo_nebenkosten);
                    immoDatabase.child("immo_heizkosten").setValue(string_immo_heizkosten);
                    immoDatabase.child("immo_mietzuschläge").setValue(string_immo_mietzuschläge);
                    immoDatabase.child("immo_gesamtmiete").setValue(string_immo_gesamtmiete);
                    immoDatabase.child("immo_sonstige_kosten").setValue(string_immo_sonstige_kosten);
                    immoDatabase.child("immo_kaution_kosten").setValue(string_immo_kaution_kosten);
                    immoDatabase.child("immo_kaution_text").setValue(string_immo_kaution_text);
                    immoDatabase.child("immo_provision").setValue(string_immo_provision);
                    immoDatabase.child("immo_baujahr").setValue(string_immo_baujahr);
                    immoDatabase.child("immo_sanierung").setValue(string_immo_sanierung);
                    immoDatabase.child("immo_wohnfläche").setValue(string_immo_wohnfläche);
                    immoDatabase.child("immo_grundstücksfläche").setValue(string_immo_grundstücksfläche);
                    immoDatabase.child("immo_nutzfläche").setValue(string_immo_nutzfläche);
                    immoDatabase.child("immo_gesamtfläche").setValue(string_immo_gesamtfläche);
                    immoDatabase.child("immo_zimmer_gesamt").setValue(string_immo_zimmer_gesamt);
                    immoDatabase.child("immo_schlafzimmer").setValue(string_immo_schlafzimmer);
                    immoDatabase.child("immo_badezimmer").setValue(string_immo_badezimmer);
                    immoDatabase.child("immo_desc_text").setValue(string_immo_desc_text);
                    immoDatabase.child("immo_location_text").setValue(string_immo_location_text);
                    immoDatabase.child("immo_ausstattung_text").setValue(string_immo_ausstattung_text);

                    immoDatabase.child("immo_ausgewählte_ausstattung").setValue(string_immo_ausgewählte_ausstattung);
                    immoDatabase.child("immo_art").setValue(string_immo_art);
                    immoDatabase.child("immo_lage").setValue(string_immo_lage);
                    immoDatabase.child("immo_nutzungsart").setValue(string_immo_nutzungsart);
                    immoDatabase.child("immo_vermarktung").setValue(string_immo_vermarktung);
                    immoDatabase.child("immo_alter").setValue(string_immo_alter);
                    immoDatabase.child("immo_heizungsart").setValue(string_immo_heizungsart);
                    immoDatabase.child("immo_energieträger").setValue(string_immo_energieträger);
                    immoDatabase.child("immo_objektzustand").setValue(string_immo_objektzustand);
                    immoDatabase.child("immo_ausstattung_quality").setValue(string_immo_ausstattung_quality);
                    immoDatabase.child("immo_energieausweis_yes_no").setValue(string_immo_energieausweis_yes_no);
                    immoDatabase.child("immo_energieausweis_type").setValue(string_immo_energieausweis_type);
                    immoDatabase.child("immo_image_url").setValue(string_immo_image_url);


                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });

            contactDatabase.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {

                    contactDatabase.child("salutation").setValue(string_contact_salutation);
                    contactDatabase.child("title").setValue(string_contact_title);
                    contactDatabase.child("lastname").setValue(string_contact_lastname);
                    contactDatabase.child("firstname").setValue(string_contact_firstname);
                    contactDatabase.child("firm").setValue(string_contact_firma);
                    contactDatabase.child("contact_street").setValue(string_contact_firma_street);
                    contactDatabase.child("contact_housenumber").setValue(string_contact_firma_housenumber);
                    contactDatabase.child("contact_postcode").setValue(string_contact_firma_postcode);
                    contactDatabase.child("contact_city").setValue(string_contact_firma_city);
                    contactDatabase.child("phonenumber").setValue(string_contact_firma_phonenumber);
                    contactDatabase.child("email").setValue(string_contact_firma_email);
                    contactDatabase.child("website").setValue(string_contact_firma_website);

                }

                @Override
                public void onCancelled(DatabaseError databaseError) {


                }
            });


            pictureDatabase.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    pictureDatabase.child("url").setValue(string_immo_image_url);
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });


            // uploading the Picture
            pictureStorageReference = FirebaseStorage.getInstance().getReference(user_id).child(Constants.STORAGE_PATH_UPLOADS);


            //checking if file is available
            if (filePath != null) {


                //getting the storage reference
                StorageReference sRef = pictureStorageReference.child(Constants.STORAGE_PATH_UPLOADS + System.currentTimeMillis() + "." + getFileExtension(filePath));

                //adding the file to reference
                sRef.putFile(filePath)
                        .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                            @Override
                            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {


                                String imageName = input_image_name.getText().toString().trim();
                                String imageDownloadURL = taskSnapshot.getDownloadUrl().toString();

                                //creating the upload object to store uploaded image details
                                PictureUpload upload = new PictureUpload(imageName, imageDownloadURL);

                                //adding an upload to firebase database
                                pictureDatabase.child("url").setValue(imageDownloadURL);
                                string_immo_image_url = imageDownloadURL;


                                //dismissing the progress dialog
                                progressDialog.dismiss();


                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception exception) {
                                progressDialog.dismiss();
                                Toast.makeText(getContext(), exception.getMessage(), Toast.LENGTH_LONG).show();
                            }
                        })
                        .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                            @Override
                            public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                                //displaying the upload progress
                                double progress = (100.0 * taskSnapshot.getBytesTransferred()) / taskSnapshot.getTotalByteCount();
                                progressDialog.setMessage(((int) progress) + "% wurden hochegeladen");
                            }
                        });

            } else {

                //getting the storage reference
                StorageReference sRef = pictureStorageReference.child(Constants.STORAGE_PATH_UPLOADS + System.currentTimeMillis() + "." + getFileExtension(filePath));

                //adding the file to reference
                sRef.putFile(filePath)
                        .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                            @Override
                            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {


                                //adding an upload to firebase database
                                pictureDatabase.child("url").setValue(Constants.DEFAULT_IMMO_PICTURE_URL);
                                string_immo_image_url = Constants.DEFAULT_IMMO_PICTURE_URL;

                                //dismissing the progress dialog
                                progressDialog.dismiss();


                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception exception) {
                                progressDialog.dismiss();
                                Toast.makeText(getContext(), exception.getMessage(), Toast.LENGTH_LONG).show();
                            }
                        })
                        .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                            @Override
                            public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                                //displaying the upload progress
                                double progress = (100.0 * taskSnapshot.getBytesTransferred()) / taskSnapshot.getTotalByteCount();
                                progressDialog.setMessage(((int) progress) + "% wurden hochegeladen");
                            }
                        });
                //display an error if no file is selected
                progressDialog.dismiss();

                Toast.makeText(getContext(), "Immobile wurde erfolgreich bearbeitet.", Toast.LENGTH_LONG).show();
            }


            //change the fragment
            changeFragment();


        } else {
            Toast.makeText(getContext(), "Neue Immobilie hinzufügen ist fehlgeschlagen", Toast.LENGTH_SHORT).show();
            input_immo_name.setError("Geben Sie mindestens den Titel der Immobilie an!");
            input_immo_name.requestFocus();
        }
    }


}


