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

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Los fragment son como módulos de una activity, con su propio layout y 
 * su propio ciclo de vida. Reciben sus propios eventos y pueden ser 
 * añadidos o elminados de la actividad mientras ésta se encuentra en 
 * ejecución. Digamos que es como una sub-actividad que puede ser reusada en 
 * múltiples actividades.
 * 
 * Para crear una crear un Fragment, al igual que con las actividades, extendemos
 * de la clase Fragment e implementamos los métodos del ciclo de vida necesarios.
 * La principal diferencia es que se debe usar el callback @see{#onCreateView()}
 * para definir el layout. Es la única función obligatoria.
 * 
 * @see http://developer.android.com/training/basics/fragments
 */
public class ArticleFragment extends Fragment {
    final static String ARG_POSITION = "position";
    int mCurrentPosition = -1;

    /**
     * Además de este método del ciclo de vida, se deberá implementar 
     * cualquier método necesario para el correcto funcionamiento de nuestro
     * fragment. Por ejemplo, cuando se llama al onPause() de la actividad, 
     * todos los onPause de los fragment que la compongan serán llamados también.
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, 
        Bundle savedInstanceState) {

        /* 
         * Si la actividad se ha re-creado (Por una rotación de pantalla, por ejemplo), 
         * se restaura la selección del artículo establecido por onSaveInstanceState().
         * Es especialmente necesario en el layout a dos paneles.
         */
        if (savedInstanceState != null) {
            mCurrentPosition = savedInstanceState.getInt(ARG_POSITION);
        }

        return inflater.inflate(R.layout.article_view, container, false);
    }

    @Override
    public void onStart() {
        super.onStart();

        /*
         * Durante el inicio, comprobar si se le han pasado argumentos al fragment.
         */
        Bundle args = getArguments();
        if (args != null) {
            // Elegir el artículo a mostrar en base a la posición seleccionada
            updateArticleView(args.getInt(ARG_POSITION));
        } else if (mCurrentPosition != -1) {
            // Elegir el artículo en base a un estado previo debido a una instancia guardada.
            updateArticleView(mCurrentPosition);
        }
    }

    public void updateArticleView(int position) {
        TextView article = (TextView) getActivity().findViewById(R.id.article);
        article.setText(Ipsum.Articles[position]);
        mCurrentPosition = position;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        // Guardar la selección actual del artículo en caso de que tengamos que volver a crear el fragment.
        outState.putInt(ARG_POSITION, mCurrentPosition);
    }
}