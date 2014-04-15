
package com.tformacion.persistenciadedatos;

import com.tformacion.persistenciadedatos.PersonContract.PersonEntry;

import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TextView.OnEditorActionListener;
import android.widget.Toast;

/**
 * Si se tiene una colección de datos relativamente pequeña en forma
 * de pares clave-valor, se puede usar la API de SharedPreferences. Un objeto
 * de éste tipo apunta a un archivo conteniendo pares de datos. Pueden ser
 * privados o públicos.
 * 
 * Ésta API no debe confundirse con la API de Preference, que ayuda a construir 
 * una interfaz gráfica para las preferencias de nuestra aplicación (Por debajo ésta usa
 * SharedPreferences)
 * 
 * Para mayores cantidades de datos se usarán bases de datos en SQLite
 * 
 * @see http://developer.android.com/training/basics/data-storage/shared-preferences.html
 *
 */
public class MainActivity extends ActionBarActivity {

    public static final String EDIT_TEXT_SHARED_PREFERENCES = "ejShared"; 
    
    private SharedPreferences mSharedPrefs;
    private PersonDbBHelper mDbHelper; 
    private TextView mDbRecordsText;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        mSharedPrefs = getPreferences(Context.MODE_PRIVATE);
        
        final EditText editTextSharedPrefs = (EditText) findViewById(R.id.sharedPrefEditText);
        final EditText firstNameED = (EditText) findViewById(R.id.firstNameEd);
        final EditText secondNameED = (EditText) findViewById(R.id.secondNameED);
        final EditText deleteRowED = (EditText) findViewById(R.id.deleteRowEditText);
        final Button saveToDbButton = (Button) findViewById(R.id.buttonSaveToDb);
        final Button showDbRecordsButton = (Button) findViewById(R.id.buttonShowDbContent);
        final Button deleteRowButton = (Button) findViewById(R.id.deleteRowButton);
        
        mDbRecordsText = (TextView) findViewById(R.id.BDRecords);
        
        // Comprobamos si hay datos guardados.
        String value = mSharedPrefs.getString(EDIT_TEXT_SHARED_PREFERENCES, "");
        if (!value.isEmpty()){
           editTextSharedPrefs.setText(value);
        }
        
        mDbHelper = new PersonDbBHelper(this);
        // Mosrar los datos en la base de datos si existen.
        new getPersons().execute();
        
        editTextSharedPrefs.setOnEditorActionListener(new OnEditorActionListener() {

            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == R.id.save_shared || actionId == EditorInfo.IME_NULL) {
                    
                    /*
                     * Con getSharedPreferences() es posible crear varios archivos de preferencias
                     * identificados por nombre.
                     * Con getPreferences(), usado desde un Activity únicamente se crea un archivo
                     * con el nombre de la actividad. 
                     */
                    
//                    Context context = getActivity();
//                    SharedPreferences sharedPref = context.getSharedPreferences(
//                            getString(R.string.preference_file_key), Context.MODE_PRIVATE);
                    
                    mSharedPrefs
                            .edit()
                            .putString(EDIT_TEXT_SHARED_PREFERENCES, v.getText().toString())
                            .commit();

                    Toast.makeText(getApplicationContext(), "Guardando " + v.getText(),
                            Toast.LENGTH_LONG).show();
                    
                    hideKB();
                    return true;
                }
                return false;
            }
        });
        
        saveToDbButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                new insertPerson().execute(firstNameED.getText().toString(), secondNameED.getText().toString());
            }
        });
        
        showDbRecordsButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                mDbRecordsText.setText("ID \t FName");
                new getPersons().execute();
            }
        });
        
        deleteRowButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                new deleteRow().execute(Integer.parseInt(deleteRowED.getText().toString()));
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
    
    private void hideKB() {
        InputMethodManager inputManager = (InputMethodManager)
                getSystemService(Context.INPUT_METHOD_SERVICE);
        inputManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(),
                InputMethodManager.HIDE_NOT_ALWAYS);
    }
    
    
    
    /**
     * Siempre que se realizen operaciones que conlleven carga a la CPU o puedan
     * tardar bastante tiempo, hay que lanzarlas en hilos separdos del principal, 
     * encargado de la interfaz gráfica.
     */
    private class insertPerson extends AsyncTask<String, Void, Long>{

        @Override
        protected Long doInBackground(String... what) {

            SQLiteDatabase db = mDbHelper.getWritableDatabase();
            // Mapa de valores, cuyas claves serán los nombres de las columnas
            ContentValues values = new ContentValues();
            values.put(PersonEntry.COLUMN_NAME_FIRST_NAME, what[0]);
            values.put(PersonEntry.COLUMN_NAME_SECOND_NAME, what[1]);

            long newRowId = db.insert(PersonEntry.TABLE_NAME, null, values);
            return newRowId;

        }
        
        @Override
        protected void onPostExecute(Long result) {
            Toast.makeText(getApplicationContext(), "Insertada fila con ID: " + result,
                    Toast.LENGTH_SHORT).show();
        }
    }
    
    private class getPersons extends AsyncTask<Void, Void, String[]>{

        @Override
        protected String[] doInBackground(Void... params) {
            SQLiteDatabase db = mDbHelper.getReadableDatabase();
            
            // Definir la proyección de los datos que queremos, en este caso solo ID y primer nombre
            String[] projection = {
                    PersonEntry._ID,
                    PersonEntry.COLUMN_NAME_FIRST_NAME
            };
            
            // Definir el orden en que devolver los datos
            String sortOrder = PersonEntry.COLUMN_NAME_FIRST_NAME + " DESC";
            
            Cursor c = db.query(
                    PersonEntry.TABLE_NAME, // Nombre de la tabla 
                    projection,             // Columnas a devolver
                    null,                   // Columnas para la cláusula WHERE
                    null,                   // Valores para la cláusula WHERE
                    null,                   // GROUP BY
                    null,                   // HAVING
                    sortOrder);             // ORDER BY
            
            // Recorrer la información devuelta
            String[] data = new String[c.getCount()];
            int i = 0;
            //c.moveToFirst();
            while (c.moveToNext()) {
                data[i++] = 
                        c.getString(c.getColumnIndex(PersonEntry._ID)) + " \t " +
                        c.getString(c.getColumnIndex(PersonEntry.COLUMN_NAME_FIRST_NAME));
            }
            
            c.close();
            
            return data;
        }
        
        @Override
        protected void onPostExecute(String[] result) {
            for (String row: result){
                mDbRecordsText.append(" \n " + row);
            }
        }
    }
    
    private class deleteRow extends AsyncTask<Integer, Void, Integer>{

        @Override
        protected Integer doInBackground(Integer... params) {
            SQLiteDatabase db = mDbHelper.getWritableDatabase();
            
            String selection;
            String[] selectionArgs = null;
            
            // Definición de la parte WHERE
            if (params[0] == -1){
                selection = "1"; // Borra todas las filas
            }
            else {
                selection = PersonEntry._ID + " LIKE ?";
                selectionArgs = new String[] {String.valueOf(params[0])};
            }

            // Ejecutar la consulta SQL
            int rows_deleted = db.delete(PersonEntry.TABLE_NAME, selection, selectionArgs);
            
            return rows_deleted;
        }
        
        @Override
        protected void onPostExecute(Integer result) {
            Toast.makeText(getApplicationContext(), "Elminadas " + result + " filas",
                    Toast.LENGTH_SHORT).show();
        }
    }
    
}
