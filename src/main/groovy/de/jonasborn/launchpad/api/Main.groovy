package de.jonasborn.launchpad.api


import de.jonasborn.launchpad.api.util.PathUtils

class Main {

    static void main(String[] args) {

        println PathUtils.matches("/test/eins", "/test/*")


        def la = new Launchpad(Channel.C1).fadePads(Color.BLANK, 10, 10).pressed("/", { l, m ->
            l.move("/2")
            l.fadePads(Color.RED, 10)
        }).pressed("/2", { l, m ->
            l.move("/")
            l.fadePads(Color.GREEN, 10)

        })


    }

}
