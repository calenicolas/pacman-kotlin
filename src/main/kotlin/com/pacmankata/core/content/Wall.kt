package com.pacmankata.core.content

import com.pacmankata.core.cell.CellContent
import com.pacmankata.core.cell.CellContentObserver

class Wall(private val representation: String): CellContent {
    override fun bumpsWith(newContent: CellContent): CellContent {
        return this
    }

    override fun render(): String {
        return representation
    }

    override fun observeBy(observer: CellContentObserver) {}

    override fun moves() {}
}
