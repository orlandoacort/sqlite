package com.example.alberto.directoriomedico.addeditmedico;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.example.alberto.directoriomedico.MainActivity;
import com.example.alberto.directoriomedico.R;

public class AddEditMedicoActivity extends AppCompatActivity {

    public static final int REQUEST_ADD_MEDICO = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_edit_medico);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        String medicoId = getIntent().getStringExtra(MainActivity.EXTRA_MEDICO_ID);

        setTitle(medicoId == null? "Añadir":"Editar");

        AddEditMedicoFragment fragment = (AddEditMedicoFragment)getSupportFragmentManager().findFragmentById(R.id.add_edit_medico_container);

        if(fragment == null){
            fragment = AddEditMedicoFragment.newInstance(medicoId);
            getSupportFragmentManager()
                    .beginTransaction()
                    .add(R.id.add_edit_medico_container,fragment)
                    .commit();
        }


        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }

    @Override
    public boolean onSupportNavigateUp(){
        onBackPressed();
        return true;
    }

}
