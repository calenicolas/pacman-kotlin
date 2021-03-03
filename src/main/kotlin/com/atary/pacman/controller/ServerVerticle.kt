package com.atary.pacman.controller

import com.atary.pacman.AtaryPacman
import com.pacmankata.config.Environment
import io.vertx.core.http.HttpServerOptions
import io.vertx.reactivex.core.AbstractVerticle
import io.vertx.reactivex.core.http.HttpServer
import io.vertx.reactivex.ext.web.Router
import io.vertx.reactivex.ext.web.RoutingContext
import io.vertx.reactivex.ext.web.handler.BodyHandler
import org.slf4j.LoggerFactory

private val logger = LoggerFactory.getLogger(ServerVerticle::class.java)

class ServerVerticle(private val pacman: AtaryPacman) : AbstractVerticle() {
    override fun start() {
        val router = createRouter()

        startHttpServer(router)
    }

    private fun createRouter(): Router {

        val routes = Routes(pacman)

        return Router.router(vertx)
            .apply(this::addBodyHandler)
            .apply(routes::register)
    }

    private fun startHttpServer(router: Router) {
        vertx.createHttpServer(HttpServerOptions().setHandle100ContinueAutomatically(true))
            .requestHandler(router)
            .rxListen(Environment.PORT)
            .subscribe(this::onServerStarted, this::onServerStartError)
    }

    private fun addBodyHandler(router: Router) {
        router.route().handler(BodyHandler.create())
    }

    private fun onServerStarted(httpServer: HttpServer) {
        println("\nServer listening on :${httpServer.actualPort()}")
    }

    private fun onServerStartError(error: Throwable) {
        logger.info("Server not started")
        logger.error(error.message, error)
    }
}

class Routes(private val pacman: AtaryPacman) {
    fun register(router: Router) {
        router.use(ControllerHandler(pacman))
    }

    private fun Router.use(handler: Handler) = handler.register(this)
}

interface Handler {
    fun register(router: Router)
}

class ControllerHandler(private val pacman: AtaryPacman) :
    Handler {

    override fun register(router: Router) {
        router.get(PATH).handler(this::handle)
    }

    private fun handle(context: RoutingContext) {

        val keys = context.queryParam("key")
        if(keys.isEmpty()) return end(context)

        val key = keys.first()
        val direction = directions[key]

        direction?.let {
            pacman.goTo(it)
        }

        end(context)
    }

    private fun end(context: RoutingContext) {
        context.response().setStatusCode(200).end()
    }

    companion object {
        private const val PATH = "/key"
        private val directions = mapOf(
            "d" to "RIGHT",
            "a" to "LEFT",
            "s" to "DOWN",
            "w" to "UP"
        )
    }
}
