package com.pacmankata.core.content

import com.pacmankata.core.cell.CellContent
import com.pacmankata.core.cell.CellContentObserver

class Empty: CellContent {
    override fun bumpsWith(newContent: CellContent): CellContent {
        newContent.moves()
        return newContent
    }

    override fun render(): String {
        return " "
    }

    override fun observeBy(observer: CellContentObserver) {}
    override fun moves() {}
}