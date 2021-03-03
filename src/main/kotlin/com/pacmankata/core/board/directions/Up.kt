package com.pacmankata.core.board.directions

import com.pacmankata.core.board.two_dimensions.Position

class Up: Direction {
    override fun applies(rawSide: String): Boolean {
        return rawSide.toLowerCase() == "up"
    }

    override fun nextTo(position: Position): Position {
        return position.up()
    }
}
