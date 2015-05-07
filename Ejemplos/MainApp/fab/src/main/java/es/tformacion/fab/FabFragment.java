package es.tformacion.fab;

import com.software.shell.fab.ActionButton;

import android.app.Fragment;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


public class FabFragment extends Fragment {

    private ActionButton ab1;
    private ActionButton ab3;
    private ActionButton ab4;
    private ActionButton ab5;
    private ActionButton ab6;

    public FabFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.activity_fab_main, container, false);

        ab1 = (ActionButton) root.findViewById(R.id.action_button);
        ab3 = (ActionButton) root.findViewById(R.id.action_button3);
        ab4 = (ActionButton) root.findViewById(R.id.action_button4);
        ab5 = (ActionButton) root.findViewById(R.id.action_button5);
        ab6 = (ActionButton) root.findViewById(R.id.action_button6);

        configFab(ab1,
                R.drawable.fab_plus_icon,
                ActionButton.Animations.FADE_IN,
                ActionButton.Animations.FADE_OUT,
                getResources().getColor(R.color.fab_material_amber_500),
                getResources().getColor(R.color.fab_material_amber_900)
        );
        configFab(ab3,
                R.drawable.fab_plus_icon,
                ActionButton.Animations.JUMP_FROM_DOWN,
                ActionButton.Animations.JUMP_TO_DOWN,
                getResources().getColor(R.color.fab_material_blue_500),
                getResources().getColor(R.color.fab_material_blue_900)
        );
        configFab(ab4,
                R.drawable.fab_plus_icon,
                ActionButton.Animations.JUMP_FROM_RIGHT,
                ActionButton.Animations.JUMP_TO_RIGHT,
                getResources().getColor(R.color.fab_material_brown_500),
                getResources().getColor(R.color.fab_material_brown_900)
        );
        configFab(ab5,
                R.drawable.fab_plus_icon,
                ActionButton.Animations.ROLL_FROM_DOWN,
                ActionButton.Animations.ROLL_TO_DOWN,
                getResources().getColor(R.color.fab_material_red_500),
                getResources().getColor(R.color.fab_material_red_900)
        );
        configFab(ab6,
                R.drawable.fab_plus_icon,
                ActionButton.Animations.SCALE_UP,
                ActionButton.Animations.SCALE_DOWN,
                getResources().getColor(R.color.fab_material_teal_500),
                getResources().getColor(R.color.fab_material_teal_900)
        );

        return root;
    }

    private void configFab(final ActionButton ab, int icon, ActionButton.Animations animShow, ActionButton.Animations animHide, int color, int colorPressed){
        ab.setImageResource(icon);
        ab.setShowAnimation(animShow);
        ab.setHideAnimation(animHide);
        ab.setButtonColor(color);
        ab.setButtonColorPressed(colorPressed);

        ab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ab.hide();
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        ab.show();
                    }
                }, 2000);
            }
        });
    }
}
