package ma.emsi.evaluation.notifications;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class NotificationApplication implements CommandLineRunner {

    private final NotificationManager notificationManager;

    public NotificationApplication(NotificationManager notificationManager) {
        this.notificationManager = notificationManager;
    }

    public static void main(String[] args) {
        SpringApplication.run(NotificationApplication.class, args);
    }

    @Override
    public void run(String... args) {
        System.out.println("=== Gestion des notifications ===");
        notificationManager.envoyerNotification("Votre commande a été confirmée.");
    }
}
