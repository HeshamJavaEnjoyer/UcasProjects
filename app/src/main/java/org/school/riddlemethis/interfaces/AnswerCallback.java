package org.school.riddlemethis.interfaces;

public interface AnswerCallback {
    void onSuccess(int riddleId);
    void onFailed(int riddleId);
}
