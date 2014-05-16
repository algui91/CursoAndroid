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

package com.tformacion.adapterbasico.data.ui;

import com.tformacion.adapterbasico.R;
import com.tformacion.adapterbasico.adapters.AndroidVersionsAdapter;
import com.tformacion.adapterbasico.data.AndroidVersions;

import android.app.ListFragment;
import android.os.Bundle;
import android.util.SparseBooleanArray;
import android.view.ActionMode;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView.MultiChoiceModeListener;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * ListFragment que contendrá los datos.
 * 
 * @author Alejandro Alcalde (elbauldelprogramador.com) Licensed under GPLv3
 */
public class AndroidVersionsFragment extends ListFragment {

    // Array con los objetos que queremos mostrar en la lista
    private ArrayList<AndroidVersions> mVersions;
    private ListView mListView;
    // El callback para el ActionMode Menu, usado ahora en lugar de ActionMode.Callback
    private MultiChoiceModeListener myMCML;
    // Adaptador para establecer la correspondencia entre los datos y el listview
    private AndroidVersionsAdapter mAdapter;

    public AndroidVersionsFragment() {
        /*
         * En el ejemplo del menú, implementábamos directamente
         * ActionMode.Callback mActionModeCallback, en esta ocasión instanciamos
         * MultiChoiceModeListener que ya implementa ella misma el callback.
         */
        myMCML = new MultiChoiceModeListener() {

            /*
             * Se llama cada vez que un elemento se selecciona 
             */
            @Override
            public void onItemCheckedStateChanged(ActionMode mode,
                    int position, long id, boolean checked) {
                mAdapter.selectView(position, checked);
                mode.setTitle(mListView.getCheckedItemCount() + " rows selected!");
            }

            /*
             * Llamado cada vez que se pulsa algún botón del menu
             */
            @Override
            public boolean onActionItemClicked(ActionMode mode, MenuItem item) {

                int count = mAdapter.getSelectedCount();
                boolean handled = false;

                switch (item.getItemId()) {
                    case R.id.edit_entry:
                        Toast.makeText(getActivity(), "Edited " + count + " entries: ",
                                Toast.LENGTH_SHORT).show();
                        handled = true;
                        mode.finish();
                        break;
                    case R.id.delete_entry:
                        SparseBooleanArray selected = mAdapter.getSelectedIds();
                        // Obtener los ids seleccionados
                        for (int i = (selected.size() - 1); i >= 0; i--) {
                            if (selected.valueAt(i)) {
                                AndroidVersions selectedItem = (AndroidVersions) mAdapter
                                        .getItem(selected.keyAt(i));
                                // Eliminar los elementos cuyos ids están
                                // seleccionados
                                mAdapter.remove(selectedItem);
                            }
                        }
                        Toast.makeText(getActivity(), "Deleted " + count + " entries",
                                Toast.LENGTH_SHORT).show();
                        mode.finish();
                        handled = true;
                        break;
                    case R.id.select_all_entry:
                        mAdapter.selectAll(mListView);
                        int all = mAdapter.getCount();
                        Toast.makeText(getActivity(), all + " entries selected",
                                Toast.LENGTH_SHORT).show();
                        handled = true;
                        break;
                    case R.id.finish_it:
                        mAdapter.removeSelection();
                        Toast.makeText(getActivity(), "Finish the CAB!",
                                Toast.LENGTH_SHORT).show();
                        mode.finish();
                        handled = true;
                }

                return handled;
            }

            @Override
            public boolean onCreateActionMode(ActionMode mode, Menu menu) {
                mode.getMenuInflater().inflate(R.menu.cabselection_menu, menu);
                return true;
            }

            @Override
            public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
                return false;
            }

            @Override
            public void onDestroyActionMode(ActionMode mode) {
                mAdapter.removeSelection();
            }
        };
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_main, container, false);

        initData();

        mAdapter = new AndroidVersionsAdapter(
                getActivity(), mVersions);
        setListAdapter(mAdapter);

        // Modo de selección
        mListView = (ListView) rootView.findViewById(android.R.id.list);
        mListView.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE_MODAL);
        mListView.setMultiChoiceModeListener(myMCML);

        return rootView;
    }

    private void initData() {
        mVersions =
                new ArrayList<AndroidVersions>();

        for (int i = 0; i < 10; i++) {
            mVersions.add(
                    new AndroidVersions("Froyo" + i, "2.3", R.drawable.froyo_22));
            mVersions.add(
                    new AndroidVersions("Ginger Bread" + i, "2.3.2", R.drawable.gingerbread23));
            mVersions.add(
                    new AndroidVersions("HoneyComb" + i, "3.2", R.drawable.honeycomb32));
            mVersions.add(
                    new AndroidVersions("Ice Cream Sandwich" + i, "4.0", R.drawable.ice_cream_sandwich40));
            mVersions.add(
                    new AndroidVersions("Jelly Bean" + i, "4.1", R.drawable.jelly_bean41));
            mVersions.add(
                    new AndroidVersions("Kit Kat" + i, "4.4", R.drawable.kitkat44));
        }
    }
}
