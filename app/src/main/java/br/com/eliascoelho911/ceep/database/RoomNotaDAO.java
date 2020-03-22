package br.com.eliascoelho911.ceep.database;

import java.util.List;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;
import br.com.eliascoelho911.ceep.model.Nota;

@Dao
public abstract class RoomNotaDAO {
    @Insert
    public abstract void insere(Nota nota);

    @Delete
    public abstract void remove(Nota nota);

    @Query("SELECT * FROM Nota ORDER BY idAdapter ASC, id DESC")
    public abstract List<Nota> todos();

    @Update
    public abstract void altera(Nota nota);

    @Update
    public abstract void altera(List<Nota> notas);

    @Update
    public abstract void altera(Nota... notas);
}
