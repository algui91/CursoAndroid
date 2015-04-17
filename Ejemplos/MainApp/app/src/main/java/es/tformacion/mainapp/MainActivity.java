package es.tformacion.mainapp;

import android.content.Intent;
import android.os.Bundle;

import es.tformacion.helloworld.HelloWorldMain;
import it.neokree.materialnavigationdrawer.MaterialNavigationDrawer;
import it.neokree.materialnavigationdrawer.elements.MaterialSection;

public class MainActivity extends MaterialNavigationDrawer {

    @Override
    public void init(Bundle bundle) {
        MaterialSection helloWorld = newSection("Hello World", R.mipmap.ic_launcher,
                HelloWorldMain.newInstance());
        MaterialSection dataPers = newSection("Data Persistency", R.mipmap.ic_launcher,
                new Intent("es.tformacion.datapersistency.DataPersistencyMainActivity"));
        MaterialSection basicAdap = newSection("Basic Adapter", R.mipmap.ic_launcher,
                new Intent("es.tformacion.basicadapter.data.ui.BasicAdapterMainActivity"));
        MaterialSection fragmentBasics = newSection("Fragment Basics", R.mipmap.ic_launcher,
                new Intent("es.tformacion.fragmentbasics.FragmentBasicsMainActivity"));
        MaterialSection menus = newSection("Menus", R.mipmap.ic_launcher,
                new Intent("es.tformacion.menus.MenusMainActivity"));
        MaterialSection interact = newSection("Intents", R.mipmap.ic_launcher,
                new Intent("es.tformacion.interact.InteractMainActivity"));
        MaterialSection receive = newSection("Receiving Data", R.mipmap.ic_launcher,
                new Intent("es.tformacion.receivingdata.ReceivingDataMainActivity"));

        addSection(helloWorld);
        addSection(dataPers);
        addSection(basicAdap);
        addSection(fragmentBasics);
        addSection(menus);
        addSection(interact);
        addSection(receive);

        setDefaultSectionLoaded(0);
    }
}
