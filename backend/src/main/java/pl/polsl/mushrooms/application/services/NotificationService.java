package pl.polsl.mushrooms.application.services;

import pl.polsl.mushrooms.application.commands.notification.DeleteNotificationCommand;

/**
 * Created by chythe on 2017-07-02.
 */
public interface NotificationService {

    void handle(DeleteNotificationCommand command);
}