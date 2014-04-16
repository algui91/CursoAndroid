
package com.tformacion.interactwithothersapps;

import org.apache.http.protocol.HTTP;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.os.Bundle;
import android.provider.CalendarContract;
import android.provider.CalendarContract.Events;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import java.util.Calendar;
import java.util.List;

/**
 * 
 * Una de las caracterísitcas más importantes en las aplicaciones Android 
 * es la capacidad de enviar al usuario a otra aplicación basándose en la acción
 * que éste quiere hacer. Por ejemplo, Si la aplicación necestia echar una foto,
 * no es necesario crear una actividad dentro de nuestra aplicación que use la cámara.
 * Podremos enviar una petición al sistema para que el usuario elija con qué aplicación
 * tomar la foto.
 * 
 * La diferencia entre intents implícitos y explícitos es que en el último se indica 
 * el nombre de la clase del componente a iniciar. En los intents implícitos por contra,
 * se indica el tipo de acción que se quiere realizar, como echar una foto o mostrar 
 * una dirección en un mapa.
 * 
 * @author Alejandro Alcalde (elbauldelprogramador.com)
 *
 */

public class MainActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    public void onButtonClick(View target) {

        Intent intent = null;
        Uri uri = null;

        switch (target.getId()) {

            case R.id.intentTelButton:
                uri = Uri.parse("tel:5551234");
                intent = new Intent(Intent.ACTION_DIAL, uri);
                break;

            case R.id.intentMapButton:
                uri = Uri.parse("geo:37.1969773,-3.624193,218?z=18");
                intent = new Intent(Intent.ACTION_VIEW, uri);
                break;

            case R.id.intentBrowserButton:
                uri = Uri.parse("http://elbauldelprogramador.com");
                intent = new Intent(Intent.ACTION_VIEW, uri);
                break;

            case R.id.intentEmailButton:
                /*
                 * En caso de requerir de datos extra se le proporcionan con
                 * putExtra. Es aconsejable establecer el MIME Type basado en la
                 * Uri para especificar el tipo de actividades que podrán
                 * recibir el intent. Es importante definir el intent lo más
                 * específico posible. Si queremos mostrar una imagen con
                 * ACTION_VIEW, el MIME Type debería ser image/*.
                 */
                intent = new Intent(Intent.ACTION_SEND);
                intent.setType(HTTP.PLAIN_TEXT_TYPE); // Declaramos el tipo, ya
                                                      // que este intent no
                                                      // tiene uri
                intent.putExtra(Intent.EXTRA_EMAIL, new String[] {
                    "foo@example.com"
                });
                intent.putExtra(Intent.EXTRA_SUBJECT, "Asunto");
                intent.putExtra(Intent.EXTRA_TEXT, "Mensaje");
                intent.putExtra(Intent.EXTRA_STREAM,
                        Uri.parse("content://path/to/email/attachment"));
                break;

            case R.id.intentCalendarButton:
                intent = new Intent(Intent.ACTION_INSERT, Events.CONTENT_URI);
                long beginTime = Calendar.getInstance().getTimeInMillis();
                long endTime = Calendar.getInstance().getTimeInMillis() + 3600 * 1000;
                intent.putExtra(CalendarContract.EXTRA_EVENT_BEGIN_TIME, beginTime);
                intent.putExtra(CalendarContract.EXTRA_EVENT_END_TIME, endTime);
                intent.putExtra(Events.TITLE, "Curso Android");
                intent.putExtra(Events.EVENT_LOCATION, "Academia T-Formación");
                intent.putExtra(Events.DESCRIPTION, "Curso básico sobre Android en T-Formación");
                break;

            case R.id.intentShareButton:
                intent = new Intent(Intent.ACTION_SEND);
                intent.setType("text/plain");
                intent.putExtra(Intent.EXTRA_TEXT, "Aprendiendo a compartir cosas en Android");
                String title = getResources().getString(R.string.chooser_title);
                // Mostrar el selector de apps
                Intent chooser = Intent.createChooser(intent, title);
                
                if (intent.resolveActivity(getPackageManager()) != null) {
                    startActivity(chooser);
                } else {
                    Toast.makeText(this, "No hay aplicaciones para esta acción", Toast.LENGTH_SHORT).show();
                }
                break;
            default:
                break;
        }
        
        /*
         * Antes de lanzar el intent, es conveniente comprobar que en efecto hay al menos una 
         * aplicación que pueda responder al intent. 
         */
        PackageManager pm = getPackageManager();
        List<ResolveInfo> activities = pm.queryIntentActivities(intent, 0);
        boolean isIntentSafe = activities.size() > 0;
        
        if (isIntentSafe && target.getId() != R.id.intentShareButton){
            startActivity(intent);
        } else{
            Toast.makeText(this, "No hay aplicaciones para esta acción", Toast.LENGTH_SHORT).show();
        }
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
}
