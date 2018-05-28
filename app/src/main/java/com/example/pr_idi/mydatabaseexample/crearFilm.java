package com.example.pr_idi.mydatabaseexample;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class crearFilm extends AppCompatActivity {
    private Toolbar toolbar;

    String titol;
    String direct;
    String prot;
    String pays;
    String any;

    EditText titulo;
    EditText director;
    EditText protagonista;
    EditText pais;
    EditText ano;
    Button aceptar;
    Button cancelar;
    private FilmData filmData;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crear_film);
        toolbar = (Toolbar) findViewById(R.id.tool_bar);
        toolbar.setTitle(" Nuevo Film");
        toolbar.setTitleTextColor(getResources().getColor(R.color.white));
        setSupportActionBar(toolbar);
        // Get a support ActionBar corresponding to this toolbar
        ActionBar ab = getSupportActionBar();
        // Enable the Up button
        ab.setDisplayHomeAsUpEnabled(true);

        Intent intent = getIntent();
        titol = intent.getStringExtra("titol");
        direct = intent.getStringExtra("direct");
        prot = intent.getStringExtra("prot");
        pays = intent.getStringExtra("pays");
        int a = intent.getIntExtra("any",0);

        titulo = (EditText) findViewById(R.id.titulo);
        director = (EditText) findViewById(R.id.director);
        protagonista = (EditText) findViewById(R.id.protagonista);
        pais = (EditText) findViewById(R.id.pais);
        ano = (EditText) findViewById(R.id.ano);
        aceptar = (Button) findViewById(R.id.aceptar);
        cancelar = (Button) findViewById(R.id.cancelar);

        if (!(titol == null)) {
            titulo.setText(titol);
        }
        if (!(direct == null)) {
            director.setText(direct);
        }
        if (!(prot == null)) {
            protagonista.setText(prot);
        }
        if (!(pays == null)) {
            pais.setText(pays);
        }
        if (!(a == 0)) {
            ano.setText("");
        }

        filmData = new FilmData(this);
        filmData.open();

        aceptar.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                //Aqui faltaria verificar que los atributos sean correctos
                int aprobar = 0;
                if (titulo.length() == 0) {
                    Toast toast = Toast.makeText(crearFilm.this,"El titulo esta vacío", Toast.LENGTH_SHORT);
                    toast.show();
                    aprobar = 1;
                }
                if (director.length() == 0) {
                    Toast toast = Toast.makeText(crearFilm.this,"El director esta vacío", Toast.LENGTH_SHORT);
                    toast.show();
                    aprobar = 1;
                }
                if (protagonista.length() == 0) {
                    Toast toast = Toast.makeText(crearFilm.this,"El protagonista esta vacío", Toast.LENGTH_SHORT);
                    toast.show();
                    aprobar = 1;
                }
                if (pais.length() == 0) {
                    Toast toast = Toast.makeText(crearFilm.this,"El pais esta vacío", Toast.LENGTH_SHORT);
                    toast.show();
                    aprobar = 1;
                }
                if (ano.length() == 0) {
                    Toast toast = Toast.makeText(crearFilm.this,"El año esta vacío", Toast.LENGTH_SHORT);
                    toast.show();
                    aprobar = 1;
                }
                if (aprobar == 0) {
                    titol = titulo.getText().toString();
                    pays = pais.getText().toString();
                    any = ano.getText().toString();
                    int tercer = Integer.parseInt(any);
                    direct = director.getText().toString();
                    prot = protagonista.getText().toString();
                    int sexto = 0;
                    filmData.createFilm(titol,direct,tercer,prot,pays,sexto);
                    filmData.close();
                    Intent returnIntent = new Intent();
                    setResult(crearFilm.RESULT_OK, returnIntent);
                    finish();
                }

            }
        });
        cancelar.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                filmData.close();

                titol = titulo.getText().toString();
                pays = pais.getText().toString();
                any = ano.getText().toString();
                direct = director.getText().toString();
                prot = protagonista.getText().toString();


                Intent returnIntent = new Intent();
                returnIntent.putExtra("titol",titol);
                returnIntent.putExtra("direct",direct);
                returnIntent.putExtra("prot",prot);
                returnIntent.putExtra("pays",pays);

                if (!(ano.length() == 0)) returnIntent.putExtra("any", (String) any);

                setResult(crearFilm.RESULT_CANCELED, returnIntent);
                finish();
            }
        });
    }
}


/*package com.example.pr_idi.mydatabaseexample;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class crearFilm extends AppCompatActivity {
    private Toolbar toolbar;//Declaramos el Toolbar

    String titol;
    String direct;
    String prot;
    String pays;
    String any;

    EditText titulo;
    EditText director;
    EditText protagonista;
    EditText pais;
    EditText ano;
    Button aceptar;
    Button cancelar;
    private FilmData filmData;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crear_film);
        toolbar = (Toolbar) findViewById(R.id.tool_bar);
        toolbar.setTitle(" Nuevo Film");
        toolbar.setTitleTextColor(getResources().getColor(R.color.white));
        setSupportActionBar(toolbar);
        // Get a support ActionBar corresponding to this toolbar
        ActionBar ab = getSupportActionBar();
        // Enable the Up button
        ab.setDisplayHomeAsUpEnabled(true);

        Intent intent = getIntent();
        titol = intent.getStringExtra("titol");
        direct = intent.getStringExtra("titol");
        titol = intent.getStringExtra("titol");
        titol = intent.getStringExtra("titol");


        titulo = (EditText) findViewById(R.id.titulo);
        director = (EditText) findViewById(R.id.director);
        protagonista = (EditText) findViewById(R.id.protagonista);
        pais = (EditText) findViewById(R.id.pais);
        ano = (EditText) findViewById(R.id.ano);
        aceptar = (Button) findViewById(R.id.aceptar);
        cancelar = (Button) findViewById(R.id.cancelar);

        filmData = new FilmData(this);
        filmData.open();

        aceptar.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                //Aqui faltaria verificar que los atributos sean correctos
                if (titulo.length() == 0) {

                }
                String primer_campo = titulo.getText().toString();
                String segundo_campo = pais.getText().toString();
                String tercer_campo = ano.getText().toString();
                int tercer = Integer.parseInt(tercer_campo);
                String cuarto_campo = director.getText().toString();
                String quinto_campo = protagonista.getText().toString();
                int sexto = 0;
                filmData.createFilm(primer_campo,cuarto_campo,tercer,quinto_campo,segundo_campo,sexto);
                filmData.close();
                Intent returnIntent = new Intent();
                setResult(crearFilm.RESULT_OK, returnIntent);
                finish();
            }
        });
        cancelar.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                filmData.close();
                Intent returnIntent = new Intent();
                setResult(crearFilm.RESULT_CANCELED, returnIntent);
                finish();
            }
        });
    }
}*/
