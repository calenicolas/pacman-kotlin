package com.pacmankata.infrastructure

class BoardDesign {
    companion object {
        fun from(cells: List<String>): List<List<String>> {
            return cells.map { it.map { char -> char.toString()}}
        }
    }
}
