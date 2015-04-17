package es.tformacion.interact;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.provider.CalendarContract;
import android.provider.MediaStore;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import org.apache.http.protocol.HTTP;

import java.io.File;
import java.util.Calendar;
import java.util.List;

/**
 * Una de las caracterísitcas más importantes en las aplicaciones Android
 * es la capacidad de enviar al usuario a otra aplicación basándose en la acción
 * que éste quiere hacer. Por ejemplo, Si la aplicación necesita echar una foto,
 * no es necesario crear una actividad dentro de nuestra aplicación que use la cámara.
 * Podremos enviar una petición al sistema para que el usuario elija con qué aplicación
 * tomar la foto.
 * <p/>
 * La diferencia entre intents implícitos y explícitos es que en el último se indica
 * el nombre de la clase del componente a iniciar. En los intents implícitos por contra,
 * se indica el tipo de acción que se quiere realizar, como echar una foto o mostrar
 * una dirección en un mapa.
 *
 * @author Alejandro Alcalde (elbauldelprogramador.com)
 */

public class InteractMainActivity extends Activity {

    public static final int MEDIA_TYPE_IMAGE = 1;

    public static final int MEDIA_TYPE_VIDEO = 2;

    private static final int TAKE_PHOTO_REQUEST = 100;

    private static final String IMAGE_RESOURCE = "image-resource";

    private ImageView mPhotoImgage;

    private Uri mPhotoUri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.interact_activity_main);

        mPhotoImgage = (ImageView) findViewById(R.id.photoResultImage);

        if (savedInstanceState != null) {
            Uri s = Uri.parse(savedInstanceState.getString(IMAGE_RESOURCE));
            mPhotoImgage.setImageURI(s);
        }
    }

    public void onButtonClick(View target) {

        Intent intent = null;
        Uri uri = null;

        int i = target.getId();
        if (i == R.id.intentTelButton) {
            uri = Uri.parse("tel:5551234");
            intent = new Intent(Intent.ACTION_DIAL, uri);

        } else if (i == R.id.intentMapButton) {
            uri = Uri.parse("geo:37.1969773,-3.624193,218?z=18");
            intent = new Intent(Intent.ACTION_VIEW, uri);

        } else if (i == R.id.intentBrowserButton) {
            uri = Uri.parse("http://elbauldelprogramador.com");
            intent = new Intent(Intent.ACTION_VIEW, uri);

        } else if (i == R.id.intentEmailButton) {
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
            intent.putExtra(Intent.EXTRA_EMAIL, new String[]{
                    "foo@example.com"
            });
            intent.putExtra(Intent.EXTRA_SUBJECT, "Asunto");
            intent.putExtra(Intent.EXTRA_TEXT, "Mensaje");
            intent.putExtra(Intent.EXTRA_STREAM,
                    Uri.parse("content://path/to/email/attachment"));

        } else if (i == R.id.intentCalendarButton) {
            intent = new Intent(Intent.ACTION_INSERT, CalendarContract.Events.CONTENT_URI);
            long beginTime = Calendar.getInstance().getTimeInMillis();
            long endTime = Calendar.getInstance().getTimeInMillis() + 3600 * 1000;
            intent.putExtra(CalendarContract.EXTRA_EVENT_BEGIN_TIME, beginTime);
            intent.putExtra(CalendarContract.EXTRA_EVENT_END_TIME, endTime);
            intent.putExtra(CalendarContract.Events.TITLE, "Curso Android");
            intent.putExtra(CalendarContract.Events.EVENT_LOCATION, "Academia T-Formación");
            intent.putExtra(CalendarContract.Events.DESCRIPTION, "Curso básico sobre Android en T-Formación");

        } else if (i == R.id.intentShareButton) {
            intent = new Intent(Intent.ACTION_SEND);
            intent.setType("text/plain");
            intent.putExtra(Intent.EXTRA_TEXT, "Aprendiendo a compartir cosas en Android");
            String title = getResources().getString(R.string.chooser_title);
            // Mostrar el selector de apps
            Intent chooser = Intent.createChooser(intent, title);

            if (intent.resolveActivity(getPackageManager()) != null) {
                startActivity(chooser);
            } else {
                Toast.makeText(this, "No hay aplicaciones para esta acción", Toast.LENGTH_SHORT)
                        .show();
            }
        }
        
        /*
         * Antes de lanzar el intent, es conveniente comprobar que en efecto hay al menos una 
         * aplicación que pueda responder al intent. 
         */
        PackageManager pm = getPackageManager();
        List<ResolveInfo> activities = pm.queryIntentActivities(intent, 0);
        boolean isIntentSafe = activities.size() > 0;

        if (isIntentSafe && target.getId() != R.id.intentShareButton) {
            startActivity(intent);
        } else {
            Toast.makeText(this, "No hay aplicaciones para esta acción", Toast.LENGTH_SHORT).show();
        }
    }

    public void takePhoto(View target) {
        Intent takePhoto = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        mPhotoUri = Uri.fromFile(new File(getExternalFilesDir(null), "randomfilename"));
        takePhoto.putExtra(MediaStore.EXTRA_OUTPUT, mPhotoUri);
        startActivityForResult(takePhoto, TAKE_PHOTO_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case TAKE_PHOTO_REQUEST:
                    //http://stackoverflow.com/questions/12564112/android-camera-onactivityresult-intent-is-null-if-it-had-extras
                    mPhotoImgage.setImageBitmap((Bitmap) data.getExtras().get("data"));
                    break;
                default:
                    break;
            }
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState, PersistableBundle outPersistentState) {
        outState.putString(IMAGE_RESOURCE, mPhotoUri.toString());
        super.onSaveInstanceState(outState);
    }
}
