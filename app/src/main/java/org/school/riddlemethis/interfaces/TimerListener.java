package org.school.riddlemethis.interfaces;

public interface TimerListener {
    void setTimerTickDuration(long remainingTime, int riddleId);

    boolean onTimerFinished();
}
