
package es.tformacion.basicadapter.data.ui;

import android.app.Activity;
import android.os.Bundle;

import es.tformacion.basicadapter.R;

public class BasicAdapterMainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.basic_adapter_activity_main);

        if (savedInstanceState == null) {
            getFragmentManager().beginTransaction()
                    .add(R.id.container, new AndroidVersionsFragment())
                    .commit();
        }
    }
}
