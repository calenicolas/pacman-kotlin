package com.pacmankata.core.cell

import com.pacmankata.core.content.Empty
import com.pacmankata.core.board.directions.Direction
import com.pacmankata.core.board.Board

class Cell(private val board: Board, private var content: CellContent = Empty()) :
    CellContentObserver {

    fun render(): String {
        return content.render()
    }

    fun arrives(newContent: CellContent) {
        content = content.bumpsWith(newContent)
        content.observeBy(this)
        board.update()
    }

    override fun notifyMovement(direction: Direction) {
        val next = board.getNext(this, direction)
        next.arrives(content)
    }

    override fun notifyChange(newContent: CellContent) {
        content = newContent
        content.observeBy(this)
    }
}
