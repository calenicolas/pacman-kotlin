package com.pacmankata.core.board.directions

import com.pacmankata.core.board.two_dimensions.Position

class Right : Direction {
    override fun applies(rawSide: String): Boolean {
        return rawSide.toLowerCase() == "right"
    }

    override fun nextTo(position: Position): Position {
        return position.right()
    }

}
