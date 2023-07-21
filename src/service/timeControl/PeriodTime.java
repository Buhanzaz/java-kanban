package service.timeControl;

import java.time.Duration;
import java.time.LocalDate;
import java.time.Period;

public class PeriodTime {
    protected LocalDate startTime;
    protected LocalDate endTime;
    protected Duration duration = Duration.ofMinutes(15);
    protected Period period;
    protected boolean freeTime = true;

    public PeriodTime(LocalDate startTime) {
        this.startTime = startTime;
        this.endTime = startTime.plus(duration);
        this.period = Period.between(startTime, endTime);
    }
}
