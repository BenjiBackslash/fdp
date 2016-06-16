package com.fractureof.demos.location;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link PartnerEntryFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link PartnerEntryFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class PartnerEntryFragment extends Fragment {
    public static class Consts {
        public static String datingPartnerFmtNameTmpReplacement = "...";
        public static String ARG_PARTNER_FMT_NAME = "partnerFmtName";
    }
    private String mPartnerFmtName;

    private OnFragmentInteractionListener mListener;

    public PartnerEntryFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment PartnerEntryFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static PartnerEntryFragment newInstance(String partnerFmtName) {
        PartnerEntryFragment fragment = new PartnerEntryFragment();
        Bundle args = new Bundle();
        args.putString(Consts.ARG_PARTNER_FMT_NAME, partnerFmtName);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mPartnerFmtName = getArguments().getString(Consts.ARG_PARTNER_FMT_NAME);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_partner_entry, container, false);
    }

//    // TODO: Rename method, update argument and hook method into UI event
//    public void onButtonPressed(Uri uri) {
//        if (mListener != null) {
//            mListener.onFragmentInteraction(uri);
//        }
//    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
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
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
