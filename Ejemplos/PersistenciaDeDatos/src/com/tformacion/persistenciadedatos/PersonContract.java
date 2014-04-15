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

import android.provider.BaseColumns;

/**
 * La finalidad de esta clase es servir abstracción a la base de datos. 
 * Aquí se definirá el esquema de representación de la base de datos, bien documentado.
 * 
 * En la clase externa deberá ir todo campo global a la base de datos
 * 
 * @author Alejandro Alcalde
 * 
 * Un ejemplo real @see https://android.googlesource.com/platform/frameworks/base/+/master/core/java/android/provider/
 *
 */
public final class PersonContract {

    public PersonContract() {
    }
    
    /**
     * Clase interna que define el contenido de la tabla.
     * 
     * Implementando BaseColumns, se añadirán dos entradas más a la tabla, 
     * _ID y _COUNT.
     */
    public static abstract class PersonEntry implements BaseColumns{
        public static final String TABLE_NAME = "person";
        public static final String COLUMN_NAME_ENTRY_ID = "personID";
        public static final String COLUMN_NAME_FIRST_NAME = "firstname";
        public static final String COLUMN_NAME_SECOND_NAME = "secondname";
    }
}
