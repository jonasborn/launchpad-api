package de.jonasborn.launchpad.api.client


import de.jonasborn.launchpad.api.Button
import de.jonasborn.launchpad.api.Pad
import de.jonasborn.launchpad.api.client.message.ButtonChangedMessage
import de.jonasborn.launchpad.api.client.message.Message
import de.jonasborn.launchpad.api.client.message.PadChangedMessage
import de.jonasborn.launchpad.api.client.message.TextFinishedMessage

import javax.sound.midi.ShortMessage
import java.util.function.Function

class MessageDetector {

    static Map<Function<? extends ShortMessage, Boolean>, Class> rules = [:]


    static {
        rules.put({ return it.data1 == 0 && it.data2 == 3 } as Function<? extends ShortMessage, Boolean> , TextFinishedMessage.class)
        rules.put({ return Button.find(it) != null } as Function<? extends ShortMessage, Boolean>, ButtonChangedMessage.class)
        rules.put({ return Pad.find(it) != null } as Function<? extends ShortMessage, Boolean>, PadChangedMessage.class)
    }

    Message detect(ShortMessage message) {
        try {
            def entry = rules.find { it.key.apply(message) }
            if (entry == null) return null
            return entry.value.newInstance(message)
        } catch (Exception e) {
            e.printStackTrace() //TODO REMOVE!
            return null
        }
    }

}
