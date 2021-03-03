package com.pacmankata.core.content

import com.pacmankata.core.cell.CellContent

class ContentFactory {
    fun create(cellContent: String): CellContent {
        return if(cellContent == "*") {
            Ball()
        } else if(cellContent == " " || cellContent == "P") {
            Empty()
        } else {
            Wall(cellContent)
        }
    }

}
