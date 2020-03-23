package com.example.android_lab06;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link DetailsFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 */
public class DetailsFragment extends Fragment {

    private OnFragmentInteractionListener mListener;

    private TextView tv1;
    private TextView tv2;
    private CheckBox chbx;

    private AppCompatActivity parentActivity;
    public DetailsFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = getLayoutInflater().inflate(R.layout.fragment_details, container, false);

        tv1 = view.findViewById(R.id.editTextfrag);
        tv1.setText(getArguments().getString("message"));

        tv2 = view.findViewById(R.id.idGoesHere);
        tv2.setText(Long.toString(getArguments().getLong("id")));

        chbx = view.findViewById(R.id.checkBoxFrag);
        chbx.setChecked(getArguments().getBoolean("isSent"));

        Button removeBtn = view.findViewById(R.id.removeBtn);
        removeBtn.setOnClickListener( click ->{
            parentActivity.getSupportFragmentManager().beginTransaction().remove(this).commit();
            if (getActivity().getClass().getSimpleName().equals("EmptyActivity")) getActivity().finish();
        });
        return view;
    }

    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        }

        parentActivity = (AppCompatActivity)context;

//        } else {
//            throw new RuntimeException(context.toString()
//                    + " must implement OnFragmentInteractionListener");
//        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        void onFragmentInteraction(Uri uri);
    }
}