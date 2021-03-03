package com.pacmankata.core.cell

import com.pacmankata.core.board.directions.Direction
import com.pacmankata.core.cell.CellContent

interface CellContentObserver {
    fun notifyMovement(direction: Direction)
    fun notifyChange(newContent: CellContent)
}
