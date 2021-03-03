package com.pacmankata.core.board.directions

import com.pacmankata.core.board.two_dimensions.Position

class Left : Direction {
    override fun applies(rawSide: String): Boolean {
        return rawSide.toLowerCase() == "left"
    }

    override fun nextTo(position: Position): Position {
        return position.left()
    }
}
