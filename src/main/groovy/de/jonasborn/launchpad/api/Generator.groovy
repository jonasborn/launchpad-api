package de.jonasborn.launchpad.api

class Generator {

    static Map<Pressable, Color> patter(Direction start, Map<Pressable, Color> pads) {
        if (start == Direction.TOP) return (createTB(pads))
        if (start == Direction.BOTTOM) return (createBT(pads))
        if (start == Direction.RIGHT) return (createLR(pads))
        if (start == Direction.LEFT) return (createRL(pads))
        return [:]
    }

    private static Map<Pressable, Color>  createTB(Map<Pressable, Color> pads) {
        Map<Pressable, Color> entries = [:]
        Pad.values().each { entries.put(it, Color.BLANK) }
        pads.each { entries.put(it.key, it.value) }
        return entries
    }

    private static Map<Pressable, Color>  createBT(Map<Pressable, Color> pads) {
        Map<Pressable, Color> entries = [:]
        Pad.values().reverse().each { entries.put(it, Color.BLANK) }
        new ArrayList<>(pads.entrySet()).reverse().each { entries.put(it.key, it.value) }
        return entries
    }

    private static Map<Pressable, Color> createRL(Map<Pressable, Color> pads) {
        Map<Pressable, Color> entries = [:]
        for (int y = 0; y < 8; y++) for (int x = 7; x > -1; x--) entries.put(Pad.find(y, x), Color.BLANK)
        return fill(pads, entries)
    }

    private static Map<Pressable, Color> createLR(Map<Pressable, Color> pads) {
        Map<Pad, Color> entries = [:]
        for (int y = 0; y < 8; y++) for (int x = 0; x < 8; x++) entries.put(Pad.find(x, y), Color.BLANK)
        return fill(pads, entries)
    }


    private static Map<Pressable, Color> fill(Map<Pressable, Color> pads, Map<Pressable, Color> entries) {
        for (int x = 0; x < 8; x++) {
            for (int y = 0; y < 8; y++) {
                def v = pads.find { it.key == Pad.find(y, x) }
                if (v != null) entries.put(v.key, v.value)
            }
        }
        return entries
    }


    void set( Map<Pressable, Color> entries) {
        entries.each {
            launchpad.set(it.key, it.value)
            Thread.sleep(5)
        }
    }

}
