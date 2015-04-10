package es.tformacion.menus;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.view.ActionMode;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnLongClickListener;
import android.view.ViewGroup;
import android.widget.AbsListView.MultiChoiceModeListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

public class MenusMainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.menus_activity_main);

        if (savedInstanceState == null) {
            getFragmentManager().beginTransaction()
                    .add(R.id.container, new PlaceholderFragment())
                    .commit();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    /*
     * Método encargado de manejar los clicks en los elementos del menú. Debe
     * devolver verdadero si se ha consumido el evento, falso en otro caso.
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        String elemento = "";
        boolean consumed = false;

        int i = item.getItemId();
        if (i == R.id.action_settings) {
            elemento = "settings";
            consumed = true;

        } else if (i == R.id.item1) {
            elemento = "item1";
            consumed = true;

        } else if (i == R.id.item11) {
            elemento = "item11";
            consumed = true;

        } else if (i == R.id.item12) {
            elemento = "item12";
            consumed = true;

        } else if (i == R.id.item2) {
            elemento = "item2";
            consumed = true;

        } else {
            return super.onOptionsItemSelected(item);
        }
        Toast.makeText(this, "Elemento " + elemento + " pulsado", Toast.LENGTH_SHORT).show();

        return consumed;
    }

    /*
     * Decomentando estos dos métodos se crea un menú contextual al mantener
     * pulsado el textview, Sin embargo está anticuado, usaremos el Contextual
     * Action Mode
     */
//     @Override
//     public void onCreateContextMenu(ContextMenu menu, View v, ContextMenuInfo
//     menuInfo) {
//     super.onCreateContextMenu(menu, v, menuInfo);
//     getMenuInflater().inflate(R.menu.main, menu);
//     }
//    
//     @Override
//     public boolean onContextItemSelected(MenuItem item) {
//     switch (item.getItemId()) {
//     case R.id.item1:
//     break;
//     default:
//     return super.onContextItemSelected(item);
//     }
//     }

    public static class PlaceholderFragment extends Fragment {

        // Contextual Action mode para una sola vista
        private ActionMode.Callback mActionModeCallback;

        private ActionMode mActionMode;

        private ArrayList<String> mItems = new ArrayList<String>();

        private SelectionAdapter mAdapter;

        public PlaceholderFragment() {

            mActionModeCallback = new ActionMode.Callback() {

                // Se llama al crear el action mode, Cuando se llama a
                // startActionMode()
                @Override
                public boolean onCreateActionMode(ActionMode mode, Menu menu) {
                    MenuInflater inflater = mode.getMenuInflater();
                    inflater.inflate(R.menu.contex_menu, menu);
                    return true;
                }

                /*
                 * Se llama cada vez que se muestra el action mode. Siempre es
                 * llamado después de onCreateActionMode, pero puede ser llamada
                 * varias veces si se invalida el menú
                 */
                @Override
                public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
                    return false; // Devolver falso si no hacemos nada
                }

                // Llamado cuando se selecciona una opción
                @Override
                public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
                    int i = item.getItemId();
                    if (i == R.id.item1) {
                        Toast.makeText(getActivity(), "Item1", Toast.LENGTH_SHORT).show();
                        mode.finish(); // Cerrar el CAB una vez escogida
                        // acción
                        return true;
                    } else {
                        return false;
                    }
                }

                // Llamado cuando se sale del action mode
                @Override
                public void onDestroyActionMode(ActionMode mode) {
                    Toast.makeText(getActivity(), "Finalizado", Toast.LENGTH_SHORT).show();
                    mActionMode = null;
                }
            };

        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.menus_fragment_main, container, false);
            TextView textview = (TextView) rootView.findViewById(R.id.textview);
            textview.setText(Ipsum.Articles[0]);

            textview.setOnLongClickListener(new OnLongClickListener() {

                @Override
                public boolean onLongClick(View v) {
                    if (mActionMode != null) {
                        return false;
                    }

                    // Start the CAB using the ActionMode.Callback defined above
                    mActionMode = getActivity().startActionMode(mActionModeCallback);
                    v.setSelected(true);
                    mActionMode.setTitle("Title");
                    mActionMode.setSubtitle("SubTitle");
                    return true;
                }
            });

            // Para el menu contextual
            // registerForContextMenu(textview);

            // Ejemplo de la lista sacado de
            // http://stackoverflow.com/questions/10598348/multiple-selection-in-custom-listview-with-cab
            final ListView listView = (ListView) rootView.findViewById(R.id.listView1);

            for (int i = 0; i < 24; i++) {
                mItems.add("Elemento " + i);
            }

            mAdapter = new SelectionAdapter(getActivity(),
                    R.layout.adapters_cabselection_row, R.id.the_text, mItems);
            listView.setAdapter(mAdapter);
            listView.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE_MODAL);

            // Aquí se implementa implícitamente el ActionMode.Callback, no es necesario
            // crearlo nosotros mismos, así como tampoco lo es registrar el onLongclickListener,
            // Al ser una lista fijada con CHOICE_MODE_MULTIPLE_MODAL se registra automáticamente.
            // Puede verse éste comportamiento en https://github.com/android/platform_frameworks_base/blob/master/core/java/android/widget/AbsListView.java#L1159
            listView.setMultiChoiceModeListener(new MultiChoiceModeListener() {

                private int nr = 0;

                @Override
                public boolean onCreateActionMode(ActionMode mode, Menu menu) {
                    MenuInflater inflater = mode.getMenuInflater();
                    inflater.inflate(R.menu.cabselection_menu, menu);
                    return true;
                }

                @Override
                public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
                    return false;
                }

                @Override
                public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
                    StringBuilder sb = new StringBuilder();
                    Set<Integer> positions = mAdapter.getCurrentCheckedPosition();
                    for (Integer pos : positions) {
                        sb.append(" " + pos + ",");
                    }
                    int i = item.getItemId();
                    if (i == R.id.edit_entry) {
                        Toast.makeText(getActivity(), "Edited entries: " + sb.toString(),
                                Toast.LENGTH_SHORT).show();

                    } else if (i == R.id.delete_entry) {
                        Toast.makeText(getActivity(), "Deleted entries : " + sb.toString(),
                                Toast.LENGTH_SHORT).show();

                    } else if (i == R.id.finish_it) {
                        nr = 0;
                        mAdapter.clearSelection();
                        Toast.makeText(getActivity(), "Finish the CAB!",
                                Toast.LENGTH_SHORT).show();
                        mode.finish();
                    }
                    return false;
                }

                @Override
                public void onDestroyActionMode(ActionMode mode) {
                    mAdapter.clearSelection();
                }

                @Override
                public void onItemCheckedStateChanged(ActionMode mode,
                        int position, long id, boolean checked) {
                    if (checked) {
                        nr++;
                        mAdapter.setNewSelection(position, checked);
                    } else {
                        nr--;
                        mAdapter.removeSelection(position);
                    }
                    mode.setTitle(nr + " rows selected!");

                }

            });

            return rootView;
        }
    }

    private static class SelectionAdapter extends ArrayAdapter<String> {

        // Un hashmap Optimizado para <Integer, Boolean>
        // (http://stackoverflow.com/a/18822927/1612432)
        private HashMap<Integer, Boolean> mSelection = new HashMap<Integer, Boolean>();

        public SelectionAdapter(Context context, int resource,
                int textViewResourceId, List<String> objects) {
            super(context, resource, textViewResourceId, objects);
        }

        public void setNewSelection(int position, boolean value) {
            mSelection.put(position, value);
            notifyDataSetChanged();
        }

        public boolean isPositionChecked(int position) {
            Boolean result = mSelection.get(position);
            return result == null ? false : result;
        }

        public Set<Integer> getCurrentCheckedPosition() {
            return mSelection.keySet();
        }

        public void removeSelection(int position) {
            mSelection.remove(position);
            notifyDataSetChanged();
        }

        public void clearSelection() {
            mSelection = new HashMap<Integer, Boolean>();
            notifyDataSetChanged();
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View v = super.getView(position, convertView, parent);// let the
            // adapter
            // handle
            // setting up
            // the row
            // views
            v.setBackgroundColor(Color.parseColor("#99cc00")); // default color
            if (mSelection.get(position) != null) {
                v.setBackgroundColor(Color.RED);// this is a selected position
                // so make it red
            }
            return v;
        }

    }

}
