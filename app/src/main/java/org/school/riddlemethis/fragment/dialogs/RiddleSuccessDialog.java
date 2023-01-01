package org.school.riddlemethis.fragment.dialogs;

import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import org.school.riddlemethis.R;

public class RiddleSuccessDialog extends DialogFragment {

    public RiddleSuccessDialog() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        new Handler().postDelayed(this::dismiss,680);
        return inflater.inflate(R.layout.dialog_right_answer, container, false);
    }

}
