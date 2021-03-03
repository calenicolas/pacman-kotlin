package com.pacmankata.core.board.two_dimensions

data class Position(val x: Int, val y: Int) {
    fun right(): Position {
        return Position(x + 1, y)
    }

    fun left(): Position {
        return Position(x - 1, y)
    }

    fun down(): Position {
        return Position(x, y + 1)
    }

    fun up(): Position {
        return Position(x, y - 1)
    }
}