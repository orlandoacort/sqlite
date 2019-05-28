package com.example.alberto.directoriomedico.datos.Pacientes;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.annotation.Nullable;

public class PacientesDbHelper extends SQLiteOpenHelper {

    public static final int DATABASE_VERSION
            = 1;
    public static final String DATABASE_NAME
            = "Pacientes.db";

    public PacientesDbHelper(Context context){
        super(context,DATABASE_NAME
                ,null,DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("CREATE TABLE "
                + PacientesContract.PacienteEntry.TABLE_NAME + "("
                + PacientesContract.PacienteEntry._ID
                + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + PacientesContract.PacienteEntry.ID
                + " TEXT NOT NULL,"
                + PacientesContract.PacienteEntry.NAME
                + " TEXT NOT NULL,"
                + PacientesContract.PacienteEntry.PHONE_NUMBER
                + " TEXT NOT NULL,"
                + PacientesContract.PacienteEntry.HISTORY
                + " TEXT NOT NULL,"
                + PacientesContract.PacienteEntry.AVATAR_URI
                + " TEXT,"
                +PacientesContract.PacienteEntry.IDMEDICO
                + " TEXT NOT NULL,"
                + "UNIQUE (" + PacientesContract.PacienteEntry.ID + "))"
        );

        //realizar inserción
        mockData(sqLiteDatabase);
    }

    private void mockData(SQLiteDatabase sqLiteDatabase) {
        mockPacientes(sqLiteDatabase, new Pacientes("Paciente1"
                , "975863551", "1111",
                "paciente1.jpg"
                , "84ef2a10-6d1f-4be7-91c1-f8150dd80e3c"
        ));
        mockPacientes(sqLiteDatabase, new Pacientes("Paciente2"
                , "975863551", "1111",
                "paciente7.jpg"
                , "2415af12-cc89-47e8-ad12-be84ee215ff5"
        ));
        mockPacientes(sqLiteDatabase, new Pacientes("Paciente3"
                , "975863551", "1111",
                "paciente6.jpg"
                , "5d82af50-203a-4519-b181-3aec08885e77"
        ));
        mockPacientes(sqLiteDatabase, new Pacientes("Paciente4"
                , "975863551", "1111",
                "paciente5.jpg"
                , "781b0e25-2ae6-4567-9234-f431183d13d2"
        ));
        mockPacientes(sqLiteDatabase, new Pacientes("Paciente5"
                , "975863551", "1111",
                "paciente3.jpg"
                , "9b1510fd-ff9b-45d5-ae40-58a05263ae9d"
        ));
        mockPacientes(sqLiteDatabase, new Pacientes("Paciente6"
                , "975863551", "1111",
                "paciente2.jpg"
                , "9780cafd-b442-4be1-8ff9-26cbda49ec28"
        ));
    }

    public long mockPacientes(SQLiteDatabase db
            , Pacientes pacientes){
        return db.insert(PacientesContract.PacienteEntry.TABLE_NAME
                ,null
                ,pacientes.toContentValues());
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

    public Cursor getAllPacientes(){
        return getReadableDatabase()
                .query(PacientesContract.PacienteEntry.TABLE_NAME
                        ,null // columnas
                        ,null // WHERE
                        ,null // valores WHERE
                        ,null // GROUP BY
                        ,null // HAVING
                        ,null); // OREDER BY
    }

    public Cursor getPacientesByMedico(String idMedico){
        return getReadableDatabase()
                .query(PacientesContract.PacienteEntry.TABLE_NAME
                        ,null
                        ,PacientesContract.PacienteEntry.IDMEDICO + " LIKE ?"
                        ,new String[] {idMedico}
                        ,null
                        ,null
                        ,null);

    }

    public Cursor getPacientesById(String pacienteId){
        return getReadableDatabase()
                .query(PacientesContract.PacienteEntry.TABLE_NAME
                        ,null
                        , PacientesContract.PacienteEntry.ID + " LIKE ?"
                        ,new String[] {pacienteId}
                        ,null
                        ,null
                        ,null);
    }

    public long savePaciente(Pacientes pacientes){
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();

        return sqLiteDatabase.insert(
                PacientesContract.PacienteEntry.TABLE_NAME
                ,null
                ,pacientes.toContentValues());
    }

    public int updatePaciente(Pacientes pacientes
            ,String pacienteId){
        return getWritableDatabase().update(
                PacientesContract.PacienteEntry.TABLE_NAME
                ,pacientes.toContentValues()
                , PacientesContract.PacienteEntry.ID+" LIKE ?"
                ,new String[]{pacienteId}
        );
    }

    public int deletePaciente(String pacienteId){
        return getWritableDatabase().delete(
                PacientesContract.PacienteEntry.TABLE_NAME
                , PacientesContract.PacienteEntry.ID+" LIKE ?"
                ,new String[]{pacienteId}
        );
    }
}
