package com.tencent.qcloud.tim.demo.Database;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import android.content.Context;


@Database(entities = {Diaodan.class}, version = 3, exportSchema = false)
public abstract class DiaodanDatabase extends RoomDatabase {
    public abstract DiaodanDAO DiaodanDAO();
    private static volatile DiaodanDatabase INSTANCE;
    static DiaodanDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (DiaodanDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE =
                            Room.databaseBuilder(context.getApplicationContext(),
                                    DiaodanDatabase.class, "Diaodan_Database")
                                    .build();
                }
            }
        }
        return INSTANCE;
    }
}
