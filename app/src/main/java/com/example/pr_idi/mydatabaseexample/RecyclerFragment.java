package com.example.pr_idi.mydatabaseexample;

import android.app.Activity;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.Vector;


public class RecyclerFragment extends Fragment {

    private OnFragmentInteractionListener mListener;
    private FilmData filmData;

    public RecyclerView recyclerView;//una instancia de el recyclerview
    public RecyclerView.LayoutManager layoutManager;//* manejador de layout
    public RecyclerView.Adapter adapter;
    public Vector<Film> vectorFilms;

    private View view;

    public RecyclerFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate (Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        filmData = new FilmData(getActivity());
        filmData.open();

    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        view = inflater.inflate(R.layout.fragment_recycler, container, false);
        if (view != null) {
            filmData = new FilmData(getActivity());
            vectorFilms = new Vector<>();
            llista();
        }
        return view;
    }

    public void llista() {
        filmData.open();
        vectorFilms = filmData.getAllFilms();
        recyclerView = (RecyclerView) view.findViewById(R.id.RecView) ;//definimos el identificador
        recyclerView.addItemDecoration(new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL_LIST));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        adapter = new Adaptador(getActivity(),vectorFilms);
        adapter.notifyDataSetChanged();
        recyclerView.setAdapter(adapter);//definir datos
        layoutManager = new LinearLayoutManager(getActivity());//*manejador de layout
        recyclerView.setLayoutManager(layoutManager);//*manejador de layout

        registerForContextMenu(recyclerView);
    }

    public interface ItemClickListener {
        void onClick(View view, int position, boolean isLongClick);
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    public void onAttach(Context context) {
        super.onAttach((Activity) context);
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

    @Override
    public void onResume() {
        llista();
        super.onResume();

    }

    public interface OnFragmentInteractionListener {
        void onFragmentInteraction(Uri uri);
    }
}
