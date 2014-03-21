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

package com.example.android.lifecycle;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.android.lifecycle.util.StatusTracker;
import com.example.android.lifecycle.util.Utils;

/**
 * Actividad de ejemplo para demostrar el ciclo de vida de las actividades
 * 
 * Conforme el usuario navega por la aplicación, la instancias de las
 * Actividades de la aplicación pasan por diferentes estados de su ciclo de
 * vida. Por ejemplo, cuando una actividad se inicia por primera vez, se muestra
 * en primer plano y recibe el foco. Durante éste proceso Android llama a una
 * serie de métodos del ciclo de vida de la actividad en los cuales se debe
 * inicializar la interfaz gráfica y otros componenetes. Si el usuario realiza
 * otra acción que inicie otra actividad, el sistema llamará a otro conjunto de
 * métodos del ciclo de vida conforme se mueve a segundo plano. 
 * 
 * En segundo plano la actividad no es visible, pero la instancia y su estado
 * permanecen intactos.
 * 
 * Dentro de los métodos del ciclo de vida se puede declarar cómo se comportará 
 * la aplicación cuando el usuario abandona o entra en la actividad. Por ejemplo,
 * en una aplicación de video en streaming, se deberá pausar el vídeo y detener
 * la conexión de datos cuando el usuario inicie otra aplicación. Cuando regrese, 
 * se reconectará a la red y se permitirá reproducir el vídeo por donde se quedó.
 * 
 * El ciclo de vida se puede ver en @see{http://developer.android.com/training/basics/activity-lifecycle/starting.html#lifecycle-states}
 * Normalemente no es necesario implementar todos los métodos del ciclo de vida,
 * pero implementar los adecuados asegurará que la aplicación:
 * 
 * - No falla si se recibe una llamada de teléfono o se cambia a otra aplicación.
 * - No consume más recursos de los necesarios cuando el usuario no la está usando activamente.
 * - No pierde el progreso del usuario si se abandona la app o se regresa pasado un tiempo.
 * - No falla o pierde el progreso del usuario cuando la pantalla cambia de orientación.
 * 
 * La actividad solo puede existir durante largos períodos de tiempo en uno de éstos tres estados:
 * 
 * - Resumed: 
 *      Está en primer plano y el usuario puede interactuar con ella. (Ejecutándose)
 * - Paused:
 * 		Parcialmente oculta por otra actividad, es decir, la actividad ahora en primer
 * 		plano es semi transparente o no ocupa la pantalla entera. No interactua con el usuario
 * 		ni ejecuta código. 
 * - Stopped:
 * 		Completamente oculta al usuario, en segundo plano. No interactua con el usuario
 * 		ni ejecuta código.
 * 
 * @see <a href="http://developer.android.com/training/basics/activity-lifecycle/starting.html">Android Docs</a>
 */
public class ActivityA extends Activity {

	// Miembros de la Clase
    private String mActivityName;
    private TextView mStatusView;
    private TextView mStatusAllView;
    private StatusTracker mStatusTracker = StatusTracker.getInstance();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        /**
         * Método encargado de “inflar” la actividad. Inicializar cada
         * componente de la actividad con su correspondiente View.
         */
        setContentView(R.layout.activity_a);
        
        mActivityName = getString(R.string.activity_a);
        
        // Obtenemos una referencia a las Vistas que queremos manipular
        mStatusView = (TextView)findViewById(R.id.status_view_a);
        mStatusAllView = (TextView)findViewById(R.id.status_view_all_a);
        mStatusTracker.setStatus(mActivityName, getString(R.string.on_create));
        
        Utils.printStatus(mStatusView, mStatusAllView);
    }

    @Override
    protected void onStart() {
        super.onStart();
        mStatusTracker.setStatus(mActivityName, getString(R.string.on_start));
        Utils.printStatus(mStatusView, mStatusAllView);
    }

    /**
     * Si el usuario abre la ventana de apps recientes y cambia de nuestra app
     * a otra, la actividad se detiene (onStop), si el usuario vuelve a abrir 
     * la aplicación, la actividad se reinicia (onRestart).
     * 
     * El usuario realiza una acción en nuestra app que inicia una nueva activity,
     * la activit y actual se detiene cuando la nueva se crea. Si el usuario presiona
     * la tecla de atrás, la primera actividad se reinicia.
     * 
     * El usuario recibe una llamada de teléfono.
     * 
     */
    @Override
    protected void onRestart() {
        super.onRestart();
        mStatusTracker.setStatus(mActivityName, getString(R.string.on_restart));
        Utils.printStatus(mStatusView, mStatusAllView);
    }

    /**
     * Cuando el usuario reanuda una actividad que estaba en pausa, el sistema llama a onResume.
     * Éste método se llama cada vez que la actividad pasa a primer plano, incluso cuando se 
     * crea por primera vez. 
     * 
     * Aquí se deberían inicializar los componentes que se liberaron durante el estado onPause y 
     * cualquier otra inicialización que deba producirse cada vez que la actividad entra en éste 
     * estado. Como por ejemplo comenzar las animaciones e inicializar los componentes usados 
     * únicamente cuando la actividad tiene el foco.
     */
    @Override
    protected void onResume() {
        super.onResume();
        mStatusTracker.setStatus(mActivityName, getString(R.string.on_resume));
        Utils.printStatus(mStatusView, mStatusAllView);
    }

    /**
     * Durante el uso normal de una app, la actividad en primer plano puede ser obstruida
     * por otros componentes visuales que causan una llamada a éste método. Por ejemplo
     * cuando se ejecutan actividades semi transparentes. Permanecerá en estado de pausa
     * tanto tiempo como esté parcialmente visible y no tenga el foco. 
     * 
     * Cuando es obstruida por completo y no es visible estará detenida (onStop).
     * 
     * En éste método se deberán detener ciertas acciones que se estén llevando a cabo y 
     * no deberían continuar mientras la actividad esté en pausa, como por ejemplo pausar
     * un vídeo. Tambíen se debe guardar cualquier dato que deba recuperarse cuando vuelva 
     * a abrirse la actividad. 
     * 
     * Si el usuario vuelve a nuestra actividad desde este estado, el sistema la reanuda llamando
     * a onResume()
     * 
     * Éste método suele ser un indicador de que el usuario va a abandonar la actividad y pasará
     * de un momento a otro al estado detenida (onStop). Se debe usar éste método para:
     * 
     * - Detener animaciones o acciones ejecutandose que consuman CPU.
     * - Guardar los cambios realizados, pero sólo si el usuario espera que dichos datos deban ser
     *   salvados (Como un borrador de correo)
     * - Liberar recursos del sistema, como Broadcast Receivers, sensores o cualquier recurso que 
     *   consuma batería.
     *   
     * Básicamente, en éste método no se pueden almacenar cambios del usuario a almacenamiento permanente, 
     * ya que puede retrasar la transición entre estados. Éstas operaciones hay que realizarlas en onStop
     * 
     * @see <a href="http://developer.android.com/training/basics/activity-lifecycle/pausing.html">Android Docs</a>
     */
    @Override
    protected void onPause() {
        super.onPause();
        mStatusTracker.setStatus(mActivityName, getString(R.string.on_pause));
        Utils.printStatus(mStatusView, mStatusAllView);
    }

    /**
     * Si el usuario abre la ventana de apps recientes y cambia de nuestra app
     * a otra, la actividad se detiene (onStop), si el usuario vuelve a abrir 
     * la aplicación, la actividad se reinicia (onRestart).
     * 
     * El usuario realiza una acción en nuestra app que inicia una nueva activity,
     * la activit y actual se detiene cuando la nueva se crea. Si el usuario presiona
     * la tecla de atrás, la primera actividad se reinicia.
     * 
     * El usuario recibe una llamada de teléfono.
     * 
     * En contra del estado pausado, que significa que hay algo que obstruye parcialmente
     * la interfaz de nuestra actividad, el estado detenido garantiza que la UI no está 
     * visible y el usuario no tiene el foco.
     * 
     * Debido a que el sistema mantiene una instancia de la actividad en memoria, en la 
     * mayoría de los casos no será necesario implementar OnStop, onRestart e incluso
     * onStart. Gran parte de las actividades son relativamente simples, se detendrán
     * y reiniciarán correctamente, posiblemente sólo sea necesario implementar onPause
     * para detener las tareas en ejecución.
     * 
     * Se debén liberar todos los recursos que puedan producir fugas de memoria. Aunque
     * onStop se llama después de onPause, en onStop se deben realizar tareas más pesadas
     * para la CPU, como escribir a la BD.
     */
    @Override
    protected void onStop() {
        super.onStop();
        mStatusTracker.setStatus(mActivityName, getString(R.string.on_stop));
    }

    /**
     * OnCreate() es el primer método llamado, éste es el último. Una vez ejecutado
     * la actividad será eliminada por completo de la memoria del sistema.
     * 
     * Normalmente no se implementa éste método, ya que todas las referencias locales
     * se destruyen con la actividad y se debería realizar toda la limpieza en los métodos
     * onPause y onStop. Sin embargo, de usar threads en segundo plano o recursos de 
     * larga duración que puedan hacer que el sistema pierda memória, deberán finalizarse aquí.
     */
    @Override
    protected void onDestroy() {
        super.onDestroy();
        mStatusTracker.setStatus(mActivityName, getString(R.string.on_destroy));
        mStatusTracker.clear();
    }

    public void startDialog(View v) {
        Intent intent = new Intent(ActivityA.this, DialogActivity.class);
        startActivity(intent);
    }

    public void startActivityB(View v) {
        Intent intent = new Intent(ActivityA.this, ActivityB.class);
        startActivity(intent);
    }

    public void startActivityC(View v) {
        Intent intent = new Intent(ActivityA.this, ActivityC.class);
        startActivity(intent);
    }

    public void finishActivityA(View v) {
        ActivityA.this.finish();
    }    
}
