package com.example.alberto.directoriomedico.medicodetail;

import android.app.Activity;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;

import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.alberto.directoriomedico.MainActivity;
import com.example.alberto.directoriomedico.MedicosFragment;
import com.example.alberto.directoriomedico.PacienteActivity;
import com.example.alberto.directoriomedico.R;
import com.example.alberto.directoriomedico.addeditmedico.AddEditMedicoActivity;
import com.example.alberto.directoriomedico.addeditpaciente.AddEditPacienteActivity;
import com.example.alberto.directoriomedico.datos.Medicos.Medicos;
import com.example.alberto.directoriomedico.datos.Medicos.MedicosDbHelper;


public class MedicoDetailFragment extends Fragment {

    private static final String ARG_MEDICO_ID = "medicoId";
    private String mMedicoId;
    private String mMedicoNombre;

    private CollapsingToolbarLayout mCollapsingView;
    private ImageView mAvatar;
    private TextView mPhoneNumber;
    private TextView mSpecialty;
    private TextView mBio;

    private MedicosDbHelper mMedicosDBHelper;






    public MedicoDetailFragment() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static MedicoDetailFragment newInstance(String medicoId) {
        MedicoDetailFragment fragment = new MedicoDetailFragment();
        Bundle args = new Bundle();
        args.putString(ARG_MEDICO_ID,medicoId);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if(getArguments() != null){
            mMedicoId = getArguments().getString(ARG_MEDICO_ID);
        }

        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_medico_detail, container, false);
        mCollapsingView = (CollapsingToolbarLayout)getActivity().findViewById(R.id.toolbar_layout);
        mAvatar = (ImageView)getActivity().findViewById(R.id.iv_avatar);
        mPhoneNumber = (TextView)root.findViewById(R.id.tv_phone_number);
        mSpecialty = (TextView)root.findViewById(R.id.tv_specialty);
        mBio = (TextView)root.findViewById(R.id.tv_bio);
        mMedicosDBHelper = new MedicosDbHelper(getActivity());
        Button btnPacientes = (Button)root.findViewById(R.id.btnListaP);

        btnPacientes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loadPaciente();
            }
        });

        loadMedico();
        return root;
    }

    private void loadPaciente() {

        Intent intent = new Intent(getActivity(), PacienteActivity.class);
        intent.putExtra(MainActivity.EXTRA_MEDICO_ID,mMedicoId);
        //startActivityForResult(intent, MedicosFragment.REQUEST_UPDATE_DELETE_MEDICO);
        startActivity(intent);

    }

    private void showLoadError(){
        Toast.makeText(getActivity(),"Error al cargar información",Toast.LENGTH_LONG).show();
    }

    private void showMedico(Medicos medicos){
        mCollapsingView.setTitle(medicos.getName());
        Glide.with(this)
                .load(Uri.parse("file:///android_asset/"+medicos.getAvatarUri()))
                .centerCrop()
                .into(mAvatar);
        mPhoneNumber.setText(medicos.getPhoneNumber());
        mSpecialty.setText(medicos.getSpeciality());
        mBio.setText(medicos.getBio());
    }

    private class GetMedicoByIdTask extends AsyncTask<Void,Void,Cursor>{

        @Override
        protected Cursor doInBackground(Void... params) {
            return mMedicosDBHelper.getMedicoById(mMedicoId);
        }
        @Override
        protected void onPostExecute(Cursor c){
            if(c!= null && c.moveToLast()){
                showMedico(new Medicos(c));
            }else{
                showLoadError();
            }
        }
    }

    private void loadMedico(){
        new GetMedicoByIdTask().execute();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data){
        if(requestCode == MedicosFragment.REQUEST_UPDATE_DELETE_MEDICO){
            if(resultCode == Activity.RESULT_OK){
                getActivity().setResult(Activity.RESULT_OK);
                getActivity().finish();
            }
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        switch (item.getItemId()){
            case R.id.action_edit:
                showEditScreen();
                break;
            case R.id.action_delete:
                new DeleteMedicoTask().execute();
                break;
            case R.id.action_addpaciente:
                showAddPaciente();
                break;
        }
        return  super.onOptionsItemSelected(item);
    }

    private void showAddPaciente() {
        Intent intent = new Intent(getActivity(), AddEditPacienteActivity.class);
        intent.putExtra(MainActivity.EXTRA_MEDICO_ID,mMedicoId);
        //startActivityForResult(intent, MedicosFragment.REQUEST_UPDATE_DELETE_MEDICO);
        startActivity(intent);
    }

    private void showDeleteError(){
        Toast.makeText(getActivity(),"Error al eliminar Médico", Toast.LENGTH_SHORT).show();
    }

    private void showMedicoScreen(boolean requery){
        if(!requery){
            showDeleteError();
        }
        getActivity().setResult(requery? Activity.RESULT_OK:Activity.RESULT_CANCELED);
        getActivity().finish();
    }

    private class DeleteMedicoTask extends AsyncTask<Void,Void,Integer>{

        @Override
        protected Integer doInBackground(Void... params) {
            return mMedicosDBHelper.deleteMedico(mMedicoId);
        }
        @Override
        protected void onPostExecute(Integer integer){
            showMedicoScreen(integer>0);
        }
    }

    private void showEditScreen(){
        Intent intent = new Intent(getActivity(),AddEditMedicoActivity.class);
        intent.putExtra(MainActivity.EXTRA_MEDICO_ID,mMedicoId);
        startActivityForResult(intent, MedicosFragment.REQUEST_UPDATE_DELETE_MEDICO);
    }


}
