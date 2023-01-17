package org.school.riddlemethis.fragment.dialogs;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import org.school.riddlemethis.R;
import org.school.riddlemethis.interfaces.DialogListener;

public class RiddleSkipDialog extends DialogFragment {
    private DialogListener okay_btn_listener;

    private TextView tv_text_for_skipping_riddle;

    private Button btn_dialog_okay;

    private static final String ARG_SKIP_TEXT = "SkipText";

    private String textForSkipping;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        try {
            okay_btn_listener = (DialogListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context
                    + " must implement DialogListener ");
        }
    }

    public RiddleSkipDialog() {
    }


    //just for the hint answer
    public static RiddleSkipDialog newInstance(String textForSkipping) {
        RiddleSkipDialog riddleSkipDialog = new RiddleSkipDialog();
        Bundle args = new Bundle();
        args.putString(ARG_SKIP_TEXT, textForSkipping);
        riddleSkipDialog.setArguments(args);
        return riddleSkipDialog;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            //test
            Bundle bundle = getArguments();
            textForSkipping = bundle.getString(ARG_SKIP_TEXT);
        } else {
            Log.i("RiddleDialog", "onCreate: theArgumentsAreEmpty");
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // for inflating and finds ids
        View view = findViews(inflater, container);
        //setting the hint into the dialog % the hint came from the newInstance from this Class
        tv_text_for_skipping_riddle.setText(textForSkipping);
        //when btn inside dialog is clicked == will run my custom listener "okay_btn_listener" AND dismiss();
        btn_dialog_okay.setOnClickListener(view1 -> {
            if (okay_btn_listener != null) {
                okay_btn_listener.onClickForSkippedDialog();
                dismiss();
            }
        });
        return view;
    }
    // Inflate the layout for this fragment
    private View findViews(LayoutInflater inflater, ViewGroup container) {
        View view = inflater.inflate(R.layout.dialog_skipp_riddle, container, false);
        tv_text_for_skipping_riddle = view.findViewById(R.id.tv_text_for_skipping_riddle);
        btn_dialog_okay = view.findViewById(R.id.btn_skip_riddle_okay);
        return view;
    }


}
