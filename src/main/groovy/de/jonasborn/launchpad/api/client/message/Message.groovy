package de.jonasborn.launchpad.api.client.message

import de.jonasborn.launchpad.api.Channel

import javax.sound.midi.ShortMessage

class Message {

    ShortMessage delegate

    Message(ShortMessage delegate) {
        this.delegate = delegate
    }

    int getCommand() {
        return delegate.command
    }

    int getData1() {
        return delegate.data1
    }

    int getData2() {
        return delegate.data2
    }

    int getStatus() {
        return delegate.getStatus()
    }

    byte[] getMessage() {
        return delegate.message
    }

    Channel getChannel() {
        def message = delegate
        return Channel.values().find { it.channelForSystem() == message.getChannel() }
    }

}
