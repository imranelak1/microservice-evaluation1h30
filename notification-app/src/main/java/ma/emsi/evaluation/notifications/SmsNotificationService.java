package ma.emsi.evaluation.notifications;

import org.springframework.stereotype.Service;

/** Simulation console d'un envoi de SMS, sans passerelle externe. */
@Service
public class SmsNotificationService implements NotificationService {

    @Override
    public void envoyer(String message) {
        System.out.println("[SMS] " + message);
    }
}
