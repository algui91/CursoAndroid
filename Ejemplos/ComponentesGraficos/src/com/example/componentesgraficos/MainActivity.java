
package com.example.componentesgraficos;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnKeyListener;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TextView.OnEditorActionListener;
import android.widget.Toast;

public class MainActivity extends ActionBarActivity implements OnItemSelectedListener {

    // Variable que servirá de contador de pulsaciones del botón
    public int contador = 0;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Inicializar los componentes gráficos para poder usarlos.
        final Button button1 = (Button) findViewById(R.id.button1);
        final EditText editText1 = (EditText) findViewById(R.id.editText1);
        final ImageView imageView1 = (ImageView) findViewById(R.id.imageView);
        final CheckBox checkbox1 = (CheckBox) findViewById(R.id.checkBox1);
        final Spinner spinner = (Spinner) findViewById(R.id.spinner1);
        final Spinner spinnerResource = (Spinner) findViewById(R.id.spinnerR);

        /**
         * Registramos un listener on click para el botón. Cada vez que se pulse
         * se ejecutará el método onClick.
         */
        button1.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {
                Toast.makeText(
                        button1.getContext()
                        , "Me has pulsado " + ++contador + " veces."
                        , Toast.LENGTH_SHORT)
                        .show();
            }
        });

        /**
         * Otro tipo de listener, para el edittext, en esta ocasión los
         * parámetros del evento permiten conocer qué teclas está pulsando el
         * usuario, de modo que podemos reaccionar ante la pulsación de una
         * tecla determinada. Se ejecuta cada vez que se pulsa una tecla del
         * teclado
         */
        editText1.setOnEditorActionListener(new OnEditorActionListener() {
            
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    Toast.makeText(
                            editText1.getContext()
                            , "Escribiste: " + editText1.getText()
                            , Toast.LENGTH_SHORT)
                            .show();
                    return true;
                }
                return false;
            }
        });

        imageView1.setImageResource(R.drawable.ic_launcher);

        /**
         * Listener ejecutado cada vez que el checkbox se pulsa, nos permite
         * saber con la variable checked si se ha activado o desactivado.
         */
        checkbox1.setOnCheckedChangeListener(new OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton arg0, boolean checked) {
                if (checked)
                    Toast.makeText(checkbox1.getContext(), "Activo", Toast.LENGTH_SHORT).show();
                else
                    Toast.makeText(checkbox1.getContext(), "Inactivo", Toast.LENGTH_SHORT).show();
            }
        });

        
        /*
         * El siguiente trozo de código rellena dos spineers con datos desde distintas fuentes.
         * Uno de ellos es mediante un array de Strings y el otro es mediante un array de strings
         * almacenado como recurso XML.
         */
        
        String[] items = {
                "Enero", "Febrero", "Marzo", "..."
        };
        
        /*
         * Los Adapter son intermediarios entre los datos y la interfaz gráfica. Adaptan (de ahí su nombre)
         * una serie de datos para que puedan visualizarse en un componente gráfico. 
         * 
         * Se está declarando un arrayAdapter que usará como datos el array de string, y el aspecto que mostrará
         * el spinner será simple_spinner_dropdown_item.
         */
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_dropdown_item, items);

        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);

        /*
         * Aquí básicamente se hace lo mismo que en el anterior adapter, salvo que los datos 
         * se cargan desde un recurso XML.
         */
        ArrayAdapter<CharSequence> adapter2 = ArrayAdapter.createFromResource(this,
                R.array.planets_array, android.R.layout.simple_spinner_dropdown_item);
        spinnerResource.setAdapter(adapter2);

    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        String selected = parent.getItemAtPosition(position).toString();
        Toast.makeText(this, "Seleccionaste " + selected, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
        Toast.makeText(this, "Nada seleccionado", Toast.LENGTH_SHORT).show();
    }
}
