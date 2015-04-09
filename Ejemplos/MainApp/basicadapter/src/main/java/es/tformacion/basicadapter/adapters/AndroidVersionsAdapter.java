/**
 *   This program is free software: you can redistribute it and/or modify
 *   it under the terms of the GNU General Public License as published by
 *   the Free Software Foundation, either version 3 of the License, or
 *   (at your option) any later version.
 *
 *   This program is distributed in the hope that it will be useful,
 *   but WITHOUT ANY WARRANTY; without even the implied warranty of
 *   MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *   GNU General Public License for more details.
 *
 *   You should have received a copy of the GNU General Public License
 *   along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package es.tformacion.basicadapter.adapters;

import android.content.Context;
import android.util.Log;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

import es.tformacion.basicadapter.R;
import es.tformacion.basicadapter.data.AndroidVersions;

/**
 * Clase base para implementaciones de adaptadores para ListView y Spinners El
 * objetivo del adapter consiste en rellenar objetos View con los datos a
 * mostrar. En este ejemplo, los datos son instancias de la clase
 * AndroidVersions.
 *
 * @author Alejandro Alcalde (elbauldelprogramador.com) Licensed under GPLv3
 */
public class AndroidVersionsAdapter extends BaseAdapter {

    private static final String TAG = AndroidVersions.class
            .toString();

    private static int convertViewCounter = 0;

    // Datos
    private ArrayList<AndroidVersions> mVersionList;

    private LayoutInflater lInflater;

    private Context mContext;

    // PAra controlar qué elementos están seleccionados
    private SparseBooleanArray mSelectedItemsIds;

    public AndroidVersionsAdapter(Context context,
            ArrayList<AndroidVersions> versions) {
        Log.v(TAG, "Constructing CustomAdapter");
        mSelectedItemsIds = new SparseBooleanArray();
        mContext = context;
        lInflater = LayoutInflater.from(context);
        mVersionList = versions;
    }

    @Override
    public int getCount() {
        Log.v(TAG, "in getCount()");
        return mVersionList.size();
    }

    @Override
    public Object getItem(int position) {
        Log.v(TAG, "in getItem() for position " + position);
        return mVersionList.get(position);
    }

    @Override
    public long getItemId(int position) {
        Log.v(TAG, "in getItemId() for position " + position);
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;

        Log.v(TAG, "in getView for position " + position + ", convertView is "
                + ((convertView == null) ? "null" : "being recycled"));

        /*
         * Si convertView es un valor no nulo, el ListView está reusando el
         * objeto View, evitanto inflar de nuevo el layout XML y hacer las
         * llamadas a findViewById() para cada View de la fila. Al vincular el
         * objeto ViewHolder a un View (con setTag()) se reciclará mucho más
         * rápido la próxima vez que sea necesario mostrarla. Así, sólo hace
         * falta recuperar el ViewHolder y asignarle los datos correspondientes
         * al View.
         */
        // Crear una instancia nueva
        if (convertView == null) {

            convertViewCounter++;
            Log.v(TAG, convertViewCounter + " convertViews have been created");

            convertView = lInflater.inflate(R.layout.version_list_layout, null);

            holder = new ViewHolder();
            holder.codeNameTV = (TextView) convertView.findViewById(R.id.codenameTV);
            holder.versionNumberTV = (TextView) convertView.findViewById(R.id.versionNumberTV);
            holder.logoIV = (ImageView) convertView.findViewById(R.id.logoIV);

            convertView.setTag(holder);
        } else
        // Hacer el reciclaje
        {
            holder = (ViewHolder) convertView.getTag();
        }

        // Asignar los datos a las vistas
        AndroidVersions versions
                = (AndroidVersions) getItem(
                position);
        holder.codeNameTV.setText(versions.getNombre());
        holder.versionNumberTV.setText(versions.getVersion());
        holder.logoIV.setImageResource(versions.getLogotipo());

        return convertView;
    }

    /**
     * Elimina un elemento de la lista, es necesario llamar a
     * notifyDataSetChanged para que la interfaz se actualice.
     *
     * @param object Objeto a borrar del conjunto de datos
     */
    public void remove(AndroidVersions object) {
        mVersionList.remove(object);
        notifyDataSetChanged();
    }

    /*
     * Metodos que controlarán la selección de elementos
     */

    /**
     * Devuelve el conjunto de datos
     *
     * @return Los datos
     */
    public ArrayList<AndroidVersions> getAndroidVersions() {
        return mVersionList;
    }

    /**
     * Elmina todos los elementos seleccionados
     */
    public void removeSelection() {
        mSelectedItemsIds = new SparseBooleanArray();
        notifyDataSetChanged();
    }

    /**
     * Selecciona/Deselecciona el elemento en la posición indicada
     *
     * @param position Posición donde se encuentra el elemento
     * @param checked  Estado
     */
    public void selectView(int position, boolean checked) {
        if (checked) {
            mSelectedItemsIds.put(position, checked);
        } else {
            mSelectedItemsIds.delete(position);
        }
        notifyDataSetChanged();
    }

    /**
     * @return El número de elementos seleccionados
     */
    public int getSelectedCount() {
        return mSelectedItemsIds.size();
    }

    /**
     * Ids de los elementos seleccionados
     */
    public SparseBooleanArray getSelectedIds() {
        return mSelectedItemsIds;
    }

    /**
     * Selecciona todos los elementos de la lista
     */
    public void selectAll(ListView lv) {
        int size = mVersionList.size();
        for (int i = 0; i < size; i++) {
            mSelectedItemsIds.put(i, true);
            lv.setItemChecked(i, true);
            notifyDataSetChanged();
        }
    }

    // Clase que donde se mantendrán las vistas para reciclarlas
    static class ViewHolder {

        TextView codeNameTV;

        TextView versionNumberTV;

        ImageView logoIV;
    }
}
