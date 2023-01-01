package org.school.riddlemethis.source_data;

import android.content.Context;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.school.riddlemethis.appController.AppController;
import org.school.riddlemethis.database.db.Repository;
import org.school.riddlemethis.database.models.Levels;
import org.school.riddlemethis.database.models.Riddles;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

public class Utils {

    private static Utils INSTANCE;


    public static Utils getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new Utils();
        }
        return INSTANCE;
    }


    private static final Repository repository = new Repository(AppController.getInstance());

    private static String loadJSONFromAsset(Context context) {
        String json;
        try {
            InputStream inputStream = context.getAssets().open("puzzleGameData.json");

            int size = inputStream.available();

            byte[] buffer = new byte[size];

            int readied = inputStream.read(buffer);
            Log.d("Usage of read method", "int readied = inputStream.read(buffer); returned: " + readied);
            inputStream.close();

            json = new String(buffer, StandardCharsets.UTF_8);
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
        return json;
    }

    public void readRiddles() {
        String id, title, hintAnswer;
        String duration_value;
        String point_value;
        String ans1, ans2, ans3, ans4;
        String trueAnswer;
        //******************************
        String level_num_;
        String level_minPoint_;
        //******************************
        {
            try {
                JSONArray mainArray = new JSONArray(loadJSONFromAsset(AppController.getInstance()));

                for (int h = 0; h < mainArray.length(); h++) {
                    //get Level Info
                    level_num_ = mainArray.getJSONObject(h).getString("level_no");
                    level_minPoint_ = mainArray.getJSONObject(h).getString("unlock_points");

                    int level_num = Integer.parseInt(level_num_);
                    int level_minPoint = Integer.parseInt(level_minPoint_);

                    //repository.insertLevels(new Levels(level_num,level_minPoint,false));
                    Thread.sleep(100);
                    repository.multiInsertLevels(new Levels(level_num, level_minPoint));

                    JSONArray json_questionsArray = mainArray.getJSONObject(h).getJSONArray("questions");

                    for (int i = 0; i < json_questionsArray.length(); i++) {

                        id = json_questionsArray.getJSONObject(i).getString("id");
                        title = json_questionsArray.getJSONObject(i).getString("title");
                        trueAnswer = json_questionsArray.getJSONObject(i).getString("true_answer");
                        hintAnswer = json_questionsArray.getJSONObject(i).getString("hint");
                        //****************
                        duration_value = json_questionsArray.getJSONObject(i).getString("duration");
                        point_value = json_questionsArray.getJSONObject(i).getString("points");
                        //------------------
                        ans1 = json_questionsArray.getJSONObject(i).getString("answer_1");
                        ans2 = json_questionsArray.getJSONObject(i).getString("answer_2");
                        ans3 = json_questionsArray.getJSONObject(i).getString("answer_3");
                        ans4 = json_questionsArray.getJSONObject(i).getString("answer_4");
                        //------------------
                        String patternObject_id = json_questionsArray.getJSONObject(i).getJSONObject("pattern").getString("pattern_id");
//                        String patternObject_name = json_questionsArray.getJSONObject(i).getString("pattern_name");

                        int id_ = Integer.parseInt(id);
                        int pattern_id = Integer.parseInt(patternObject_id);
                        int duration_actual = Integer.parseInt(duration_value);
                        int point_actual = Integer.parseInt(point_value);

                        //Test//Test//Test//Test//Test//Test//Test//Test//Test//Test
                        Thread.sleep(150);
                        repository.multiInsertRiddles(new Riddles(id_, title, point_actual, duration_actual, ans1, ans2, ans3, ans4, trueAnswer, hintAnswer, pattern_id, level_num));

                    }

                }
            } catch (JSONException | InterruptedException e) {
                e.printStackTrace();
            }
        }
        //******************************
    }
}
