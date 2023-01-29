package me.fapcs.shared.expect

import me.fapcs.shared.config.ConfigurationHandler
import me.fapcs.shared.log.Logger
import me.fapcs.shared.util.unit
import java.net.URI
import java.net.http.HttpClient
import java.net.http.WebSocket
import java.util.concurrent.CompletionStage

internal actual object CommunicationProvider {

    private var webSocket: WebSocket? = null
    private val callbacks = mutableListOf<(String) -> Unit>()

    actual val isInitialized: Boolean
        get() = webSocket != null && !webSocket!!.isInputClosed && !webSocket!!.isOutputClosed

    actual fun init() {
        Logger.debug("Initializing communication provider on JVM...")
        webSocket = HttpClient
            .newHttpClient()
            .newWebSocketBuilder()
            .buildAsync(URI.create("ws://" +
                    "${ConfigurationHandler.get("websocket.host", "localhost", true)}:" +
                    "${ConfigurationHandler.get("websocket.port", 8080, true)}/ws"),
                object : WebSocket.Listener {
                    override fun onOpen(webSocket: WebSocket) = Logger.debug("WebSocket opened.")

                    override fun onClose(webSocket: WebSocket?, statusCode: Int, reason: String?): CompletionStage<*>? {
                        Logger.debug("WebSocket closed.")
                        return super.onClose(webSocket, statusCode, reason)
                    }

                    override fun onError(webSocket: WebSocket, error: Throwable) {
                        Logger.error("WebSocket error: $error")
                        super.onError(webSocket, error)
                    }

                    override fun onText(webSocket: WebSocket, data: CharSequence, last: Boolean): CompletionStage<*>? {
                        Logger.debug("WebSocket received data: $data")
                        callbacks.forEach { callback -> callback(data.toString()) }
                        return super.onText(webSocket, data, last)
                    }
                })
            .join()

        webSocket?.request(1)

        Logger.debug("WebSocket initialized.")
    }

    actual fun send(message: String) = unit(webSocket?.sendText(message, true))

    actual fun receive(callback: (String) -> Unit) = unit(callbacks.add(callback))

    actual fun close() {
        Logger.debug("Closing communication provider on JVM...")
        webSocket?.sendClose(WebSocket.NORMAL_CLOSURE, "Closing")?.join()
        Logger.debug("WebSocket closed.")

        webSocket = null

        callbacks.clear()
        Logger.debug("Communication provider closed.")
    }

}