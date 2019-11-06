package de.jonasborn.launchpad.api

import javax.sound.midi.ShortMessage

enum Button implements Pressable{

    CON1(6, 8, ShortMessage.CONTROL_CHANGE),
    CON2(6, 9, ShortMessage.CONTROL_CHANGE),
    CON3(6, 10, ShortMessage.CONTROL_CHANGE),
    CON4(6, 11, ShortMessage.CONTROL_CHANGE),
    CON5(6, 12, ShortMessage.CONTROL_CHANGE),
    CON6(6, 13, ShortMessage.CONTROL_CHANGE),
    CON7(6, 14, ShortMessage.CONTROL_CHANGE),
    CON8(6, 15, ShortMessage.CONTROL_CHANGE),

    A(0, 8, ShortMessage.NOTE_ON),
    B(1, 8, ShortMessage.NOTE_ON),
    C(2, 8, ShortMessage.NOTE_ON),
    D(3, 8, ShortMessage.NOTE_ON),
    E(4, 8, ShortMessage.NOTE_ON),
    F(5, 8, ShortMessage.NOTE_ON),
    G(6, 8, ShortMessage.NOTE_ON),
    H(7, 8, ShortMessage.NOTE_ON);

    final int x
    final int y
    final int command

    private Button(int x, int y, int command) {
        this.x = x
        this.y = y
        this.command = command
    }

    int getCode() {
        return x * 16 + y
    }

    static Button find(ShortMessage message) {
        return values().find { it.command == message.command && it.getCode() == message.data1 }
    }

    static Button find(int x, int y) {
        return values().find { it.x == x && it.y == y }
    }
}
