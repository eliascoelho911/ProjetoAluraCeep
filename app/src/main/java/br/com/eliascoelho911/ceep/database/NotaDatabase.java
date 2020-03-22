package br.com.eliascoelho911.ceep.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import br.com.eliascoelho911.ceep.model.Nota;

@Database(entities = {Nota.class}, version = 3, exportSchema = false)
public abstract class NotaDatabase extends RoomDatabase {

    private static final String NOME_BANCO = "nota.db";

    public abstract RoomNotaDAO getRoomNotaDAO();

    public static NotaDatabase getInstance(Context context) {
        if (context != null) {
            return Room.databaseBuilder(context, NotaDatabase.class, NOME_BANCO)
                    .fallbackToDestructiveMigrationFrom(1, 2)
                    .build();
        } else {
            return null;
        }
    }
}
