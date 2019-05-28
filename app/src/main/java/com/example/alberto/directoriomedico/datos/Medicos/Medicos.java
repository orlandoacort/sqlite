package com.example.alberto.directoriomedico.datos.Medicos;

import android.content.ContentValues;
import android.database.Cursor;

import java.util.UUID;

public class Medicos {
    private String id;
    private String name;
    private String speciality;
    private String phoneNumber;
    private String bio;
    private String avatarUri;

    public Medicos( String name
            , String speciality, String phoneNumber
            , String bio, String avatarUri) {
        this.id = UUID.randomUUID().toString();
        this.name = name;
        this.speciality = speciality;
        this.phoneNumber = phoneNumber;
        this.bio = bio;
        this.avatarUri = avatarUri;
    }

    public Medicos(Cursor cursor){
        id = cursor.getString(cursor.getColumnIndex
                (MedicosContract.MedicoEntry.ID));
        name = cursor.getString(cursor.getColumnIndex
                (MedicosContract.MedicoEntry.NAME));
        speciality = cursor.getString(cursor.getColumnIndex
                (MedicosContract.MedicoEntry.SPECIALTY));
        phoneNumber = cursor.getString(cursor.getColumnIndex
                (MedicosContract.MedicoEntry.PHONE_NUMBER));
        bio = cursor.getString(cursor.getColumnIndex
                (MedicosContract.MedicoEntry.BIO));
        avatarUri = cursor.getString(cursor.getColumnIndex
                (MedicosContract.MedicoEntry.AVATAR_URI));
    }

    public ContentValues toContentValues(){
        ContentValues values = new ContentValues();
        values.put(MedicosContract.MedicoEntry.ID,id);
        values.put(MedicosContract.MedicoEntry.NAME,name);
        values.put(MedicosContract.MedicoEntry.SPECIALTY
                ,speciality);
        values.put(MedicosContract.MedicoEntry.PHONE_NUMBER
                ,phoneNumber);
        values.put(MedicosContract.MedicoEntry.BIO,bio);
        values.put(MedicosContract.MedicoEntry.AVATAR_URI
                ,avatarUri);
        return values;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getSpeciality() {
        return speciality;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getBio() {
        return bio;
    }

    public String getAvatarUri() {
        return avatarUri;
    }
}
