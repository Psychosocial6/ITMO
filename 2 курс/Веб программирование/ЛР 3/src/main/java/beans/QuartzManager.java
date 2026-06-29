package beans;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Named;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.impl.StdSchedulerFactory;
import utils.EmailService;

@Named
@ApplicationScoped
public class QuartzManager {
    private Scheduler scheduler;

    @PostConstruct
    public void init() {
        try {
            StdSchedulerFactory sf = new StdSchedulerFactory();
            scheduler = sf.getScheduler();
            scheduler.getContext().put("emailService", new EmailService());
            scheduler.start();
        } catch (SchedulerException e) {
            throw new RuntimeException(e);
        }
    }

    @PreDestroy
    public void destroy() {
        if (scheduler != null) {
            try {
                scheduler.shutdown(true);
            } catch (SchedulerException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public Scheduler getScheduler() {
        return scheduler;
    }
}
