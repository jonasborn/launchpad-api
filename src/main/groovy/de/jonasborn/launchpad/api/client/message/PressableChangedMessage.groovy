package de.jonasborn.launchpad.api.client.message

import de.jonasborn.launchpad.api.Button
import de.jonasborn.launchpad.api.Pad
import de.jonasborn.launchpad.api.Pressable

import javax.sound.midi.ShortMessage

class PressableChangedMessage extends Message {

    private Class type

    PressableChangedMessage(ShortMessage delegate) {
        super(delegate)
    }

    Pressable get() {
        def p = Pad.find(delegate)
        def b = Button.find(delegate)
        if (p != null) {
            type = Pad.class
            return p
        }
        if (b != null) {
            type = Button.class
            return b
        }

    }

    boolean isPressed() {
        return delegate.data2 == 127
    }


}
