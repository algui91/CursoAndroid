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

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;

public class MainActivity extends FragmentActivity 
        implements HeadlinesFragment.OnHeadlineSelectedListener {

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        /**
         * En éste layout se especifican los dos fragment que se usarán.
         * Además, para pantallas consideradas lo suficientemente grandes (layout-large), 
         * se mostrarán ambos fragment a la vez.
         */
        setContentView(R.layout.news_articles);

        /*
         * Si existe un ID con ese nombre, el dispositivo ejecutando la aplicación
         * no tiene una pantalla lo suficientemente grande como para mostrar los 
         * dos fragment a la vez, por lo tanto se usa un layout alternativo que mostrará
         * uno.
         */
        if (findViewById(R.id.fragment_container) != null) {

            /*
             * Si se está restaurando la actividad, no es necesario hacer nada. 
             * De lo contrario podemos solapar los fragments. 
             */
            if (savedInstanceState != null) {
                return;
            }

            // Crear el fragment que será colocado en la actividad
            HeadlinesFragment firstFragment = new HeadlinesFragment();

            /*
             * En caso de que la actividad se haya lanzado con instrucciones especiales
             * desde  un Intent, pasamos la información al fragment.
             */
            firstFragment.setArguments(getIntent().getExtras());

            /*
             * Añadir el fragment al fragment_container (layout), cuando es un disp. pequeño.
             * Al haber añádido el fragment en tiempo de ejecución en lugar de XML, se podrá 
             * quitar posteriormente y reemplazar por otro.
             * 
             * En layout-large, los fragments están declarados en XML, ya que no será necesario
             * quitarlos o añadirlos de la actividad.
             */
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.fragment_container, firstFragment).commit();
        }
    }

    /**
     * Para poder comunicar los fragments con las actividades, se ha implementado
     * una interfaz creada en el fragment HeadlinesFragment.
     */
    public void onArticleSelected(int position) {
        // Se ha seleccionado la Título de un artículo desde el HeadLinesFragment

        // Obtener el fragment del artículo de la actividad
        ArticleFragment articleFrag = (ArticleFragment)
                getSupportFragmentManager().findFragmentById(R.id.article_fragment);

        if (articleFrag != null) {
            // Si al obtener el fragmen no era null, estamos en la versión de dos paneles
            
            // Llamamos al método  de ArticleFragment para actualizar su contenido
            articleFrag.updateArticleView(position);

        } else {
            // Si null, estamos en la versión de un solo fragment a la vez

            // Se crea el fragment y se le pasa el artículo seleccionado.
            ArticleFragment newFragment = new ArticleFragment();
            Bundle args = new Bundle();
            args.putInt(ArticleFragment.ARG_POSITION, position);
            newFragment.setArguments(args);
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

            /*
             * Se reemplaza el contenido del fragment_container con éste nuevo fragment.
             * Se añade la transacción a la pila para poder restaurar los dos títulos si
             * se pulsa el botón de atrás.
             */
            transaction.replace(R.id.fragment_container, newFragment);
            transaction.addToBackStack(null);

            // Aplicar los cambios
            transaction.commit();
        }
    }
}