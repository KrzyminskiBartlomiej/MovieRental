package com.rental.authorization;

import com.rental.utils.XmlWorker;

/**
 * Represents a single registration module which authenticates and provides possibility to log into program.<p>
 * Controls user input and xml file.
 *
 * @author Piotr Nawrocki
 */
public class Registration {
    private XmlWorker xml = new XmlWorker();

    /**
     * Creates a set of attributes necessary to receive the user as 'registered'.
     */
    public void signUp() {
        int userIdNumber = XmlWorker.userList.getLength();
        userIdNumber++;
        String userIdAsString = Integer.toString(userIdNumber);
        xml.createUserElementsAndValuesInBase(userIdAsString);
    }
}
