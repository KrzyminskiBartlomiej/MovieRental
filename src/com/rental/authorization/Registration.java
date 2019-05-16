package com.rental.authorization;

import com.rental.utils.XmlWorker;
/**
 * This class represents a single registration module which authenticates and provides possibility to log into program.
 * Controls user input and xml file.
 *
 * @since 1.0
 * @author Piotr Nawrocki
 */
public class Registration {
    private XmlWorker xml = new XmlWorker();
    /**
     * Creates a set of attributes necessary to receive the user as 'registered'.
     */
    public void signUp() {
        int idLength = xml.userList.getLength();
        idLength++;
        String idLengthAsString = Integer.toString(idLength);
        xml.createUserElementsAndValuesInBase(idLengthAsString);
    }
}