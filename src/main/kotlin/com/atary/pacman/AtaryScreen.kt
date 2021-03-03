package com.atary.pacman

class AtaryScreen {
    companion object {
        fun getSocket(): AtariScreenSocket {
            return socket
        }

        private val socket = AtariScreenSocket()
    }
}

class AtariScreenSocket {
    fun show(screen: List<List<String>>) {

        screen.forEach {
            println(it.joinToString(""))
        }
    }
}