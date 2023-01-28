package me.fapcs.shared.expect

internal expect object CommunicationProvider {

    val isInitialized: Boolean

    fun init()

    fun send(message: String)

    fun receive(callback: (String) -> Unit)

    fun close()

}