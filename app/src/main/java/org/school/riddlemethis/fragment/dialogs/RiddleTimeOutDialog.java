package org.school.riddlemethis.fragment.dialogs;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import org.school.riddlemethis.R;
import org.school.riddlemethis.interfaces.DialogTimeOutCallback;

public class RiddleTimeOutDialog extends DialogFragment {

    private DialogTimeOutCallback okay_btn_listener;

    private TextView hint_for_riddle_time_out;

    private Button btn_dialog_okay;

    private ImageView ic_riddle_time_out;

    private static final String ARG_RIDDLE_HINT = "riddle_hint";

    private String riddle_hint;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        try {
            okay_btn_listener = (DialogTimeOutCallback) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context
                    + " must implement DialogListener ");
        }
    }

    public RiddleTimeOutDialog() {
    }


    //just for the hint answer
    public static RiddleTimeOutDialog newInstance(String riddle_hint) {
        Log.i("RiddleWrongDialog", "newInstance: Works!");
        RiddleTimeOutDialog riddleWrongDialog = new RiddleTimeOutDialog();
        Bundle args = new Bundle();
        args.putString(ARG_RIDDLE_HINT, riddle_hint);
        riddleWrongDialog.setArguments(args);
        return riddleWrongDialog;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            //test
            Bundle bundle = getArguments();
            riddle_hint = bundle.getString(ARG_RIDDLE_HINT);
        } else {
            Log.i("RiddleDialog", "onCreate: theArgumentsAreEmpty");
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        okay_btn_listener = (DialogTimeOutCallback) getContext();
        // for inflating and finds ids
        View view = findViews(inflater, container);
        //setting the hint into the dialog % the hint came from the newInstance from this Class
        hint_for_riddle_time_out.setText(riddle_hint);

        setViewsAnimation();

        //when btn inside dialog is clicked == will run my custom listener "okay_btn_listener" AND dismiss();
        btn_dialog_okay.setOnClickListener(view1 -> {
            if (okay_btn_listener != null) {
                okay_btn_listener.onClickForTimeOut();
                dismiss();
            }
        });
        return view;
    }
    // Inflate the layout for this fragment
    private View findViews(LayoutInflater inflater, ViewGroup container) {
        View view = inflater.inflate(R.layout.dialog_time_out, container, false);

        hint_for_riddle_time_out = view.findViewById(R.id.hint_for_riddle_time_out);
        btn_dialog_okay = view.findViewById(R.id.btn_riddle_time_out);

        ic_riddle_time_out = view.findViewById(R.id.ic_riddle_time_out);

        return view;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        clearViewsAnimation();
    }

    private void setViewsAnimation(){
        Animation animation = AnimationUtils.loadAnimation(requireContext(),R.anim.bounce);
        ic_riddle_time_out.startAnimation(animation);
    }
    private void clearViewsAnimation(){
        ic_riddle_time_out.clearAnimation();
    }


    @Override
    public void onDismiss(@NonNull DialogInterface dialog) {
        super.onDismiss(dialog);
        //TODO PREVENT THE USER FROM Dismiss
        //TODO PREVENT THE USER FROM Dismiss
        //TODO PREVENT THE USER FROM Dismiss
        //TODO PREVENT THE USER FROM Dismiss
        //TODO PREVENT THE USER FROM Dismiss
        //TODO PREVENT THE USER FROM Dismiss
        //TODO PREVENT THE USER FROM Dismiss
        //TODO PREVENT THE USER FROM Dismiss
        //TODO PREVENT THE USER FROM Dismiss
    }
}
