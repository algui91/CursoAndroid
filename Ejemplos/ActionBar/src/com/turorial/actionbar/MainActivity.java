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

package com.turorial.actionbar;

import com.turorial.holamundo.R;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.Toast;

/**
 * El Action Bar es uno de los elementos más importantes en el diseño que se
 * puede implementar en una Activity. Proporciona una serie de características:
 * 
 * - Espacio para identificar a la app e indicar al usuario en qué lugar de la
 *   aplicación se encuentra 
 * 
 * - Acceso a acciones importantes (Buscar, copiar, pegar...) 
 * 
 * - Navegación entre actividades y vistas.
 * 
 * El Action bar permite añádir botones para las acciones más importantes de
 * la aplicación en el contexto actual. Se llaman action buttons. Si hay demasiados
 * botones y no tienen cabida en la pantalla, se ocultan.
 * 
 * @author Alejandro Alcalde
 */
public class MainActivity extends ActionBarActivity implements OnClickListener {

    public final static String EXTRA_MESSAGE = "com.tutorial.holamundo.MESSAGE";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        /**
         * Método encargado de “inflar” la actividad. Inicializar cada
         * componente de la actividad con su correspondiente View.
         */
        setContentView(R.layout.activity_main);
    }
    
    @Override
    public void onClick(View v) {
        /**
         * Método usado para controlar las pulsaciones del usuario en las
         * vistas. Hay varias formas de manejar los eventos onclick. Una de
         * ellas es controlarlos todos bajo un mismo método que es llamado para
         * todas las pulsaciones sobre las vistas, y filtrar mediante un switch
         * en ésta función qué vista fue pulsada. En el XML debe estar declarado
         * el atributo andoid:onClick="onClick". Otra forma es especificar un
         * método para cada evento onclick de cada vista.
         */
        switch (v.getId()) {
            case R.id.send_button:
                /**
                 * Como vimos en las transparencias, un intent proporciona una
                 * forma de enlazar componentes separados. Un intent representa
                 * la intención de una aplicación de hacer algo. Pueden usarse
                 * para muchas cosas, entre ellas para lanzar otra activity.
                 */
                // Los parámetros son el contexto y la activity a lanzar.
                Intent intent = new Intent(this, DisplayMessageActivity.class);

                /**
                 * Obtenemos una referencia del EditText declarado en XML.
                 * findViewById devuelve un objeto View, por tanto es necesario
                 * realizar un casting al tipo de vista que nos interesa.
                 */
                EditText editText = (EditText) findViewById(R.id.edit_message);
                // Obtener el valor introducido por el usuario
                String message = editText.getText().toString();
                /**
                 * Los intent pueden llevar información consigo, para que el
                 * componente que los reciba pueda usala. En este caso al intent
                 * le adjuntaremos el mensaje escrito en el edittext. El primer
                 * argurmento es el nombre que identificará al mensaje adjunto,
                 * así el otro componente podrá obtenerlo y usarlo.
                 */
                intent.putExtra(EXTRA_MESSAGE, message);
                // lanzamos la actividad
                startActivity(intent);
                break;

            default:
                break;
        }
    }    
    
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        // Inflar los elementos del menú para usarlos en el ActionBar
        getMenuInflater().inflate(R.menu.main_activity_actions, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        /**
         * Cuando el usuario pulsa uno de los action buttons o cualquier otro
         * botón del menú, se llama a éste método callback. Es parecido al
         * callback que se vió en el Tema 1 para responder a los eventos onclick
         * de un botón.
         */
        switch (item.getItemId()) {
            case R.id.action_search:
                Toast.makeText(this, "Action_search", Toast.LENGTH_SHORT).show();
                break;
            case R.id.action_settings:
                Toast.makeText(this, "Action_settings", Toast.LENGTH_SHORT).show();
            default:
                break;
        }
        return true;
    }
}
