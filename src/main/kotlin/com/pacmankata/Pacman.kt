package com.pacmankata

import com.atary.pacman.AtaryPacman
import com.pacmankata.core.Points
import com.pacmankata.core.cell.CellContent
import com.pacmankata.core.cell.CellContentObserver
import com.pacmankata.core.content.Empty
import com.pacmankata.core.board.directions.*
import com.pacmankata.core.content.Ball

class Pacman(private val points: Points) : AtaryPacman, CellContent {
    private val observers = mutableListOf<CellContentObserver>()
    private val directions: List<Direction> = listOf(
        Up(), Down(), Right(), Left()
    )

    override fun goTo(rawSide: String) {
        observers.forEach {
            val side = buildSide(rawSide)
            it.notifyMovement(side)
        }
    }

    private fun buildSide(rawSide: String): Direction {
        return directions.find {
            it.applies(rawSide)
        } ?: UnknownDirection()
    }

    override fun bumpsWith(newContent: CellContent): CellContent {
        if (newContent is Ball) {
            points.increase(10)
        }
        return this
    }

    override fun render(): String {
        return "P"
    }

    override fun observeBy(observer: CellContentObserver) {
        observers.clear()
        observers.add(observer)
    }

    override fun moves() {
        observers.forEach {
            it.notifyChange(Empty())
        }
    }
}