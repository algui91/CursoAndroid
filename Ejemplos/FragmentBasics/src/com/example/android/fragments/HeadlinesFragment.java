/*
 * Copyright (C) 2012 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.example.android.fragments;

import android.app.Activity;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class HeadlinesFragment extends ListFragment {
    OnHeadlineSelectedListener mCallback;

    /* La actividad contenedora de este fragment debe implementar ésta interfaz para
     * que el fragment pueda enviar mensajes.    
     */
    public interface OnHeadlineSelectedListener {
        /**
         * Llamado por el fragment cuando se selecciona un elemento de la lista
         * 
         * @param position El índice del elemento seleccionado
         */
        public void onArticleSelected(int position);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Seleccionar el layout para la vista según la versión de Android
        int layout = Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB ?
                android.R.layout.simple_list_item_activated_1 : android.R.layout.simple_list_item_1;

        // Crear el array adapter con la información a mostrar
        setListAdapter(new ArrayAdapter<String>(getActivity(), layout, Ipsum.Headlines));
    }

    @Override
    public void onStart() {
        super.onStart();

        /*
         * Cuando estemos en el modo a dos paneles, configurar el listview para 
         * resaltar el elemento seleccionado. En onStart, el listview ya está disponible.
         */
        if (getFragmentManager().findFragmentById(R.id.article_fragment) != null) {
            getListView().setChoiceMode(ListView.CHOICE_MODE_SINGLE);
        }
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        /*
         * Asegurarnos de que la actividad contenedora ha implementado
         * la interfaz.
         */
        try {
            mCallback = (OnHeadlineSelectedListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnHeadlineSelectedListener");
        }
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        // Notificar a la actividad padre del elemento seleccionado.
        mCallback.onArticleSelected(position);
        
        // Resaltar el elemento seleccionado en el modo a dos paneles
        getListView().setItemChecked(position, true);
    }
}