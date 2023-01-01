package org.school.riddlemethis.fragment;

import android.content.Context;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import com.google.android.material.snackbar.Snackbar;
import org.school.riddlemethis.R;
import org.school.riddlemethis.enums.RiddleType;
import org.school.riddlemethis.interfaces.AnswerCallback;
import java.util.Locale;
import java.util.Objects;

public class RiddleFragment extends Fragment {
    private MediaPlayer mediaPlayerSuccess, mediaPlayerFailed;
    private AnswerCallback answerCallback;
    private static final String ARG_RIDDLE_ID = "riddleId";
    //(Duration)
    private static final String ARG_RIDDLE_TimeBySec = "TimeBySec";
    private static final String ARG_RIDDLE_TYPE = "riddleTYPE";
    private static final String ARG_RIDDLE_Q = "riddleQ", ARG_RIDDLE_ANS1 = "riddleA1", ARG_RIDDLE_ANS2 = "riddleA2", ARG_RIDDLE_ANS3 = "riddleA3", ARG_RIDDLE_ANS4 = "riddleA4";
    //NOT FOR SHOWING
    private static final String ARG_RIDDLE_IS_TRUE = "riddleTRUE";
    private static final String ARG_RIDDLE_RIGHT_ANS = "rTRUE_ANSWER_IN_TEXT";
    //rID
    int riddleId;
    //rType
    int riddleType;
    //rGeneralQ
    String riddleQ;
    //true or false value for check with user
    boolean isTheQuestionTrue;
    //chooses the c
    String ans1, ans2, ans3, ans4;
    //complete
    String theRightAnswer;

    public RiddleFragment() {
        // Required empty public constructor
    }
    //for declaring the value of the interface
    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (mediaPlayerSuccess == null && mediaPlayerFailed == null) {
            mediaPlayerSuccess = MediaPlayer.create(context, R.raw.riddle_bonus_collect);
            mediaPlayerFailed = MediaPlayer.create(context, R.raw.riddle_negative_answer);
        }

        try {
            answerCallback = (AnswerCallback) context;

        } catch (ClassCastException e) {
            throw new ClassCastException(context + " must implement AnswerCallback ");
        }
    }
    //***********************
    //true or false
    public static RiddleFragment newInstance(int riddleId, String riddleQ, boolean isTheQuestionTrue, int riddleTimeBySec) {
        RiddleFragment fragment = new RiddleFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_RIDDLE_TYPE, RiddleType.TrueOrFalse.riddleTypeNum);

        args.putInt(ARG_RIDDLE_ID, riddleId);

        args.putInt(ARG_RIDDLE_TimeBySec, riddleTimeBySec);


        args.putString(ARG_RIDDLE_Q, riddleQ);

        args.putBoolean(ARG_RIDDLE_IS_TRUE, isTheQuestionTrue);
        fragment.setArguments(args);
        return fragment;
    }
    // choose
    public static RiddleFragment newInstance(int riddleId, String riddleQ, String ans1, String ans2, String ans3, String ans4, String theRightAnswer, int riddleTimeBySec) {
        RiddleFragment fragment = new RiddleFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_RIDDLE_TYPE, RiddleType.ChooseTheCorrectAnswer.riddleTypeNum);

        args.putInt(ARG_RIDDLE_ID, riddleId);

        args.putInt(ARG_RIDDLE_TimeBySec, riddleTimeBySec);


        args.putString(ARG_RIDDLE_Q, riddleQ);

        args.putString(ARG_RIDDLE_RIGHT_ANS, theRightAnswer);


        args.putString(ARG_RIDDLE_ANS1, ans1);
        args.putString(ARG_RIDDLE_ANS2, ans2);
        args.putString(ARG_RIDDLE_ANS3, ans3);
        args.putString(ARG_RIDDLE_ANS4, ans4);
        fragment.setArguments(args);
        return fragment;
    }
    //complete
    public static RiddleFragment newInstance(int riddleId, String riddleQ, String theRightAnswer, int riddleTimeBySec) {
        RiddleFragment fragment = new RiddleFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_RIDDLE_TYPE, RiddleType.CompleteTheSentence.riddleTypeNum);

        args.putInt(ARG_RIDDLE_ID, riddleId);

        args.putInt(ARG_RIDDLE_TimeBySec, riddleTimeBySec);

        args.putString(ARG_RIDDLE_Q, riddleQ);

        args.putString(ARG_RIDDLE_RIGHT_ANS, theRightAnswer);

        fragment.setArguments(args);
        return fragment;
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            //get Type
            riddleType = getArguments().getInt(ARG_RIDDLE_TYPE);
            //id re
            riddleId = getArguments().getInt(ARG_RIDDLE_ID);
            //(Duration)
//            riddleTimeBySec = getArguments().getInt(ARG_RIDDLE_TimeBySec);
            //R_text re
            riddleQ = getArguments().getString(ARG_RIDDLE_Q);

            //true or false
            isTheQuestionTrue = getArguments().getBoolean(ARG_RIDDLE_IS_TRUE);

            //choose
            ans1 = getArguments().getString(ARG_RIDDLE_ANS1);
            ans2 = getArguments().getString(ARG_RIDDLE_ANS2);
            ans3 = getArguments().getString(ARG_RIDDLE_ANS3);
            ans4 = getArguments().getString(ARG_RIDDLE_ANS4);

            //complete && choose for Checking with UI Answer if equal add score
            theRightAnswer = getArguments().getString(ARG_RIDDLE_RIGHT_ANS);

            //****************************************************************************



            //****************************************************************************

        }
    }
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        //Global Var
        TextView textViewQ;
        RadioButton radioButton_ans1, radioButton_ans2, radioButton_ans3, radioButton_ans4;
        Button btnTrue, btnFalse, btnSubmit;
        //*********************[Start InflatingViews]******************************================
        //inflate RiddleType.TrueOrFalse
        if (riddleType == RiddleType.TrueOrFalse.riddleTypeNum) {
            View viewTrue = inflater.inflate(R.layout.fragment_riddle_true, container, false);


            // Inflate the layout for this fragment
            textViewQ = viewTrue.findViewById(R.id.textView_frg_Q_true);

            btnTrue = viewTrue.findViewById(R.id.btn_frag_true);
            btnFalse = viewTrue.findViewById(R.id.btn_frag_false);


            btnTrue.setOnClickListener(view -> {
                if (isTheQuestionTrue) {
                    //Toast.makeText(getActivity(), "You Are Right", Toast.LENGTH_SHORT).show();
                    answerCallback.onSuccess(riddleId);
                    mediaPlayerSuccess.start();
                } else {
                    //Toast.makeText(getActivity(), "You Are wrong", Toast.LENGTH_SHORT).show();
                    answerCallback.onFailed(riddleId);
                    mediaPlayerFailed.start();
                }
            });

            btnFalse.setOnClickListener(view -> {
                if (!isTheQuestionTrue) {
                    //Toast.makeText(getActivity(), "You Are Right", Toast.LENGTH_SHORT).show();
                    answerCallback.onSuccess(riddleId);
                    mediaPlayerSuccess.start();

                } else {
                    //Toast.makeText(getActivity(), "You Are wrong", Toast.LENGTH_SHORT).show();
                    answerCallback.onFailed(riddleId);
                    mediaPlayerFailed.start();

                }
            });

            //set Value
            textViewQ.setText(riddleQ);
            return viewTrue;
        }
        //inflate RiddleType.ChooseTheCorrectAnswer
        else if (riddleType == RiddleType.ChooseTheCorrectAnswer.riddleTypeNum) {
            View viewChooses = inflater.inflate(R.layout.fragment_riddle_choose, container, false);
            // Inflate the layout for this fragment
            textViewQ = viewChooses.findViewById(R.id.textView_frag_Q);
            radioButton_ans1 = viewChooses.findViewById(R.id.radioButton_ans1);
            radioButton_ans2 = viewChooses.findViewById(R.id.radioButton_ans2);
            radioButton_ans3 = viewChooses.findViewById(R.id.radioButton_ans3);
            radioButton_ans4 = viewChooses.findViewById(R.id.radioButton_ans4);
            // radioGroupAnswers= viewChooses.findViewById(R.id.radioGroupAnswers);

            btnSubmit = viewChooses.findViewById(R.id.btn_frag_choose_submit);

            textViewQ.setText(riddleQ);
            radioButton_ans1.setText(ans1);
            radioButton_ans2.setText(ans2);
            radioButton_ans3.setText(ans3);
            radioButton_ans4.setText(ans4);


            //userAnswer fromTV
            btnSubmit.setOnClickListener(view -> {
                String userAnswer = null;
                if (radioButton_ans1.isChecked())
                    userAnswer = radioButton_ans1.getText().toString();
                else if (radioButton_ans2.isChecked())
                    userAnswer = radioButton_ans2.getText().toString();

                else if (radioButton_ans3.isChecked())
                    userAnswer = radioButton_ans3.getText().toString();

                else if (radioButton_ans4.isChecked())
                    userAnswer = radioButton_ans4.getText().toString();
                else {
                    Snackbar.make(viewChooses.findViewById(R.id.guideScreen_from_bottom), "Pick an Answer", Snackbar.LENGTH_LONG).show();
                }

                String finalUserAnswer = null;
                if (userAnswer != null) {
                    finalUserAnswer = userAnswer;
                }

                if (finalUserAnswer != null) {
                    // STOPSHIP: 12/17/2022 HERE FIX ?!!!!!
                    if (Objects.equals(finalUserAnswer, theRightAnswer)) {
                        answerCallback.onSuccess(riddleId);
                        mediaPlayerSuccess.start();
                        //Toast.makeText(getActivity(), "Good job", Toast.LENGTH_SHORT).show();
                    } else {
                        answerCallback.onFailed(riddleId);
                        mediaPlayerFailed.start();
                        // Toast.makeText(getActivity(), "sorry it not right", Toast.LENGTH_SHORT).show();
                    }
                }
            });

            return viewChooses;
        }
        //inflate RiddleType.CompleteTheSentence
        else if (riddleType == RiddleType.CompleteTheSentence.riddleTypeNum) {
            View viewComplete = inflater.inflate(R.layout.fragment_riddle_complete, container, false);
            // Inflate the layout for this fragment
            EditText ed_Input = viewComplete.findViewById(R.id.ed_frag_input);

            textViewQ = viewComplete.findViewById(R.id.textView_frg_Q_complete);
            btnSubmit = viewComplete.findViewById(R.id.btn_frag_complete_submit);

            btnSubmit.setOnClickListener(view -> {
                String userAnswer = ed_Input.getText().toString().trim().toLowerCase(Locale.ROOT);
                if (!userAnswer.isEmpty()) {
                    if (userAnswer.equals(theRightAnswer)) {
                        answerCallback.onSuccess(riddleId);
                        mediaPlayerSuccess.start();
                    } else {
                        answerCallback.onFailed(riddleId);
                        mediaPlayerFailed.start();
                    }
                } else {
                    Snackbar.make(viewComplete.findViewById(R.id.guideScreen_from_bottom), "Enter An Answer", Snackbar.LENGTH_LONG).show();
                }
                //here any way will run
            });

            textViewQ.setText(riddleQ);

            return viewComplete;
        }
        //*********************[End InflatingViews]******************************================
        return null;
    }
    @Override
    public void onDestroy() {
        super.onDestroy();
        destroyMediaPlayer();
    }
    private void destroyMediaPlayer() {
        if (mediaPlayerSuccess != null && mediaPlayerFailed != null) {
            mediaPlayerFailed.stop();
            mediaPlayerSuccess.stop();
            mediaPlayerFailed.release();
            mediaPlayerSuccess.release();
            mediaPlayerSuccess = null;
            mediaPlayerFailed = null;
        }
    }

}