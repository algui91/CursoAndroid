/**
 *   This program is free software: you can redistribute it and/or modify
 *   it under the terms of the GNU General Public License as published by
 *   the Free Software Foundation, either version 3 of the License, or
 *   (at your option) any later version.
 *
 *   This program is distributed in the hope that it will be useful,
 *   but WITHOUT ANY WARRANTY; without even the implied warranty of
 *   MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *   GNU General Public License for more details.
 *
 *   You should have received a copy of the GNU General Public License
 *   along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package com.tformacion.persistenciadedatos;

import com.tformacion.persistenciadedatos.PersonContract.PersonEntry;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Esta clase hereda de SQLiteOpenHelper, que proporciona un conjunto de APIs
 * útiles para el manejo de la base de datos.
 * 
 * @author Alejandro Alcalde (elbauldelprogramador.com)
 */
public class PersonDbBHelper extends SQLiteOpenHelper {

    // De cambiar el esquema de la base de datos, hay que incrementar la versión
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "Persons.db";
    
    private static final String TEXT_TYPE = " TEXT";
    private static final String COMMA_SEP = ",";
    
    private static final String SQL_CREATE_ENTRIES =
            "CREATE TABLE " + PersonEntry.TABLE_NAME + " (" +
                    PersonEntry._ID + " INTEGER PRIMARY KEY," + // Heredado de BaseColumns
                    PersonEntry.COLUMN_NAME_ENTRY_ID + TEXT_TYPE + COMMA_SEP +
                    PersonEntry.COLUMN_NAME_FIRST_NAME + TEXT_TYPE + COMMA_SEP +
                    PersonEntry.COLUMN_NAME_SECOND_NAME + TEXT_TYPE  +
                    " )";

    private static final String SQL_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS " + PersonEntry.TABLE_NAME;

    
    public PersonDbBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
    
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_ENTRIES);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        /*
         * De cambiar el esquema en la base de datos, aquí habría que hacer 
         * las modificaciones necesarias para actualizar de un esquema a otro,
         * para este ejemplo, simplemente la borraremos y la volveremos a crear
         */
        db.execSQL(SQL_DELETE_ENTRIES);
        onCreate(db);
    }

    @Override
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Proceso inverso a actualizar
        onUpgrade(db, oldVersion, newVersion);
    }
}
