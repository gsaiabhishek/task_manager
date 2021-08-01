package com.cs251.tasktree.ui.gallery;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;
import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDialogFragment;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.cs251.tasktree.DayView;
import com.cs251.tasktree.R;

import java.util.Calendar;
import java.util.TimeZone;

public class GalleryFragment extends Fragment {

    private GalleryViewModel galleryViewModel;
    Button dateselect;
    static final int DIALOG_ID = 0;
    int year_x, month_x, day_x;
    String dateFinal;

    String selectedDate;
    public static final int REQUEST_CODE = 11; // Used to identify the result

    //    private OnFragmentInteractionListener mListener;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        galleryViewModel =
                ViewModelProviders.of(this).get(GalleryViewModel.class);
        View root = inflater.inflate(R.layout.fragment_gallery, container, false);
        final TextView textView = root.findViewById(R.id.text_gallery);
        galleryViewModel.getText().observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });

       dateselect=(Button) root.findViewById(R.id.dateselectorbutton);
       dateselect.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               Intent intent = new Intent(getActivity(), DayView.class);
               startActivity(intent);
           }
       });



        return root;
    }
    @Override
    public void onResume(){
        super .onResume();
        dateselect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), DayView.class);
                startActivity(intent);
            }
        });


    }


}




