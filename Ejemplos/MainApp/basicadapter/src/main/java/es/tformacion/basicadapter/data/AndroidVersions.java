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

package es.tformacion.basicadapter.data;

/**
 * El propósito de esta clase es almacenar los datos que queremos mostrar en la
 * vista. Luego pasaremos éstos datos al adaptador y él se encargará de hacer
 * corresponder cada dato con su posición en el layout.
 * 
 * @author Alejandro Alcalde (elbauldelprogramador.com) Licensed under GPLv3
 */
public class AndroidVersions {

    private String codename;
    private String version;
    private int logo;

    public AndroidVersions(String nombre, String version, int logotipo) {
        this.codename = nombre;
        this.version = version;
        this.logo = logotipo;
    }

    public void setNombre(String nombre) {
        this.codename = nombre;
    }

    public String getNombre() {
        return codename;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getVersion() {
        return version;
    }

    public void setLogotipo(int logotipo) {
        this.logo = logotipo;
    }

    public int getLogotipo() {
        return logo;
    }

}
