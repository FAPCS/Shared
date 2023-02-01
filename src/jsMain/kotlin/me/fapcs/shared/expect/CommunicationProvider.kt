package me.fapcs.shared.expect

import me.fapcs.shared.config.ConfigurationHandler
import me.fapcs.shared.log.Logger
import me.fapcs.shared.util.setInterval
import me.fapcs.shared.util.unit
import org.w3c.dom.WebSocket

internal actual object CommunicationProvider {

    private var webSocket: WebSocket? = null
    private val callbacks = mutableListOf<(String) -> Unit>()
    private val buffer = mutableListOf<String>()

    actual val isInitialized: Boolean
        get() = webSocket?.readyState == WebSocket.OPEN || webSocket?.readyState == WebSocket.CONNECTING

    actual fun init() {
        Logger.debug("Initializing communication provider on JS...")
        webSocket = WebSocket(
            "ws://${ConfigurationHandler.get("socket.host", "localhost", true)}:" +
                    "${ConfigurationHandler.get("socket.port", 8080, true)}/ws"
        )
        Logger.debug("WebSocket initialized.")

        webSocket!!.onopen = { Logger.debug("WebSocket opened.") }
        webSocket!!.onclose = { Logger.debug("WebSocket closed.") }
        webSocket!!.onerror = { Logger.error("WebSocket error: $it") }

        webSocket!!.onmessage = {
            callbacks.forEach { callback -> callback(it.data.toString()) }
        }

        setInterval(50) {
            if (webSocket?.readyState != WebSocket.OPEN) return@setInterval
            buffer.forEach { message -> send(message) }
            buffer.clear()

            this.clear()

            Logger.debug("WebSocket is open, sending buffered messages...")
        }

        Logger.debug("Communication provider initialized.")
    }

    actual fun send(message: String) {
        if (webSocket?.readyState != WebSocket.OPEN) {
            Logger.debug("WebSocket is not open, buffering message...")
            buffer.add(message)
            return
        }

        Logger.debug("Sending message to WebSocket...")
        webSocket!!.send(message)
    }

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