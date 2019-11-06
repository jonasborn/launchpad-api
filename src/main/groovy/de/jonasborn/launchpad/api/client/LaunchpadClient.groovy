package de.jonasborn.launchpad.api.client

import com.google.common.collect.ArrayListMultimap
import com.google.common.collect.Multimap
import de.jonasborn.launchpad.api.Channel
import de.jonasborn.launchpad.api.client.message.Message

import javax.sound.midi.MidiMessage
import javax.sound.midi.MidiSystem
import javax.sound.midi.Receiver
import javax.sound.midi.ShortMessage
import javax.sound.midi.Transmitter
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class LaunchpadClient {

    static ExecutorService service = Executors.newFixedThreadPool(10)

    Channel channel
    Transmitter transmitter
    Receiver receiver

    Multimap<Class, MessageListener> listeners = ArrayListMultimap.create()

    LaunchpadClient() {
        this.transmitter = MidiSystem.getTransmitter()
        this.receiver = MidiSystem.getReceiver()
        transmitter.setReceiver(new MessageHandler(listeners))
        this.channel = Channel.C1 //TODO AUTODETECT CHANNEL
    }

    LaunchpadClient(Channel channel) {
        this()
        this.channel = channel
    }

    void listen(Class<? extends Message> cls, MessageListener listener) {
        listeners.put(cls, listener)
        //listeners.put(cls, listener)
    }

    void send(MidiMessage message) {
        receiver.send(message, -1)
    }

    private static class MessageHandler implements Receiver {

        static MessageDetector detector = new MessageDetector()
//        Map<Class, MessageListener> listeners = [:]
        Multimap<Class, MessageListener> listeners;

        MessageHandler(Multimap<Class, MessageListener> listeners) {
            this.listeners = listeners
        }

        @Override
        void send(MidiMessage sm, long timeStamp) {
            if (sm instanceof ShortMessage) {
                def m = detector.detect(sm)
                if (m != null) service.submit({
                    try {
                        listeners.entries().findAll {
                            return m.class.isAssignableFrom(it.key) || it.key.isAssignableFrom(m.class) //TODO WATCH THIS
                        }.each { f ->
                            service.submit({
                                try {
                                    f.value.apply(m)
                                } catch (Exception e) {
                                    e.printStackTrace()
                                }
                            } as Runnable)
                        }
                    } catch (Exception e) {
                        e.printStackTrace()
                    }
                } as Runnable)
            }
        }

        @Override
        void close() {
            receiver.close()
            transmitter.close()
        }
    }
}
