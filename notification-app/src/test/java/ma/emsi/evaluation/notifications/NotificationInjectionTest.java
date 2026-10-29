package ma.emsi.evaluation.notifications;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.system.CapturedOutput;
import org.springframework.boot.test.system.OutputCaptureExtension;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(OutputCaptureExtension.class)
class NotificationInjectionTest {

    @Test
    void qualifierChoosesEmailWhenBothServicesExist(CapturedOutput output) {
        try (var context = new AnnotationConfigApplicationContext(
                EmailNotificationService.class, SmsNotificationService.class, NotificationManager.class)) {
            assertThat(context.getBeansOfType(NotificationService.class)).hasSize(2);

            context.getBean(NotificationManager.class).envoyerNotification("Test de qualification");

            assertThat(output).contains("[EMAIL] Test de qualification");
            assertThat(output).doesNotContain("[SMS]");
        }
    }

    @Test
    void smsImplementationIsAvailableAsAnIndependentService(CapturedOutput output) {
        try (var context = new AnnotationConfigApplicationContext(SmsNotificationService.class)) {
            context.getBean(NotificationService.class).envoyer("Test SMS");

            assertThat(output).contains("[SMS] Test SMS");
            assertThat(output).doesNotContain("[EMAIL]");
        }
    }
}
