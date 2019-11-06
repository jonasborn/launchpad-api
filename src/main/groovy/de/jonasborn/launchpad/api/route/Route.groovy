package de.jonasborn.launchpad.api.route

import com.google.gson.reflect.TypeToken
import de.jonasborn.launchpad.api.Launchpad
import de.jonasborn.launchpad.api.client.message.Message

@FunctionalInterface
interface Route<T extends Message> {


    public void apply(Launchpad l, T m)

}