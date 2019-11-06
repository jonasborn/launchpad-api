package de.jonasborn.launchpad.api.client

import com.google.gson.reflect.TypeToken
import de.jonasborn.launchpad.api.client.message.Message

@FunctionalInterface
interface MessageListener<T extends Message> {
    public void apply(T m);
}