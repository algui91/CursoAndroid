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
import android.widget.TextView;

public class DisplayMessageActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        /**
         * Toda Activity se invoca mediante un intent, independientemente de
         * cómo el usuario haya llegado hasta ella. Se puede obtener el intent
         * con el método getIntent() y obtener la información adjunta.
         */
        Intent intent = getIntent();
        String message = intent.getStringExtra(HelloWorldMain.EXTRA_MESSAGE);

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
    }
}
