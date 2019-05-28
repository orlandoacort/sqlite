package com.example.alberto.directoriomedico;


import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.example.alberto.directoriomedico.addeditpaciente.AddEditPacienteActivity;
import com.example.alberto.directoriomedico.datos.Pacientes.PacientesContract;
import com.example.alberto.directoriomedico.datos.Pacientes.PacientesCursorAdapter;
import com.example.alberto.directoriomedico.datos.Pacientes.PacientesDbHelper;


/**
 * A simple {@link Fragment} subclass.
 */
public class PacientesFragment extends Fragment {

    ListView mPacientesList;
   // Button btnEditP, btnDeleteP;
    FloatingActionButton mAddButton;
    private String mPacienteId;
    static String mIdMedico = "";
    PacientesCursorAdapter mPacientesAdapter;
    PacientesDbHelper mPacientesHelper;

    public static final int REQUEST_UPDATE_DELETE_PACIENTE = 2;

    public PacientesFragment() {
        // Required empty public constructor
    }

    public static PacientesFragment newInstance(String idMedico){
        mIdMedico = idMedico;
        return new PacientesFragment();}



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_pacientes, container, false);

        mPacientesList = (ListView)root.findViewById(R.id.pacientes_list);
        mPacientesAdapter = new PacientesCursorAdapter(getActivity(),null,0);

      //  mAddButton = (FloatingActionButton)getActivity().findViewById(R.id.fab);

       /* mAddButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showAddScreen();
            }
        });*/

        mPacientesList.setAdapter(mPacientesAdapter);

        mPacientesHelper = new PacientesDbHelper(getActivity());

       /* btnEditP = (Button)root.findViewById(R.id.btnEditP);

        btnEditP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //showEditScreen();
                Toast.makeText(getActivity(), "editar", Toast.LENGTH_SHORT).show();
            }
        });*/

        mPacientesList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Cursor currentItem = (Cursor)mPacientesAdapter.getItem(position);
                String currentPacienteId = currentItem.getString(currentItem.getColumnIndex(PacientesContract.PacienteEntry.ID));


                // showDetailScreen(currentPacienteId);
            }
        });


        loadPacientes();

        return root;


    }
    private void showEditScreen(){
        Intent intent = new Intent(getActivity(),AddEditPacienteActivity.class);
        intent.putExtra(MainActivity.EXTRA_PACIENTE_ID,mPacienteId);
        startActivityForResult(intent, REQUEST_UPDATE_DELETE_PACIENTE);
    }

    private void showAddScreen(){
        Intent intent = new Intent(getActivity(), AddEditPacienteActivity.class);
        startActivityForResult(intent,AddEditPacienteActivity.REQUEST_ADD_PACIENTE);
    }

  /*  private void showDetailScreen(String pacienteId){
        Intent intent = new Intent(getActivity(), MedicoDetailActivity.class);
        intent.putExtra(MainActivity.EXTRA_MEDICO_ID,medicoId);
        startActivityForResult(intent,REQUEST_UPDATE_DELETE_MEDICO);
    }*/

    private void loadPacientes(){
        new PacientesLoadTask().execute();
    }

    private class PacientesLoadTask extends AsyncTask<Void,Void,Cursor> {


        @Override
        protected Cursor doInBackground(Void... params) {
            return mPacientesHelper.getPacientesByMedico(mIdMedico);
        }

        @Override
        protected void onPostExecute(Cursor c){
            if(c!=null && c.getCount()>0){
                mPacientesAdapter.swapCursor(c);
            }else{

            }
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(Activity.RESULT_OK == resultCode){
            switch(requestCode){
                case AddEditPacienteActivity.REQUEST_ADD_PACIENTE:
                    showSuccessfullSavedMessage();
                    loadPacientes();
                    break;
                case REQUEST_UPDATE_DELETE_PACIENTE:
                    loadPacientes();
                    break;

            }
        }
    }
    private void showSuccessfullSavedMessage(){
        Toast.makeText(getActivity(),"Paciiente guardado correctamente",Toast.LENGTH_SHORT).show();
    }


}
