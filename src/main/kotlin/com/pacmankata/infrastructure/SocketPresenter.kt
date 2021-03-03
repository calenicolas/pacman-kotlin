package com.pacmankata.infrastructure

import com.atary.pacman.AtariScreenSocket
import com.pacmankata.core.Points
import com.pacmankata.core.board.two_dimensions.Presenter

class SocketPresenter(private val socket: AtariScreenSocket, private val points: Points):
    Presenter {
    override fun render(board: List<List<String>>) {
        val pointsRow = listOf(points.toString())

        val representation = board.toMutableList()
        representation.add(pointsRow)

        socket.show(representation)
    }
}
