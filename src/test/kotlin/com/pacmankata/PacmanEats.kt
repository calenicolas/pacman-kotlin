package com.pacmankata

import com.pacmankata.core.Points
import com.pacmankata.core.board.two_dimensions.TwoDimensionsBoard
import com.pacmankata.core.board.two_dimensions.Presenter
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.times
import com.nhaarman.mockitokotlin2.verify
import com.pacmankata.infrastructure.BoardDesign
import org.spekframework.spek2.Spek
import org.spekframework.spek2.style.gherkin.Feature

object PacmanEats : Spek({
    lateinit var pacman: Pacman
    lateinit var points: Points
    lateinit var presenter: Presenter

    val singleTestBoard = BoardDesign.from(listOf(
        "**P**"
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

    Feature("pacman can eat balls") {
        Scenario("pacman is in the center and goes to right") {
            Given("the ball in the center gets eaten") {
                givenATestBoard()
            }

            When("pacman goes to right") {
                pacmanMoves(pacman, "RIGHT", 2)
            }

            Then("pacman moves to right") {
                val expectedBoard = listOf(
                    listOf("*", "*", " ", " ", "P")
                )
                verify(presenter).render(expectedBoard)
            }
        }
    }

    Feature("pacman earns 10 points when eats a ball") {
        Scenario("pacman is in the center and goes to right") {
            Given("the ball in the center gets eaten") {
                givenATestBoard()
            }

            When("pacman goes to right") {
                pacmanMoves(pacman, "RIGHT", 2)
            }

            Then("pacman ears 10 points") {
                verify(points, times(2)).increase(10)
            }
        }
    }
})
