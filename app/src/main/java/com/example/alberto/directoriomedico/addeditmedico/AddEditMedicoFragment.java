package com.example.alberto.directoriomedico.addeditmedico;


import android.app.Activity;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.alberto.directoriomedico.R;
import com.example.alberto.directoriomedico.datos.Medicos.Medicos;
import com.example.alberto.directoriomedico.datos.Medicos.MedicosDbHelper;

/**
 * A simple {@link Fragment} subclass.
 */
public class AddEditMedicoFragment extends Fragment {

    private static final String ARG_MEDICO_ID = "arg_medico_id";
    private String mMedicoId;
    private MedicosDbHelper mMedicosDBHelper;
    private FloatingActionButton mSaveButton;
    private TextInputEditText mNameField;
    private TextInputEditText mPhoneNumberField;
    private TextInputEditText mSpecialtyField;
    private TextInputEditText mBioField;
    private TextInputLayout mNameLabel;
    private TextInputLayout mPhoneNumberLabel;
    private TextInputLayout mSpecialtyLabel;
    private TextInputLayout mBioLabel;




    public AddEditMedicoFragment() {
        // Required empty public constructor
    }

    public static AddEditMedicoFragment newInstance(String medicoId){
        AddEditMedicoFragment fragment = new AddEditMedicoFragment();
        Bundle args = new Bundle();
        args.putString(ARG_MEDICO_ID,medicoId);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        if(getArguments()!=null){
            mMedicoId = getArguments().getString(ARG_MEDICO_ID);
        }
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_add_edit_medico, container, false);

        mSaveButton = (FloatingActionButton)getActivity().findViewById(R.id.fab);
        mNameLabel = (TextInputLayout)root.findViewById(R.id.til_name);
        mNameField = (TextInputEditText)root.findViewById(R.id.et_name);
        mPhoneNumberLabel = (TextInputLayout)root.findViewById(R.id.til_phone_number);
        mPhoneNumberField = (TextInputEditText)root.findViewById(R.id.et_phone_number);
        mSpecialtyLabel = (TextInputLayout)root.findViewById(R.id.til_specialty);
        mSpecialtyField = (TextInputEditText)root.findViewById(R.id.et_specialty);
        mBioLabel = (TextInputLayout)root.findViewById(R.id.til_bio);
        mBioField = (TextInputEditText)root.findViewById(R.id.et_bio);

        mSaveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addEditMedico();
            }
        });

        mMedicosDBHelper = new MedicosDbHelper(getActivity());

        if(mMedicoId != null) {
            loadMedico();
        }



        return root;
    }

    private void loadMedico(){
        new GetMedicoByIdTask().execute();
    }

    private void showMedico(Medicos medicos){
        mNameField.setText(medicos.getName());
        mPhoneNumberField.setText(medicos.getPhoneNumber());
        mSpecialtyField.setText(medicos.getSpeciality());
        mBioField.setText(medicos.getBio());
    }

    private void showLoadError(){
        Toast.makeText(getActivity(),"Error al editar",Toast.LENGTH_SHORT).show();

    }

    private class GetMedicoByIdTask extends AsyncTask<Void,Void,Cursor>{

        @Override
        protected Cursor doInBackground(Void... params) {
            return mMedicosDBHelper.getMedicoById(mMedicoId);
        }
        @Override
        protected void onPostExecute(Cursor c){
            if(c!=null && c.moveToLast()){
                showMedico(new Medicos(c));
            }else{
                showLoadError();
                getActivity().setResult(Activity.RESULT_CANCELED);
                getActivity().finish();
            }
        }
    }

    private class AddEditMedicoTask extends AsyncTask<Medicos,Void,Boolean>{

        @Override
        protected Boolean doInBackground(Medicos... params) {
            if(mMedicoId != null){
                return mMedicosDBHelper.updateMedico(params[0],mMedicoId)>0;
            }else{
                return mMedicosDBHelper.saveMedico(params[0])>0;
            }
        }

        @Override
        protected void onPostExecute(Boolean result){
            showMedicosScreen(result);
        }
    }

    private void showMedicosScreen(Boolean requery){
        if(!requery){
            showAddeditError();
            getActivity().setResult(Activity.RESULT_CANCELED);
        }else{
            getActivity().setResult(Activity.RESULT_OK);
        }
        getActivity().finish();
    }

    private void showAddeditError(){
        Toast.makeText(getActivity(),"error",Toast.LENGTH_SHORT).show();
    }

    private void addEditMedico(){
        boolean error = false;
        String name = mNameField.getText().toString();
        String phoneNumber = mPhoneNumberField.getText().toString();
        String specialty = mSpecialtyField.getText().toString();
        String bio = mBioField.getText().toString();

        if(TextUtils.isEmpty(name)){
            mNameLabel.setError(getString(R.string.field_error));
            error = true;
        }
        if(TextUtils.isEmpty(phoneNumber)){
            mPhoneNumberLabel.setError(getString(R.string.field_error));
            error = true;
        }
        if(TextUtils.isEmpty(specialty)){
            mSpecialtyLabel.setError(getString(R.string.field_error));
            error = true;
        }
        if(TextUtils.isEmpty(bio)){
            mBioLabel.setError(getString(R.string.field_error));
            error = true;
        }

        if(error){
            return;
        }
        Medicos medicos = new Medicos(name,specialty,phoneNumber,bio,"");
        new AddEditMedicoTask().execute(medicos);
    }

}
