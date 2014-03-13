
package com.turorial.holamundo;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.widget.TextView;

public class DisplayMessageActivity extends Activity {

    @SuppressLint("NewApi")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        /**
         * Toda Activity se invoca mediante un intent, independientemente de
         * cómo el usuario haya llegado hasta ella. Se puede obtener el intent
         * con el método getIntent() y obtener la información adjunta.
         */
        Intent intent = getIntent();
        String message = intent.getStringExtra(MainActivity.EXTRA_MESSAGE);

        /**
         * En esta ocasión, vamos a ver cómo crear la interfaz desde código en
         * lugar de XML. Para ello declaramos un TextView que mostrará el
         * mensaje y lo añadiremos a la raiz de la activity con
         * setContentView().
         */
        TextView textView = new TextView(this);
        textView.setTextSize(40);
        textView.setText(message);

        // Establecer el TextView como interfaz de la actividad
        setContentView(textView);

        // Show the Up button in the action bar.
        setupActionBar();

    }

    /**
     * Set up the {@link android.app.ActionBar}, if the API is available.
     */
    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    private void setupActionBar() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            getActionBar().setDisplayHomeAsUpEnabled(true);
        }
    }
}
