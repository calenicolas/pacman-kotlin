package com.pacmankata.core.cell

interface CellContent {
    fun bumpsWith(newContent: CellContent): CellContent
    fun render(): String
    fun observeBy(observer: CellContentObserver)
    fun moves()
}
