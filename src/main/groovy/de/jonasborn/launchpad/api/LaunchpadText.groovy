package de.jonasborn.launchpad.api

import de.jonasborn.launchpad.api.client.LaunchpadClient
import de.jonasborn.launchpad.api.client.MessageListener
import de.jonasborn.launchpad.api.client.message.Message
import de.jonasborn.launchpad.api.client.message.TextFinishedMessage

import javax.sound.midi.InvalidMidiDataException
import javax.sound.midi.SysexMessage
import java.nio.charset.Charset
import java.util.concurrent.CountDownLatch

class LaunchpadText {

    LaunchpadClient client

    CountDownLatch textLatch = new CountDownLatch(0)

    LaunchpadText(LaunchpadClient client) {
        this.client = client
        client.listen(TextFinishedMessage.class,{ if (textLatch.count > 0) textLatch.countDown() })
    }

    public  void cancel() throws InvalidMidiDataException {
        byte[] header = [(byte) 240, 0, 32, 41, 9, (byte) Color.BLANK.code];
        byte[] chars =  new byte[0]
        byte[] message = new byte[chars.length + 8];
        System.arraycopy(header, 0, message, 0, header.length);
        message[header.length] = (byte) 5;
        System.arraycopy(chars, 0, message, header.length + 1, chars.length);
        message[message.length - 1] = (byte) 247;
        sendSysExMessage(message);
    }
    private void sendSysExMessage(byte[] data) throws InvalidMidiDataException {
        SysexMessage message = new SysexMessage();
        message.setMessage(data, data.length);
        client.send(message);
    }

    public  void send(String text, Color color, int speed, boolean loop) throws InvalidMidiDataException {
        textLatch = new CountDownLatch(1)
        if (loop) {color += 64 }
        byte[] header = [(byte) 240, 0, 32, 41, 9, (byte) color.code];
        byte[] chars = text == null ? new byte[0] : text.getBytes(Charset.forName("ASCII"));
        byte[] message = new byte[chars.length + 8];
        System.arraycopy(header, 0, message, 0, header.length);
        message[header.length] = (byte) speed;
        System.arraycopy(chars, 0, message, header.length + 1, chars.length);
        message[message.length - 1] = (byte) 247
        sendSysExMessage(message)
        textLatch.await()
    }

    public boolean textPresent() {
        return textLatch.count > 0
    }





}
