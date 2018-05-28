package com.example.pr_idi.mydatabaseexample;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;

import java.util.List;

public class DrawerActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, SearchFragment.OnFragmentInteractionListener {

    private FilmData filmData;
    private ListView Mylist;
    private List<Film> values;
    private Fragment fragment;
    private boolean FragmentTransaction = false;
    private boolean main_cont = false;

    String titol = null;
    String direct = null;
    String prot = null;
    String pays = null;
    int any = 0;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.navigation_drawer);

        filmData = new FilmData(this);
        filmData.open();

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(view.getContext(), crearFilm.class);
                i.putExtra("titol",titol);
                i.putExtra("direct",direct);
                i.putExtra("prot",prot);
                i.putExtra("pays",pays);
                i.putExtra("any",any);
                startActivityForResult(i,1);
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        main_cont = true;

        Fragment fragment = new MainActivity();
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.content_main, fragment)
                .commit();
        getSupportActionBar().setTitle(R.string.app_name);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == 1) {
            if(resultCode == crearFilm.RESULT_OK){

                titol = null;
                direct = null;
                prot = null;
                pays = null;
                any = 0;
            }
            if (resultCode == crearFilm.RESULT_CANCELED) {
                //Write your code if there's no result
                if (data != null) {
                    data.getExtras();
                    Bundle extras = data.getExtras();
                    titol = extras.getString("titol");
                    direct = extras.getString("direct");
                    prot = extras.getString("prot");
                    pays = extras.getString("pays");
                    any = extras.getInt("any", 0);
                    AlertDialog.Builder builder = new AlertDialog.Builder(this);
                    builder.setTitle("Atención")
                            .setMessage("No has introducido ninguna película," +
                                    " si aún lo deseas puedes agregar una película, tus datos estan guardados. \n");
                    builder.setPositiveButton("Volver",

                            new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                }
                            });
                    builder.create().show();
                }
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.menu_search) {
            main_cont = false;
            Fragment fragment = new SearchFragment();
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.content_main, fragment)
                    .commit();

            item.setChecked(true);
            if (id != R.id.main) getSupportActionBar().setTitle(item.getTitle());

        }
        if (id == R.id.action_about) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);

            builder.setTitle("ABOUT")
                    .setMessage("Esta aplicación ha sido diseñada para poder " +
                            "gestionar tus películas de forma más fácil y adecuada. " +
                            "\n\nEsta app forma parte de la assignatura de IDI de la FIB." +
                            "\n\nAutores: Carlos Alvarado y Enrique Reyes\n\nVersión: 1.0")
                    .setPositiveButton("OK",
                            new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                }
                            });
            builder.create().show();
        }
        if (id == R.id.action_help1) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);

            builder.setTitle(R.string.action_help1)
                    .setMessage("Pulsando el botón + del lado inferior derecho de la pantalla podrás añadir un nuevo film a la aplicación, "+
                            "también puedes acceder a través de la opción una \"Nueva Película\" del menú lateral desplegable.\n" +
                            "En la pantalla podrás introducir los datos necesarios para añadir la nueva película. " +
                            "Ten presente que tienes que rellenar todos los espacios, en caso de cerrar la ventana tus datos quedarán almacenados " +
                            "hasta que confirmes (botón ACEPTAR).")

                    .setPositiveButton("OK",
                            new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                }
                            });

            builder.create().show();
        }
        if (id == R.id.action_help2) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);

            builder.setTitle(R.string.action_help2)
                    .setMessage("Pulsando el icono de buscar de la barra superior de la pantalla, podrás acceder a una ventana para poder buscar " +
                            "la filmografia de un actor en concreto. También puedes acceder a través de la opción \"Buscar por actor\" del menú lateral desplegable.")

                    .setPositiveButton("OK",
                            new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                }
                            });

            builder.create().show();
        }

        if (id == R.id.action_help3) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);

            builder.setTitle(R.string.action_help3)
                    .setMessage("Pulsando en la opción \"Título\" dentro de \"Visualización de las películas\" del menú lateral despegable, " +
                            "podrás acceder a la lista de todas la películas almacenadas y ordenadas alfabéticamente por título (vista principal).")

                    .setPositiveButton("OK",
                            new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                }
                            });

            builder.create().show();
        }

        if (id == R.id.action_help4) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);

            builder.setTitle(R.string.action_help4)
                    .setMessage("Pulsando en la opción \"Año de estreno\" dentro de \"Visualización de las películas\" del menú lateral despegable, " +
                            "podrás acceder a la lista de todas la películas almacenadas y ordenadas por año de estreno (vista avanzada).")

                    .setPositiveButton("OK",
                            new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                }
                            });

            builder.create().show();
        }

        if (id == R.id.action_help5) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);

            builder.setTitle(R.string.action_help5)
                    .setMessage("Al seleccionar una película (clicando en la vista simple o manteniendo pulsado en la vista avanzada) podremos " +
                            "modificar la nota de la película y eliminarla. Addicionalmente en la vista simple, podrás obtener el resto de datos de la película.")

                    .setPositiveButton("OK",
                            new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                }
                            });

            builder.create().show();
        }


        return super.onOptionsItemSelected(item);
    }


    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        Fragment fragment = null;
        boolean FragmentTransaction = false;

        if (id == R.id.add_new) {
            main_cont = false;

            Intent i = new Intent(this, crearFilm.class);
            i.putExtra("titol",titol);
            i.putExtra("direct",direct);
            i.putExtra("prot",prot);
            i.putExtra("pays",pays);
            i.putExtra("any",any);
            startActivityForResult(i,1);


        } else if (id == R.id.recycler) {
            fragment = new RecyclerFragment();
            FragmentTransaction = true;
            main_cont = false;

        } else if (id == R.id.search_actor) {
            fragment = new SearchFragment();
            FragmentTransaction = true;
            main_cont = false;

        } else if (id == R.id.main) {
            fragment = new MainActivity();
            FragmentTransaction = true;
            main_cont = true;

        }


        if(FragmentTransaction){
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.content_main, fragment)
                    .commit();

            item.setChecked(true);
            if (id != R.id.main)getSupportActionBar().setTitle(item.getTitle());
            else getSupportActionBar().setTitle(R.string.app_name);

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }

    @Override
    public void onResume() {
        filmData.open();
        super.onResume();
    }

    @Override
    public void onPause() {
        filmData.close();
        super.onPause();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
    }


}
