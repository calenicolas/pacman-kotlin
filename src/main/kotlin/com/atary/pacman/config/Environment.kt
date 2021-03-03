package com.atary.pacman.config

object Environment {
    val PORT = "PORT".getOrDefaultTo("8080").toInt()
}

private fun String.getOrDefaultTo(defaultValue: String) = System.getenv(this) ?: defaultValue
