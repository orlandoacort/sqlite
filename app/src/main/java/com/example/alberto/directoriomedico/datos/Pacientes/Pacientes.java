package com.example.alberto.directoriomedico.datos.Pacientes;

import android.content.ContentValues;
import android.database.Cursor;

import com.example.alberto.directoriomedico.datos.Medicos.MedicosContract;

import java.util.UUID;

public class Pacientes {

    private String id;
    private String name;
    private String phoneNumber;
    private String history;
    private String avatarUri;
    private String idmedico;

    public Pacientes( String name, String phoneNumber, String history, String avatarUri, String idmedico) {
        this.id = UUID.randomUUID().toString();
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.history = history;
        this.avatarUri = avatarUri;
        this.idmedico = idmedico;
    }

   public Pacientes(Cursor cursor){  // SE USA CLASE PACIENTESCONTRACT
        id = cursor.getString(cursor.getColumnIndex
                (PacientesContract.PacienteEntry.ID));
        name = cursor.getString(cursor.getColumnIndex
                (PacientesContract.PacienteEntry.NAME));
        phoneNumber = cursor.getString(cursor.getColumnIndex
                (PacientesContract.PacienteEntry.PHONE_NUMBER));
        history = cursor.getString(cursor.getColumnIndex
                (PacientesContract.PacienteEntry.HISTORY));
        avatarUri = cursor.getString(cursor.getColumnIndex
                (PacientesContract.PacienteEntry.AVATAR_URI));
         idmedico = cursor.getString(cursor.getColumnIndex
               (PacientesContract.PacienteEntry.IDMEDICO));
    }

    public ContentValues toContentValues(){
        ContentValues values = new ContentValues();
        values.put(PacientesContract.PacienteEntry.ID,id);
        values.put(PacientesContract.PacienteEntry.NAME,name);
        values.put(MedicosContract.MedicoEntry.PHONE_NUMBER
                ,phoneNumber);
        values.put(PacientesContract.PacienteEntry.HISTORY,history);
        values.put(PacientesContract.PacienteEntry.AVATAR_URI
                ,avatarUri);
        values.put(PacientesContract.PacienteEntry.IDMEDICO
                ,idmedico);
        return values;
    }



    public String getId() {
        return id;
    }


    public String getName() {
        return name;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getHistory() {
        return history;
    }


    public String getAvatarUri() {
        return avatarUri;
    }

    public String getIdmedico() {
        return idmedico;
    }


}
