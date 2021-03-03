package com.pacmankata.core.board.two_dimensions
import com.pacmankata.core.board.directions.Direction
import com.pacmankata.Pacman
import com.pacmankata.core.cell.Cell
import com.pacmankata.core.board.Board
import com.pacmankata.core.content.ContentFactory

class TwoDimensionsBoard(
    boardDesign: List<List<String>>,
    private val pacman: Pacman,
    private val presenter: Presenter
): Board {
    private lateinit var pacmanCell: Cell
    private val contentFactory = ContentFactory()

    private val cells = createCells(boardDesign)

    private fun createCells(boardDesign: List<List<String>>): List<List<Cell>> {
        return boardDesign.map { row ->
            row.map {
                createCell(it)
            }
        }
    }

    private fun createCell(it: String): Cell {
        val content = contentFactory.create(it)
        val cell = Cell(this, content)
        if (it == "P")
            pacmanCell = cell
        return cell
    }

    fun init() {
        pacmanCell.arrives(pacman)
    }

    override fun update() {
        val board = cells.map { row ->
            row.map { cell -> cell.render() }
        }
        presenter.render(board)
    }

    override fun getNext(cell: Cell, direction: Direction): Cell {
        val position = getCellPosition(cell)
        val nextPosition = direction.nextTo(position)

        val row = cells.getOrElse(nextPosition.y) {
            if (nextPosition.y < 0) {
                cells.first()
            } else {
                cells.last()
            }
        }

        return row.getOrElse(nextPosition.x) {
            if(nextPosition.x < 0) {
                row.last()
            } else {
                row.first()
            }
        }
    }

    private fun getCellPosition(cell: Cell): Position {
        var position = Position(-1, -1)

        cells.forEachIndexed { rowIndex, row ->
            row.forEachIndexed { cellIndex, it ->
                if (cell == it) {
                    position = Position(
                        cellIndex,
                        rowIndex
                    )
                }
            }
        }

        return position
    }
}