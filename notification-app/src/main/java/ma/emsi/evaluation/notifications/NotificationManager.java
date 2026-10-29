package ma.emsi.evaluation.notifications;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component
public class NotificationManager {

    private final NotificationService notificationService;

    public NotificationManager(
            @Qualifier("emailNotificationService") NotificationService notificationService) {
        this.notificationService = notificationService;
    }

    public void envoyerNotification(String message) {
        notificationService.envoyer(message);
    }
}
