
package es.tformacion.receivingdata;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ShareActionProvider;
import android.widget.TextView;

public class ReceivingDataMainActivity extends Activity {

    private ShareActionProvider mShareActionProvider;
    private Intent mIntent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.receiving_data_activity_main);

        mIntent = getIntent();
        String action = mIntent.getAction();
        String type = mIntent.getType();

        final TextView t = (TextView) findViewById(R.id.textView);

        if (Intent.ACTION_SEND.equals(action) && type != null) {
            if ("text/plain".equals(type)) {
                String sharedText = mIntent.getStringExtra(Intent.EXTRA_TEXT);
                if (sharedText != null) {
                    t.setText(sharedText);
                }
            }
        }
    }

    public void handleClick(View v) {
        int i1 = v.getId();
        if (i1 == R.id.shareintent) {
            final EditText e = (EditText) v;

            Intent i = new Intent(Intent.ACTION_SEND);
            i.setType("text/plain");
            i.putExtra(Intent.EXTRA_TEXT, e.getText().toString());
            setShareIntent(i);

        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        // Inflate menu resource file.
        getMenuInflater().inflate(R.menu.receivingdata_main, menu);

        // Locate MenuItem with ShareActionProvider
        MenuItem item = menu.findItem(R.id.menu_item_share);

        // Fetch and store ShareActionProvider
        mShareActionProvider = (ShareActionProvider) item.getActionProvider();

        setShareIntent(mIntent);

        // Return true to display menu
        return true;
    }

    // Call to update the share intent
    private void setShareIntent(Intent shareIntent) {
        if (mShareActionProvider != null) {
            mShareActionProvider.setShareIntent(shareIntent);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.menu_item_share) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
