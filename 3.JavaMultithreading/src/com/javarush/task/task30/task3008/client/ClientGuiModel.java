package com.javarush.task.task30.task3008.client;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import static java.util.Collections.unmodifiableSet;

/**
 * Created by Mikhail Shamanin on 29.06.2017.
 */
public class ClientGuiModel {
    private final Set <String> allUserNames = Collections.synchronizedSet(new HashSet<String>());
    private String newMessage;

    public Set<String> getAllUserNames() {
        return unmodifiableSet(allUserNames);
    }

    public String getNewMessage() {
        return newMessage;
    }

    public void setNewMessage(String newMessage) {
        this.newMessage = newMessage;
    }

    public void addUser(String newUserName) {
        if (newUserName != null && !allUserNames.contains(newUserName)) {
            allUserNames.add(newUserName);
        }
    }

    public void deleteUser(String userName) {
        if (allUserNames.contains(userName)) {
            allUserNames.remove(userName);
        }
    }
}
