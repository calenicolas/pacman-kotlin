package com.pacmankata.core.board.directions

import com.pacmankata.core.board.two_dimensions.Position

class UnknownDirection : Direction {
    override fun applies(rawSide: String): Boolean {
        return true
    }

    override fun nextTo(position: Position): Position {
        return position
    }
}
