package com.pacmankata.core.board.directions

import com.pacmankata.core.board.two_dimensions.Position

class Down : Direction {
    override fun applies(rawSide: String): Boolean {
        return rawSide.toLowerCase() == "down"
    }

    override fun nextTo(position: Position): Position {
        return position.down()
    }
}
