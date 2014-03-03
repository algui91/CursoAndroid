
package com.turorial.holamundo;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;

/**
 * Clase principal, hereda de la clase Activity, por lo cual dispondrá de interfaz de usuario.
 * Al crear una Activity, Android invoca a una serie de métodos, entre ellos <i>oncreate()</i>. 
 * El ciclo de vida de una activity se puede ver en <a href="http://developer.android.com/reference/android/app/Activity.html#ActivityLifecycle" target="_blank">Activity Lifecycle</a>
 * 
 * @see http://developer.android.com/reference/android/app/Activity.html#ActivityLifecycle
 * @author Alejandro Alcalde
 *
 */
public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        /**
         * Método encargado de “inflar” la actividad. Inicializar cada componente de la actividad 
         * con su correspondiente View.
         */
        setContentView(R.layout.activity_main);
    }

    /**
     * Crea los menús definidos en los recursos, sólo se llama una vez, al pulsar el botón de menú.
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

}
