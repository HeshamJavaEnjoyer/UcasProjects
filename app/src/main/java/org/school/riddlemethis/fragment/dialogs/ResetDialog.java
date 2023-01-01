package org.school.riddlemethis.fragment.dialogs;

import android.content.Context;
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
import org.school.riddlemethis.interfaces.DialogResetListener;

public class ResetDialog extends DialogFragment {
    private DialogResetListener dialogResetListener;

    private TextView tv_hint_for_delete_forever;

    private Button btn_dialog_submit;

    private ImageView ic_delete_forever;

    private static final String ARG_MESSAGE_HINT = "delete_forever";

    private String delete_forever_hint;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        try {
            dialogResetListener = (DialogResetListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context
                    + " must implement DialogListener ");
        }
    }

    public ResetDialog() {
    }


    //just for the hint answer
    public static ResetDialog newInstance(String delete_forever_hint) {
        Log.i("ResetDialog", "newInstance: Works!");
        ResetDialog resetDialog = new ResetDialog();
        Bundle args = new Bundle();
        args.putString(ARG_MESSAGE_HINT, delete_forever_hint);
        resetDialog.setArguments(args);
        return resetDialog;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            //test
            Bundle bundle = getArguments();
            delete_forever_hint = bundle.getString(ARG_MESSAGE_HINT);
        } else {
            Log.i("RiddleDialog", "onCreate: theArgumentsAreEmpty");
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        dialogResetListener = (DialogResetListener) getContext();
        // for inflating and finds ids
        View view = findViews(inflater, container);
        //setting the hint into the dialog % the hint came from the newInstance from this Class
        tv_hint_for_delete_forever.setText(delete_forever_hint);

        setViewsAnimation();

        //when btn inside dialog is clicked == will run my custom listener "okay_btn_listener" AND dismiss();
        btn_dialog_submit.setOnClickListener(view1 -> {
            if (dialogResetListener != null) {
                //its okay i called this method for getting faster (i should have created another one)
                dialogResetListener.onResetCalled();
                dismiss();
            }
        });
        return view;
    }
    // Inflate the layout for this fragment
    private View findViews(LayoutInflater inflater, ViewGroup container) {
        View view = inflater.inflate(R.layout.dialog_reset, container, false);
        tv_hint_for_delete_forever = view.findViewById(R.id.hint_delete_forever);
        btn_dialog_submit = view.findViewById(R.id.btn_delete_forever);

        ic_delete_forever = view.findViewById(R.id.ic_delete_forever);
        return view;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        clearViewsAnimation();
    }

    private void setViewsAnimation(){
        Animation animation = AnimationUtils.loadAnimation(requireContext(),R.anim.bounce);
        ic_delete_forever.startAnimation(animation);
    }
    private void clearViewsAnimation(){
        ic_delete_forever.clearAnimation();
    }
}
