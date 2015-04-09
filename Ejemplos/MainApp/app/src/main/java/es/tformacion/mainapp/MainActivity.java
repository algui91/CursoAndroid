package es.tformacion.mainapp;

import android.content.Intent;
import android.os.Bundle;

import es.tformacion.helloworld.HelloWorldMain;
import it.neokree.materialnavigationdrawer.MaterialNavigationDrawer;
import it.neokree.materialnavigationdrawer.elements.MaterialSection;

public class MainActivity extends MaterialNavigationDrawer {

    @Override
    public void init(Bundle bundle) {
        MaterialSection helloWorld = newSection("HelloWorld", R.mipmap.ic_launcher, HelloWorldMain.newInstance());
        MaterialSection dataPers = newSection("DataPersistency", R.mipmap.ic_launcher, new Intent("es.tformacion.datapersistency.DataPersistencyMainActivity"));

        addSection(helloWorld);
        addSection(dataPers);
        setDefaultSectionLoaded(0);
    }
}
