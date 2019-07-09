package com.rental.utils;

public class DatabaseFactory {
    public Worker runDatabase() {
        switch (Communicator.selectDatabase()) {
            case 1: {
                return new XmlWorker();
            }
            case 2: {
                return new SqlWorker();
            }
        }
        return null;
    }
}
