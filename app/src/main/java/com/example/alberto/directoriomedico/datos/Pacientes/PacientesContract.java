package com.example.alberto.directoriomedico.datos.Pacientes;

import android.provider.BaseColumns;

public class PacientesContract {
    public static abstract class PacienteEntry implements BaseColumns {
        public static final String TABLE_NAME = "pacientes";

        public static final String ID = "id";
        public static final String NAME = "name";
        public static final String PHONE_NUMBER = "phoneNumber";
        public static final String HISTORY = "history";
        public static final String AVATAR_URI = "avatarUri";
        public static final String IDMEDICO = "idmedico";
    }

}
