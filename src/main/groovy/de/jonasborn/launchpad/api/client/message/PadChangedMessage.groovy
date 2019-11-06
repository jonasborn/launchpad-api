package de.jonasborn.launchpad.api.client.message

import de.jonasborn.launchpad.api.Pad
import de.jonasborn.launchpad.api.Pressable

import javax.sound.midi.ShortMessage

class PadChangedMessage extends PressableChangedMessage {
    PadChangedMessage(ShortMessage delegate) {
        super(delegate)
    }

    public Pad getPad() {
        return Pad.find(delegate)
    }

}
