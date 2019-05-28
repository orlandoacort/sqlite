package com.example.alberto.directoriomedico;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class PacienteActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_paciente);

        PacientesFragment fragment = (PacientesFragment)
                getSupportFragmentManager().findFragmentById(R.id.pacientes_container);
        Intent intent = getIntent();
        String idMedico = intent.getStringExtra(MainActivity.EXTRA_MEDICO_ID);
        if(fragment == null){
            fragment = PacientesFragment.newInstance(idMedico);
            getSupportFragmentManager()
                    .beginTransaction()
                    .add(R.id.pacientes_container,fragment)
                    .commit();
        }

    }
}
