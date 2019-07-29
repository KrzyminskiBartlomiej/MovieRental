package com.rental.utils;

import com.rental.controller.AdminPanel;
import com.rental.controller.Panel;
import com.rental.controller.RentalProcessor;
import com.rental.controller.UserPanel;

public class UserRoleFactory {
    public static Panel runPanel(Worker worker) {
        if ((worker.getUserRole().equals(RentalProcessor.ADMIN_ROLE))) {
            return new AdminPanel();
        } else {
            return new UserPanel();
        }
    }
}
