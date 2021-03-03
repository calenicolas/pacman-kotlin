package com.pacmankata

import com.atary.pacman.AtaryControllers
import com.atary.pacman.AtaryScreen

object Application {
    @JvmStatic
    fun main(args: Array<String>) {
        val pacman = Pacman()

        AtaryControllers.init(pacman)

        val socket = AtaryScreen.getSocket()
        socket.show(
            listOf(listOf("Pacman kata"))
        )
    }
}
