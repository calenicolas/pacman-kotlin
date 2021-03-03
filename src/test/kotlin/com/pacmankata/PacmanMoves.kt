package com.pacmankata

import com.pacmankata.core.Points
import com.pacmankata.core.board.two_dimensions.Presenter
import com.pacmankata.core.board.two_dimensions.TwoDimensionsBoard
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.times
import com.nhaarman.mockitokotlin2.verify
import com.pacmankata.infrastructure.BoardDesign
import org.spekframework.spek2.Spek
import org.spekframework.spek2.style.gherkin.Feature

object PacmanMoves : Spek({
    lateinit var pacman: Pacman
    lateinit var points: Points
    lateinit var presenter: Presenter

    val singleTestBoard = BoardDesign.from(listOf("  P  "))
    val doubleTestBoard = BoardDesign.from(listOf(
        "  P  ",
        "     "
    ))
    val walledTestBoard = BoardDesign.from(listOf(
        "╔════════════════╗",
        "║*******P********║",
        " **************** ",
        "╚════════════════╝"
    ))
    val expectedWalledTestBoard = BoardDesign.from(listOf(
        "╔════════════════╗",
        "║******* ********║",
        " *******P******** ",
        "╚════════════════╝"
    ))
    val expectedTurnArroundTestBoard = BoardDesign.from(listOf(
        "╔════════════════╗",
        "║******* ********║",
        "P*******          ",
        "╚════════════════╝"
    ))

    fun pacmanMoves(pacman: Pacman, side: String, times: Int) {
        var counter = 0
        while (counter < times) {
            pacman.goTo(side)
            counter++
        }
    }

    fun givenATestBoard() {
        presenter = mock()
        points = mock()
        pacman = Pacman(points)
        TwoDimensionsBoard(
            singleTestBoard,
            pacman,
            presenter
        ).init()
    }

    fun givenADoubleTestBoard() {
        presenter = mock()
        points = mock()
        pacman = Pacman(points)
        TwoDimensionsBoard(
            doubleTestBoard,
            pacman,
            presenter
        ).init()
    }

    fun givenAWalledTestBoard() {
        presenter = mock()
        points = mock()
        pacman = Pacman(points)
        TwoDimensionsBoard(
            walledTestBoard,
            pacman,
            presenter
        ).init()
    }

    Feature("pacman can move along the board") {
        Scenario("pacman is in the center and goes to right") {
            Given("a board and pacman in its center") {
                givenATestBoard()
            }

            When("pacman goes to right") {
                pacmanMoves(pacman, "RIGHT", 1)
            }

            Then("pacman moves to right") {
                val expectedBoard = listOf(
                    listOf(" ", " ", " ", "P", " ")
                )
                verify(presenter).render(expectedBoard)
            }
        }

        Scenario("pacman is in the center and goes to right twice") {
            Given("a board and pacman in its center") {
                givenATestBoard()
            }

            When("pacman goes to right twice") {
                pacmanMoves(pacman, "RIGHT", 2)
            }

            Then("pacman moves to right twice") {
                val expectedBoard = listOf(
                    listOf(" ", " ", " ", " ", "P")
                )
                verify(presenter).render(expectedBoard)
            }
        }

        Scenario("pacman is in the center and goes to right when reaches end of row") {
            Given("a board and pacman in its center") {
                givenATestBoard()
            }

            When("pacman goes to right thrice") {
                pacmanMoves(pacman, "RIGHT", 3)
            }

            Then("pacman returns to the first position of the row") {
                val expectedBoard = listOf(
                    listOf("P", " ", " ", " ", " ")
                )
                verify(presenter).render(expectedBoard)
            }
        }

        Scenario("pacman moves to left") {
            Given("a board and pacman in its center") {
                givenATestBoard()
            }

            When("pacman goes to right thrice and one left") {
                pacmanMoves(pacman, "RIGHT", 3)
                pacmanMoves(pacman, "LEFT", 1)
            }

            Then("pacman moves at last position of the row") {
                val expectedBoard = listOf(
                    listOf(" ", " ", " ", " ", "P")
                )
                verify(presenter, times(2)).render(expectedBoard)
            }
        }

        Scenario("pacman moves down") {

            Given("a board and pacman in its center") {
                givenADoubleTestBoard()
            }

            When("pacman goes to right thrice") {
                pacmanMoves(pacman, "DOWN", 1)
            }

            Then("pacman stays at limit") {
                val expectedBoard = listOf(
                    listOf(" ", " ", " ", " ", " "),
                    listOf(" ", " ", "P", " ", " ")
                )
                verify(presenter).render(expectedBoard)
            }

            Given("a board and pacman in its center") {
                givenADoubleTestBoard()
            }

            When("pacman goes to right twice and once down") {
                pacmanMoves(pacman, "RIGHT", 2)
                pacmanMoves(pacman, "DOWN", 1)
            }

            Then("pacman moves at the bottom right corner of the board") {
                val expectedBoard = listOf(
                    listOf(" ", " ", " ", " ", " "),
                    listOf(" ", " ", " ", " ", "P")
                )
                verify(presenter).render(expectedBoard)
            }
        }

        Scenario("pacman moves up") {
            Given("a board and pacman in its center") {
                givenADoubleTestBoard()
            }

            When("pacman goes down and up") {
                pacmanMoves(pacman, "DOWN", 1)
                pacmanMoves(pacman, "UP", 1)
            }

            Then("pacman moves to center") {
                val expectedBoard = listOf(
                    listOf(" ", " ", "P", " ", " "),
                    listOf(" ", " ", " ", " ", " ")
                )
                verify(presenter, times(2)).render(expectedBoard)
            }
        }

        Scenario("pacman does not stuck") {
            Given("a board and pacman in its center") {
                givenADoubleTestBoard()
            }

            When("pacman goes to right thrice") {
                pacmanMoves(pacman, "LEFT", 5)
                pacmanMoves(pacman, "RIGHT", 1)
                pacmanMoves(pacman, "DOWN", 1)
            }

            Then("pacman can move after going outside the board limits") {
                val expectedBoard = listOf(
                    listOf(" ", " ", " ", " ", " "),
                    listOf(" ", " ", " ", "P", " ")
                )
                verify(presenter).render(expectedBoard)
            }
        }
    }

    Feature("walls") {
        Scenario("walls do not bug pacman") {
            Given("a board and pacman in its center") {
                givenAWalledTestBoard()
            }

            When("pacman goes up and down") {
                pacmanMoves(pacman, "UP", 1)
                pacmanMoves(pacman, "DOWN", 1)
            }

            Then("pacman goes down") {
                verify(presenter).render(expectedWalledTestBoard)
            }
        }

        Scenario("Pacman can turn arround the map") {
            Given("a board and pacman in its center") {
                givenAWalledTestBoard()
            }

            When("pacman goes up and down") {
                pacmanMoves(pacman, "DOWN", 1)
                pacmanMoves(pacman, "RIGHT", 10)
            }

            Then("pacman moves to the first position of the row") {
                verify(presenter).render(expectedTurnArroundTestBoard)
            }
        }
    }
})
