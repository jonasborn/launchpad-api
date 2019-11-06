package de.jonasborn.launchpad.api.client.message

import javax.sound.midi.ShortMessage

class TextFinishedMessage extends Message {
    TextFinishedMessage(ShortMessage delegate) {
        super(delegate)
    }

}
