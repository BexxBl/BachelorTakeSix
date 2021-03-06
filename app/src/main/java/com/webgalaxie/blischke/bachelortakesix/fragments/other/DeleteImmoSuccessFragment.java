package com.webgalaxie.blischke.bachelortakesix.fragments.other;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.webgalaxie.blischke.bachelortakesix.R;
import com.webgalaxie.blischke.bachelortakesix.fragments.main_fragments.AddNewExpose;
import com.webgalaxie.blischke.bachelortakesix.fragments.main_fragments.ShowExposeFragment;


/**
 * A simple {@link Fragment} subclass.
 */
public class DeleteImmoSuccessFragment extends Fragment {


    Button goToAddNewImmoBTN, goToAllExposeBTN;

    public DeleteImmoSuccessFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_delete_immo_success, container, false);
        goToAddNewImmoBTN = v.findViewById(R.id.goToAddNewImmoBTN);
        goToAddNewImmoBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment addNewExposeFragment = new AddNewExpose();
                FragmentManager fragmentManager = getFragmentManager();
                fragmentManager.beginTransaction().replace(R.id.content_frame, addNewExposeFragment).commit();
            }
        });

        goToAllExposeBTN = v.findViewById(R.id.goToAllExposeBTN);
        goToAllExposeBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment showAllExposeFragment = new ShowExposeFragment();
                FragmentManager fragmentManager = getFragmentManager();
                fragmentManager.beginTransaction().replace(R.id.content_frame, showAllExposeFragment).commit();
            }
        });
        return v;
    }

}
