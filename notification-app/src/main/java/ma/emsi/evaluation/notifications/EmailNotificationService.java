package ma.emsi.evaluation.notifications;

import org.springframework.stereotype.Service;

/** Simulation console d'un envoi d'email, sans serveur SMTP. */
@Service
public class EmailNotificationService implements NotificationService {

    @Override
    public void envoyer(String message) {
        System.out.println("[EMAIL] " + message);
    }
}
