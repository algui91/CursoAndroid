
package com.turorial.holamundo;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;

/**
 * Clase principal, hereda de la clase Activity, por lo cual dispondrá de
 * interfaz de usuario. Al crear una Activity, Android invoca a una serie de
 * métodos, entre ellos <i>oncreate()</i>. El ciclo de vida de una activity se
 * puede ver en <a href=
 * "http://developer.android.com/reference/android/app/Activity.html#ActivityLifecycle">Activity Lifecycle</a>
 * 
 * @see http://developer.android.com/reference/android/app/Activity.html#
 *      ActivityLifecycle
 * @author Alejandro Alcalde
 */
public class MainActivity extends Activity implements OnClickListener {

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
    public boolean onCreateOptionsMenu(Menu menu) {
        /**
         * Crea los menús definidos en los recursos, sólo se llama una vez, al
         * pulsar el botón de menú.
         */
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
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

}
