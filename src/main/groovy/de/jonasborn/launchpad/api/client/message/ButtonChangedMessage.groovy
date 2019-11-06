package de.jonasborn.launchpad.api.client.message

import de.jonasborn.launchpad.api.Button

import javax.sound.midi.ShortMessage

class ButtonChangedMessage extends PressableChangedMessage {
    ButtonChangedMessage(ShortMessage delegate) {
        super(delegate)
    }

    Button getPad() {
        return Button.find(delegate)
    }

}
