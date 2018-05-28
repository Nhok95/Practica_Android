package com.example.pr_idi.mydatabaseexample;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Vector;

/**
 * Created by lfs on 14/12/16.
 */
public class Adaptador extends RecyclerView.Adapter<Adaptador.ViewHolder> {
    //Esta clase configura cada una de las vistas a partir de los datos.
    private Context mContext;
    private LayoutInflater inflador;
    protected Vector<Film> vectorFilms;
    private EditText new_critic;

    public Adaptador(Context contexto, Vector<Film> films) {
        inflador = (LayoutInflater) contexto.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.mContext = contexto;
        this.vectorFilms = films;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        //creamos la vista sin personalizar
        View v = inflador.inflate(R.layout.elemento, null);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        //personalizamos el viewholder
        final Film film = vectorFilms.elementAt(position);
        holder.Title.setText(film.getTitle());
        holder.director.setText(film.getDirector());
        holder.año.setText(String.valueOf(film.getYear()));
        holder.actor.setText(film.getProtagonist());
        holder.pais.setText(film.getCountry());
        if (film.getCritics_rate() <= 0) holder.nota.setText("Falta votar");
        else {
            holder.nota.setText(String.valueOf((film.getCritics_rate())));
        }
        holder.setClickListener(new RecyclerFragment.ItemClickListener() {
            @Override
            public void onClick(final View view, final int position, boolean isLongClick) {
                if (isLongClick) {
                    //Toast.makeText(mContext, "#" + position + " - " + vectorFilms.elementAt(position) + " (Long click)", Toast.LENGTH_SHORT).show();
                    final String[] items = {"Valorar", "Borrar"};
                    AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
                    builder.setTitle("Opciones para " + film.getTitle())
                            .setItems(items, new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    // The 'which' argument contains the index position
                                    // of the selected item
                                    if (which == 0) {
                                        AlertDialog.Builder builder2 = new AlertDialog.Builder(mContext);
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
                                                FilmData filmData = new FilmData(mContext);

                                                if (crit <= 10 && crit >0) {
                                                    filmData.open();
                                                    filmData.setFilmCritic(film,crit);
                                                    Toast.makeText(mContext, "Nota de " + film.getTitle() + " modificada", Toast.LENGTH_SHORT).show();
                                                    notifyItemChanged(position);
                                                }
                                                else Toast.makeText(mContext, "Nota incorrecta", Toast.LENGTH_SHORT).show();
                                                filmData.close();

                                            }
                                        });
                                    }
                                    if (which == 1) {
                                        AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
                                        builder.setTitle("Estas seguro de borrar " +film.getTitle() + "?");
                                        builder.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                                            public void onClick(DialogInterface dialog, int id) {
                                                // User clicked OK button
                                                FilmData filmData = new FilmData(mContext);
                                                filmData.open();
                                                Snackbar.make(view, "Se ha eliminado " + film.getTitle(), Snackbar.LENGTH_LONG).show();
                                                filmData.deleteFilm(film);
                                                filmData.close();
                                                vectorFilms.remove(position);
                                                notifyItemRemoved(position);
                                            }
                                        });
                                        builder.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                                            public void onClick(DialogInterface dialog, int id) {
                                                // User cancelled the dialog
                                            }
                                        });
                                        AlertDialog alert = builder.create();
                                        alert.show();
                                    }
                                }
                            });
                    builder.create().show();
                } else {
                    //Toast.makeText(mContext, "#" + position + " - " + vectorFilms.elementAt(position), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public void updateData(Vector<Film> films) {
        this.vectorFilms = films;
        notifyDataSetChanged();
    }

    public void removeItem(int position) {
        vectorFilms.remove(position);
        notifyItemRemoved(position);
    }

    @Override
    public int getItemCount() {
        //indicamos el numero de elementos de la lista
        return vectorFilms.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener {
        public TextView Title;
        public TextView director;
        public TextView año;
        public TextView actor;
        public TextView pais;
        public TextView nota;


        private RecyclerFragment.ItemClickListener clickListener;
        public ViewHolder(final View itemView) {
            super(itemView);
            Title = (TextView) itemView.findViewById(R.id.txtRowTitle);
            director = (TextView) itemView.findViewById(R.id.txtRowDesc);
            año = (TextView) itemView.findViewById(R.id.year);
            actor = (TextView) itemView.findViewById(R.id.actor);
            pais = (TextView) itemView.findViewById(R.id.País);
            nota = (TextView) itemView.findViewById(R.id.nota);
            itemView.setTag(itemView);
            itemView.setOnClickListener(this);
            itemView.setOnLongClickListener(this);
        }
        public void setClickListener(RecyclerFragment.ItemClickListener itemClickListener) {
            this.clickListener = itemClickListener;
        }
        @Override
        public void onClick(View view) {
            clickListener.onClick(view, getPosition(), false);
        }
        @Override
        public boolean onLongClick(View view) {
            clickListener.onClick(view, getPosition(), true);
            return true;
        }
    }
}