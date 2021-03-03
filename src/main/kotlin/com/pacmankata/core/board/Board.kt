package com.pacmankata.core.board

import com.pacmankata.core.board.directions.Direction
import com.pacmankata.core.cell.Cell

interface Board {
    fun update()
    fun getNext(cell: Cell, direction: Direction): Cell
}
