package com.pacmankata.core.board.directions

import com.pacmankata.core.board.two_dimensions.Position

interface Direction {
    fun applies(rawSide: String): Boolean
    fun nextTo(position: Position): Position
}
