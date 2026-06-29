package quartz;

import org.quartz.*;
import utils.EmailService;

public class ReportSendingJob implements Job {

    public static final String RECIPIENT_EMAIL_KEY = "recipientEmail";

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        try {
            JobDataMap dataMap = context.getJobDetail().getJobDataMap();
            String recipientEmail = dataMap.getString(RECIPIENT_EMAIL_KEY);

            SchedulerContext schedulerContext = context.getScheduler().getContext();
            EmailService emailService = (EmailService) schedulerContext.get("emailService");

            if (recipientEmail == null || emailService == null) {
                throw new JobExecutionException();
            }

            String subject = "Отчет о проверках точек";
            String body = dataMap.getString("historyData");

            emailService.sendEmail(recipientEmail, subject, body);

        } catch (SchedulerException e) {
            throw new JobExecutionException();
        }
    }
}
