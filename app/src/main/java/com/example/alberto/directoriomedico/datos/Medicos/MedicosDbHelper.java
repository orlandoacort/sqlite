package com.example.alberto.directoriomedico.datos.Medicos;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.alberto.directoriomedico.datos.Medicos.Medicos;
import com.example.alberto.directoriomedico.datos.Medicos.MedicosContract;

public class MedicosDbHelper extends SQLiteOpenHelper {

    public static final int DATABASE_VERSION
            = 1;
    public static final String DATABASE_NAME
            = "Medicos.db";

    public MedicosDbHelper(Context context){
        super(context,DATABASE_NAME
                ,null,DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("CREATE TABLE "
                + MedicosContract.MedicoEntry.TABLE_NAME + "("
                + MedicosContract.MedicoEntry._ID
                + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + MedicosContract.MedicoEntry.ID
                + " TEXT NOT NULL,"
                + MedicosContract.MedicoEntry.NAME
                + " TEXT NOT NULL,"
                + MedicosContract.MedicoEntry.SPECIALTY
                + " TEXT NOT NULL,"
                + MedicosContract.MedicoEntry.PHONE_NUMBER
                + " TEXT NOT NULL,"
                + MedicosContract.MedicoEntry.BIO
                + " TEXT NOT NULL,"
                + MedicosContract.MedicoEntry.AVATAR_URI
                + " TEXT,"
                + "UNIQUE (" + MedicosContract.MedicoEntry.ID + "))"
        );

        //realizar inserción
        mockData(sqLiteDatabase);
    }

    private void mockData(SQLiteDatabase sqLiteDatabase){
        mockMedicos(sqLiteDatabase,new Medicos("Carlos Sanchez"
                ,"Médico Emergencista","300 200 1111",
                "Gran profesional con experiencia de 5 años " +
                "en Servicio de Emergencia.","carlos_sanchez.jpg"));
        mockMedicos(sqLiteDatabase,new Medicos("Gregory House"
                ,"Médico Internista","300 200 2222",
                "Gran profesional con experiencia de 15 años " +
                "en Servicio de Hospitalización.","gregory_house.jpg"));
        mockMedicos(sqLiteDatabase,new Medicos("Marina Acosta"
                ,"Médico Internista","300 200 3333",
                "Gran profesional con experiencia de 25 años " +
                "en Servicio de Hospitalización.","marina_acosta.jpg"));
        mockMedicos(sqLiteDatabase,new Medicos("Daniel Samper"
                ,"Médico Ginecólogo","300 200 4444",
                "Gran profesional con experiencia de 25 años " +
                "en Servicio de Ginecología.","daniel_samper.jpg"));
        mockMedicos(sqLiteDatabase,new Medicos("Lucia Aristizabal"
                ,"Médico Internista","300 200 5555",
                "Gran profesional con experiencia de 25 años " +
                "en Servicio de Hospitalización.","lucia_aristizabal.jpg"));
        mockMedicos(sqLiteDatabase,new Medicos("Olga Ortiz"
                ,"Médico Internista","300 200 6666",
                "Gran profesional con experiencia de 25 años " +
                "en Servicio de Hospitalización.","olga_ortiz.jpg"));
        mockMedicos(sqLiteDatabase,new Medicos("Pamela Briger"
                ,"Médico Internista","300 200 7777",
                "Gran profesional con experiencia de 25 años " +
                "en Servicio de Hospitalización.","pamela_briger.jpg"));
        mockMedicos(sqLiteDatabase,new Medicos("Rodrigo Benavidez"
                ,"Médico Internista","300 200 8888",
                "Gran profesional con experiencia de 25 años " +
                "en Servicio de Hospitalización.","rodrigo_benavidez.jpg"));
        mockMedicos(sqLiteDatabase,new Medicos("Tom Bonz"
                ,"Médico Internista","300 200 9999",
                "Gran profesional con experiencia de 25 años " +
                "en Servicio de Hospitalización.","tom_bonz.jpg"));

    }

    public long mockMedicos(SQLiteDatabase db
            , Medicos medicos){
        return db.insert(MedicosContract.MedicoEntry.TABLE_NAME
            ,null
            ,medicos.toContentValues());
    }



    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public Cursor getAllMedicos(){
        return getReadableDatabase()
                .query(MedicosContract.MedicoEntry.TABLE_NAME
                ,null // columnas
                ,null // WHERE
                ,null // valores WHERE
                ,null // GROUP BY
                ,null // HAVING
                ,null); // OREDER BY
    }

    public Cursor getMedicoById(String medicoId){
        return getReadableDatabase()
                .query(MedicosContract.MedicoEntry.TABLE_NAME
                ,null
                , MedicosContract.MedicoEntry.ID + " LIKE ?"
                ,new String[] {medicoId}
                ,null
                ,null
                ,null);
    }

    public long saveMedico(Medicos medicos){
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();

        return sqLiteDatabase.insert(
                MedicosContract.MedicoEntry.TABLE_NAME
                ,null
                ,medicos.toContentValues());
    }

    public int updateMedico(Medicos medicos
            ,String medicoId){
        return getWritableDatabase().update(
                MedicosContract.MedicoEntry.TABLE_NAME
                ,medicos.toContentValues()
                , MedicosContract.MedicoEntry.ID+" LIKE ?"
                ,new String[]{medicoId}
        );
    }

    public int deleteMedico(String medicoId){
        return getWritableDatabase().delete(
                MedicosContract.MedicoEntry.TABLE_NAME
                , MedicosContract.MedicoEntry.ID+" LIKE ?"
                ,new String[]{medicoId}
        );
    }
}
