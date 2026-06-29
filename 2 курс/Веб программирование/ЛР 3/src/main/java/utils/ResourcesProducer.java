package utils;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.inject.Disposes;
import jakarta.enterprise.inject.Produces;

@ApplicationScoped
public class ResourcesProducer {
    @Produces
    @ApplicationScoped
    public DatabaseManager produceDatabaseManager() {
        return new DatabaseManager("results-persistence-unit");
    }

    public void disposeDatabaseManager(@Disposes DatabaseManager databaseManager) {
        databaseManager.close();
    }
}
