package com.atary.pacman

import com.atary.pacman.controller.ServerVerticle
import io.vertx.core.VertxOptions
import io.vertx.reactivex.core.Vertx
import java.util.concurrent.TimeUnit

class AtaryControllers {
    companion object {
        fun init(pacman: AtaryPacman) {
            VertxProvider.vertx.deployVerticle(
                ServerVerticle(
                    pacman
                )
            )
        }
    }
}

object VertxProvider {
    val vertx: Vertx by lazy { Vertx.vertx(vertxOptions) }

    private val vertxOptions by lazy {

        VertxOptions()
            .setBlockedThreadCheckInterval(5)
            .setBlockedThreadCheckIntervalUnit(TimeUnit.SECONDS)

            .setMaxEventLoopExecuteTime(200L)
            .setMaxEventLoopExecuteTimeUnit(TimeUnit.MILLISECONDS)

            .setMaxWorkerExecuteTime(10)
            .setMaxWorkerExecuteTimeUnit(TimeUnit.SECONDS)

            .setWarningExceptionTime(20)
            .setWarningExceptionTimeUnit(TimeUnit.SECONDS)
    }

}