package com.example.pr_idi.mydatabaseexample;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.Comparator;
import java.util.List;

public class MainActivity extends Fragment {
    private MainActivity.OnFragmentInteractionListener mListener;
    private FilmData filmData;
    private ListView Mylist;
    private EditText new_critic;
    private Film film;
    private List<Film> values;
    private View view;

    private ArrayAdapter<Film> adapter;

    public MainActivity() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        super.onCreateView(inflater, container, savedInstanceState);

        view = inflater.inflate(R.layout.listview, container, false);
        if (view != null) {

            filmData = new FilmData(getActivity());
            llista();

            Mylist.setOnItemClickListener(new OnItemClickListener() {
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());


                public void onItemClick(AdapterView<?> parent, final View view,
                                        int position, long id) {

                    film = (Film) Mylist.getItemAtPosition(position);


                    builder.setTitle("Opciones para "+ film.getTitle());
                    builder.setItems(R.array.opciones, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            if(which == 0) {
                                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

                                builder.setTitle("Detalles de " +film.getTitle())
                                        .setMessage(" Director: "+ film.getDirector() + "\n" +
                                                " País: " + film.getCountry() + "\n" +
                                                " Año: " + film.getYear() + "\n" +
                                                " Protagonista: " + film.getProtagonist() + "\n" +
                                                " Valoración: " + film.getCritics_rate())

                                        .setPositiveButton("OK",
                                                new DialogInterface.OnClickListener() {
                                                    @Override
                                                    public void onClick(DialogInterface dialog, int which) {
                                                    }
                                                });

                                builder.create().show();
                            }

                            else if (which == 1) {
                                AlertDialog.Builder builder2 = new AlertDialog.Builder(getActivity());
                                builder2.setView(R.layout.dialog_mod);
                                builder2.setTitle("Nueva nota");
                                final Dialog dialog2 = builder2.create();
                                dialog2.show();

                                new_critic = (EditText) dialog2.findViewById(R.id.new_critic);

                                ((Button) dialog2.findViewById(R.id.modificar)).setOnClickListener(new View.OnClickListener() {

                                    @Override
                                    public void onClick(View view) {
                                        dialog2.dismiss();

                                        int crit = Integer.parseInt(new_critic.getText().toString());

                                        if (crit <= 10 && crit >0) {
                                            filmData.setFilmCritic(film,crit);
                                            Mylist.setAdapter(adapter);
                                            Toast.makeText(getActivity(), "Nota de " + film.getTitle() + " modificada", Toast.LENGTH_SHORT).show();
                                        } else Toast.makeText(getActivity(), "Nota incorrecta", Toast.LENGTH_SHORT).show();

                                    }
                                });
                            }
                            else if (which == 2) {
                                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                                builder.setTitle("Estas seguro de borrar " +film.getTitle() + "?");
                                builder.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        // User clicked OK button
                                        Snackbar.make(view, "Se ha eliminado " + film.getTitle(), Snackbar.LENGTH_LONG).show();
                                        filmData.deleteFilm(film);
                                        adapter.remove(film);
                                    }
                                });
                                builder.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        Snackbar.make(view, "Cancelado", Snackbar.LENGTH_LONG).show();
                                    }
                                });
                                AlertDialog alert = builder.create();
                                alert.show();

                            }

                        }
                    });
                    Dialog dialog = builder.create();
                    dialog.show();

                }
            });

        }
        adapter.notifyDataSetChanged();

        return view;

    }

    public void llista() {
        filmData.open();
        values = filmData.getAllFilmslist();


        Mylist = (ListView) view.findViewById(R.id.list);

        adapter = new ArrayAdapter<>(getActivity(),
                android.R.layout.simple_list_item_1, values);

        adapter.sort(new Comparator<Film>() {
            @Override
            public int compare(Film f1, Film f2) {
                return f1.getTitle().compareTo(f2.getTitle());
            }

            @Override
            public boolean equals(Object object) {
                return false;
            }
        });

        Mylist.setAdapter(adapter);
    }


    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Mylist.setAdapter(adapter);


    }

    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    public void onAttach(Context context) {
        super.onAttach((Activity) context);
        if (context instanceof MainActivity.OnFragmentInteractionListener) {
            mListener = (MainActivity.OnFragmentInteractionListener) context;
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
