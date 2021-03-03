package com.pacmankata

import com.atary.pacman.AtaryControllers
import com.atary.pacman.AtaryScreen
import com.atary.pacman.AtariScreenSocket
import com.pacmankata.core.Points
import com.pacmankata.core.board.two_dimensions.TwoDimensionsBoard
import com.pacmankata.infrastructure.BoardDesign
import com.pacmankata.infrastructure.SocketPresenter

object Application {
    @JvmStatic
    fun main(args: Array<String>) {
        val localPoints = com.pacmankata.infrastructure.LocalPoints()
        val pacman = Pacman(localPoints)

        AtaryControllers.init(pacman)

        val socket = AtaryScreen.getSocket()

        val board = createProductionBoard(pacman, socket, localPoints)
        board.init()
    }

    private fun createProductionBoard(
        pacman: Pacman,
        socket: AtariScreenSocket, localPoints: Points
    ): TwoDimensionsBoard {
        val boardDesign = BoardDesign.from(
            listOf(
                "╔════════════╗╔════════════╗",
                "║************║║************║",
                "║*╔══╗*╔═══╗*║║*╔═══╗*╔══╗*║",
                "║*║  ║*║   ║*║║*║   ║*║  ║*║",
                "║*╚══╝*╚═══╝*╚╝*╚═══╝*╚══╝*║",
                "║**************************║",
                "║*╔══╗*╔╗*╔══════╗*╔╗*╔══╗*║",
                "║*╚══╝*║║*╚══╗╔══╝*║║*╚══╝*║",
                "║******║║****║║****║║******║",
                "╚════╗*║╚══╗*║║*╔══╝║*╔════╝",
                "     ║*║╔══╝*╚╝*╚══╗║*║     ",
                "     ║*║║**********║║*║     ",
                "     ║*║║*╔══  ══╗*║║*║     ",
                "═════╝*╚╝*║      ║*╚╝*╚═════",
                "**********║      ║**********",
                "═════╗*╔╗*║      ║*╔╗*╔═════",
                "     ║*║║*╚══════╝*║║*║     ",
                "     ║*║║**********║║*║     ",
                "     ║*║║*╔══════╗*║║*║     ",
                "╔════╝*╚╝*╚══╗╔══╝*╚╝*╚════╗",
                "║************║║************║",
                "║*╔══╗*╔═══╗*║║*╔═══╗*╔══╗*║",
                "║*╚═╗║*╚═══╝*╚╝*╚═══╝*║╔═╝*║",
                "║***║║*******P********║║***║",
                "╚═╗*║║*╔╗*╔══════╗*╔╗*║║*╔═╝",
                "╔═╝*╚╝*║║*╚══╗╔══╝*║║*╚╝*╚═╗",
                "║******║║****║║****║║******║",
                "║*╔════╝╚══╗*║║*╔══╝╚════╗*║",
                "║*╚════════╝*╚╝*╚════════╝*║",
                "║**************************║",
                "╚══════════════════════════╝"
            )
        )

        val board =
            TwoDimensionsBoard(
                boardDesign,
                pacman,
                SocketPresenter(socket, localPoints)
            )
        return board
    }
}
