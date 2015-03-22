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

package es.tformacion.helloworld;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

/**
 * Clase principal, hereda de la clase Activity, por lo cual dispondrá de
 * interfaz de usuario. Al crear una Activity, Android invoca a una serie de
 * métodos, entre ellos <i>oncreate()</i>. El ciclo de vida de una activity se
 * puede ver en <a href="http://developer.android.com/reference/android/app/Activity.html#ActivityLifecycle">Activity
 * Lifecycle</a>
 *
 * @author Alejandro Alcalde
 * @see http://developer.android.com/reference/android/app/Activity.html#ActivityLifecycle
 */
public class HelloWorldMain extends Activity {

    public final static String EXTRA_MESSAGE = "com.tutorial.holamundo.MESSAGE";

    private Button mButton;

    private OnClickListener mOnClickListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        /**
         * Método encargado de “inflar” la actividad. Inicializar cada
         * componente de la actividad con su correspondiente View.
         */
        setContentView(R.layout.activity_hello_world_main);

        mButton = (Button) findViewById(R.id.send_button);

        mOnClickListener = new OnClickListener() {
            @Override
            public void onClick(View view) {
                /**
                 * Como vimos en las transparencias, un intent proporciona una
                 * forma de enlazar componentes separados. Un intent representa
                 * la intención de una aplicación de hacer algo. Pueden usarse
                 * para muchas cosas, entre ellas para lanzar otra activity.
                 */
                // Los parámetros son el contexto y la activity a lanzar.
                Intent intent = new Intent(getApplicationContext(), DisplayMessageActivity.class);

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
            }
        };

        mButton.setOnClickListener(mOnClickListener);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        // Inflar los elementos del menú para usarlos en el ActionBar
        getMenuInflater().inflate(R.menu.main_activity_actions, menu);
        return true;
    }
}
