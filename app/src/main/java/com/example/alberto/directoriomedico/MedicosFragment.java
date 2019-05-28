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
import android.widget.ListView;
import android.widget.Toast;

import com.example.alberto.directoriomedico.addeditmedico.AddEditMedicoActivity;
import com.example.alberto.directoriomedico.datos.Medicos.MedicosContract;
import com.example.alberto.directoriomedico.datos.Medicos.MedicosCursorAdapter;
import com.example.alberto.directoriomedico.datos.Medicos.MedicosDbHelper;
import com.example.alberto.directoriomedico.medicodetail.MedicoDetailActivity;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link MedicosFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MedicosFragment extends Fragment {

    ListView mMedicosList;
    FloatingActionButton mAddButton;
    MedicosCursorAdapter mMedicosAdapter;
    MedicosDbHelper mMedicosDBHelper;

    public static final int REQUEST_UPDATE_DELETE_MEDICO = 2;


    public MedicosFragment() {
        // Required empty public constructor
    }

    public static MedicosFragment newInstance(){return new MedicosFragment();}




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_medicos, container, false);

        mMedicosList = (ListView)root.findViewById(R.id.medicos_list);
        mMedicosAdapter = new MedicosCursorAdapter(getActivity(),null,0);
        mAddButton = (FloatingActionButton)getActivity().findViewById(R.id.fab);

        mAddButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showAddScreen();
            }
        });

        mMedicosList.setAdapter(mMedicosAdapter);

        mMedicosDBHelper = new MedicosDbHelper(getActivity());

        mMedicosList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Cursor currentItem = (Cursor)mMedicosAdapter.getItem(position);
                String currentMedicoId = currentItem.getString(currentItem.getColumnIndex(MedicosContract.MedicoEntry.ID));
                showDetailScreen(currentMedicoId);
            }
        });


        loadMedicos();

        return root;
    }

    private void showAddScreen(){
        Intent intent = new Intent(getActivity(),AddEditMedicoActivity.class);
        startActivityForResult(intent,AddEditMedicoActivity.REQUEST_ADD_MEDICO);
    }

    private void showDetailScreen(String medicoId){
        Intent intent = new Intent(getActivity(), MedicoDetailActivity.class);
        intent.putExtra(MainActivity.EXTRA_MEDICO_ID,medicoId);
        startActivityForResult(intent,REQUEST_UPDATE_DELETE_MEDICO);
    }

    private void loadMedicos(){
        new MedicosLoadTask().execute();
    }

    private class MedicosLoadTask extends AsyncTask<Void,Void,Cursor>{


        @Override
        protected Cursor doInBackground(Void... params) {
            return mMedicosDBHelper.getAllMedicos();
        }

        @Override
        protected void onPostExecute(Cursor c){
            if(c!=null && c.getCount()>0){
                mMedicosAdapter.swapCursor(c);
            }else{

            }
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(Activity.RESULT_OK == resultCode){
            switch(requestCode){
                case AddEditMedicoActivity.REQUEST_ADD_MEDICO:
                    showSuccessfullSavedMessage();
                    loadMedicos();
                    break;
                case REQUEST_UPDATE_DELETE_MEDICO:
                    loadMedicos();
                    break;

            }
        }
    }

    private void showSuccessfullSavedMessage(){
        Toast.makeText(getActivity(),"Médico guardado correctamente",Toast.LENGTH_SHORT).show();
    }

}
