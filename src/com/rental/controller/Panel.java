package com.rental.controller;

import com.rental.utils.Worker;

/**
 * Contains set of method for logged in users. Access to this actions, depend of user roles.
 */
public interface Panel {
    /**
     * Contains set of different methods for logged users with different roles.
     */
    void runPanel(Worker worker);
}
