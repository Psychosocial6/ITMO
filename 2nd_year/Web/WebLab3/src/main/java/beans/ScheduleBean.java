package beans;

import jakarta.enterprise.context.RequestScoped;
import jakarta.enterprise.context.SessionScoped;
import jakarta.faces.context.FacesContext;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import org.primefaces.PrimeFaces;
import org.quartz.*;
import quartz.ReportSendingJob;

import java.io.Serializable;
import java.util.Date;
import java.util.TimeZone;
import java.util.UUID;

@Named
@SessionScoped
public class ScheduleBean implements Serializable {

    private int hours;
    private int minutes;
    private int seconds;
    private String email;
    private String userTimeZoneId;  //= TimeZone.getTimeZone("Europe/Moscow").getID();

    @Inject
    private HistoryBean historyBean;

    @Inject
    private QuartzManager quartzManager;

    public void scheduleEmail() {
        Scheduler scheduler = quartzManager.getScheduler();
        if (scheduler == null) {
            return;
        }
        int totalDelayInSeconds = hours * 3600 + minutes * 60 + seconds;
        if (totalDelayInSeconds <= 0) {
            return;
        }

        try {
            String jobId = "emailJob-" + UUID.randomUUID();
            String triggerId = "emailTrigger-" + UUID.randomUUID();

            JobDetail jobDetail = JobBuilder.newJob(ReportSendingJob.class)
                    .withIdentity(jobId, "email-group")
                    .usingJobData(ReportSendingJob.RECIPIENT_EMAIL_KEY, email)
                    .usingJobData("historyData", historyBean.getHistoryAsString(TimeZone.getTimeZone(userTimeZoneId)))
                    .build();

            Date fireTime = DateBuilder.futureDate(totalDelayInSeconds, DateBuilder.IntervalUnit.SECOND);

            Trigger trigger = TriggerBuilder.newTrigger()
                    .withIdentity(triggerId, "email-group")
                    .startAt(fireTime)
                    .build();

            scheduler.scheduleJob(jobDetail, trigger);

            PrimeFaces.current().executeScript("PF('scheduleDialogWidget').hide()");
            resetForm();

        } catch (SchedulerException e) {
            e.printStackTrace();
        }
    }

    private void resetForm() {
        this.hours = 0;
        this.minutes = 0;
        this.seconds = 0;
        this.email = null;
    }

    public int getHours() { return hours; }
    public void setHours(int hours) { this.hours = hours; }
    public int getMinutes() { return minutes; }
    public void setMinutes(int minutes) { this.minutes = minutes; }
    public int getSeconds() { return seconds; }
    public void setSeconds(int seconds) { this.seconds = seconds; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public String getUserTimeZoneId() { return userTimeZoneId; }
    public void setUserTimeZoneId(String userTimeZoneId) { this.userTimeZoneId = userTimeZoneId; }
}