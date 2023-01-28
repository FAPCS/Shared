package me.fapcs.shared.expect

import me.fapcs.shared.config.ConfigurationHandler
import me.fapcs.shared.log.Logger
import me.fapcs.shared.util.unit
import org.w3c.dom.WebSocket

internal actual object CommunicationProvider {

    private var webSocket: WebSocket? = null
    private val callbacks = mutableListOf<(String) -> Unit>()

    actual val isInitialized: Boolean
        get() = webSocket?.readyState == WebSocket.OPEN

    actual fun init() {
        Logger.debug("Initializing communication provider on JS...")
        webSocket = WebSocket(
            "ws://${ConfigurationHandler.get<String>("socket.host")}:" +
                    "${ConfigurationHandler.get<Int>("socket.port")}/ws"
        )
        Logger.debug("WebSocket initialized.")

        webSocket!!.onopen = { Logger.debug("WebSocket opened.") }
        webSocket!!.onclose = { Logger.debug("WebSocket closed.") }
        webSocket!!.onerror = { Logger.error("WebSocket error: $it") }

        webSocket!!.onmessage = {
            callbacks.forEach { callback -> callback(it.data.toString()) }
        }

        Logger.debug("Communication provider initialized.")
    }

    actual fun send(message: String) = unit(webSocket?.send(message))

    actual fun receive(callback: (String) -> Unit) = unit(callbacks.add(callback))

    actual fun close() {
        Logger.debug("Closing communication provider on JS...")
        webSocket?.close()
        Logger.debug("WebSocket closed.")

        webSocket = null

        callbacks.clear()
        Logger.debug("Communication provider closed.")
    }

}