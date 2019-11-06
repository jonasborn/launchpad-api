package de.jonasborn.launchpad.api

import de.jonasborn.launchpad.api.client.LaunchpadClient
import de.jonasborn.launchpad.api.client.MessageListener
import de.jonasborn.launchpad.api.client.message.*
import de.jonasborn.launchpad.api.route.Route
import de.jonasborn.launchpad.api.util.PathUtils

import javax.sound.midi.ShortMessage

class Launchpad {

    LaunchpadClient client
    final LaunchpadText text

    String path = "/"

    Map<Pressable, Color> colors = [:]

    Launchpad(Channel channel) {
        client = new LaunchpadClient(channel)
        this.text = new LaunchpadText(client)
    }

    public Color getColor(Pressable pad) {
        return colors.getOrDefault(pad, Color.BLANK)
    }

    public Launchpad set(Pressable button, Color color,  Boolean save = true) {
        client.send(new ShortMessage(button.command, Channel.C1.channelForSystem(), button.getCode(), color.getCode()))
        if (save) colors.put(button, color)
        return this
    }

    public Launchpad fade(Pressable pad, Color target, Long timeout = 50) {
        def source = getColor(pad)
        def steps = source.fade(target)
        steps.each {
            set(pad, it)
            Thread.sleep(timeout)
        }
        return this
    }

    Launchpad fade(List<Pressable> pressables, Color target, Long stepTimeout = 50, Long padTimeout = 50) {
        pressables.each {
            fade(it, target, stepTimeout)
            Thread.sleep(padTimeout)
        }
        return this
    }

    Launchpad move(String path) {
        this.path = path
        return this
    }

    public Launchpad fadePads(Color target, Long stepTimeout = 50, Long padTimeout = 50) {
        fade(Pad.values() as List<Pressable>, target, stepTimeout, padTimeout)
        return this
    }

    public Launchpad fadeButtons(Color target, Long stepTimeout = 50, Long padTimeout = 50) {
        fade(Button.values() as List<Pressable>, target, stepTimeout, padTimeout)
        return this
    }

    public Launchpad patter(Direction direction, Map<Pressable, Color> pressables, Long padTimeout = 50) {
        def ops = Generator.patter(direction, pressables)
        ops.each {
            set(it.key, it.value)
            Thread.sleep(padTimeout)
        }
        return this
    }

    public Launchpad patter(Direction direction, List<ColoredPressable> cps, Long pressableTimeout = 50) {
        patter(direction, cps.collectEntries {[it.pressable, it.color]}, pressableTimeout)
        return this
    }


    public Launchpad listen(Class<? extends Message> cls, MessageListener listener) {
        client.listen(cls, listener)
        return this
    }

    public Launchpad onPadPressed(MessageListener<PadChangedMessage> listener) {
        client.listen(PadChangedMessage.class, new MessageListener<PadChangedMessage>() {
            @Override
            void apply(PadChangedMessage m) {
                if (m.pressed) listener.apply(m)
            }
        })
        return this
    }

    public Launchpad onPadReleased(MessageListener<PadChangedMessage> listener) {
        client.listen(PadChangedMessage.class, new MessageListener<PadChangedMessage>() {
            @Override
            void apply(PadChangedMessage m) {
                if (!m.pressed) listener.apply(m)
            }
        })
        return this
    }

    public Launchpad onButtonPressed(MessageListener<ButtonChangedMessage> listener) {
        client.listen(ButtonChangedMessage.class, new MessageListener<ButtonChangedMessage>() {
            @Override
            void apply(ButtonChangedMessage m) {
                if (m.pressed) listener.apply(m)
            }
        })
        return this
    }

    public Launchpad onButtonReleased(MessageListener<ButtonChangedMessage> listener) {
        client.listen(ButtonChangedMessage.class, new MessageListener<ButtonChangedMessage>() {
            @Override
            void apply(ButtonChangedMessage m) {
                if (!m.pressed) listener.apply(m)
            }
        })
        return this
    }

    public Launchpad onTextFinished(MessageListener<TextFinishedMessage> listener) {
        client.listen(TextFinishedMessage.class, listener)
        return this
    }

    Launchpad pressed(String path, Route<PressableChangedMessage> route) {
        def self = this
        client.listen(PressableChangedMessage.class,  new MessageListener<PressableChangedMessage>() {
            @Override
            void apply(PressableChangedMessage m) {
                if (m.pressed && PathUtils.matches(self.path, path))
                    route.apply(self, m)
            }
        })
        return this
    }

    Launchpad released(String path, Route<PressableChangedMessage> route) {
        def self = this
        client.listen(PressableChangedMessage.class,  new MessageListener<PressableChangedMessage>() {
            @Override
            void apply(PressableChangedMessage m) {
                if (!m.pressed && PathUtils.matches(self.path, path))
                    route.apply(self, m)
            }
        })
        return this
    }

}
