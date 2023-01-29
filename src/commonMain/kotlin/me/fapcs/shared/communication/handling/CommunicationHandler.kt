package me.fapcs.shared.communication.handling

import kotlinx.serialization.KSerializer
import kotlinx.serialization.json.Json
import me.fapcs.shared.communication.IPacket
import me.fapcs.shared.communication.UnknownPacket
import me.fapcs.shared.expect.CommunicationProvider
import me.fapcs.shared.log.Logger
import me.fapcs.shared.util.json.JsonDocument
import me.fapcs.shared.util.unit
import kotlin.reflect.KClass
import kotlin.reflect.cast

internal class CommunicationHandler : ICommunicationHandler {

    private val packets = mutableListOf<PacketHolder<*>>()
    private val listeners = mutableListOf<(IPacket) -> Unit>()

    init {
        Logger.info("Initializing Communication...")

        Logger.debug("Checking if CommunicationProvider is initialized...")
        if (!CommunicationProvider.isInitialized) {
            Logger.debug("CommunicationProvider is not initialized, initializing...")
            CommunicationProvider.init()

            if (!CommunicationProvider.isInitialized) {
                Logger.fatal("Failed to initialize CommunicationProvider!")
                throw IllegalStateException("Failed to initialize CommunicationProvider!")
            } else Logger.debug("CommunicationProvider initialized!")
        } else Logger.debug("CommunicationProvider is already initialized!")


        Logger.debug("Listening for incoming packets...")
        CommunicationProvider.receive { message ->
            Logger.debug("Received packet: $message")
            val document = JsonDocument(message)

            Logger.debug("Parsing packet...")
            val packet = document.getString("packet") ?: "unknown"
            Logger.debug("Packet: $packet")

            Logger.debug("Parsing data...")
            val data = document.getString("data") ?: "{}"
            Logger.debug("Data: $data")

            Logger.debug("Getting packet instance...")
            val packetHolder = packets.firstOrNull { it.packet.simpleName == packet }
            Logger.debug("Packet instance: ${packetHolder?.packet?.simpleName ?: "unknown"}")

            val packetInstance =
                if (packetHolder == null) UnknownPacket(JsonDocument(data))
                else Json.decodeFromString(packetHolder.serializer, data)

            Logger.debug("Invoking listeners...")
            listeners.forEach { it(packetInstance) }
        }

        Logger.info("Communication initialized!")
    }

    override fun <T : IPacket> register(packet: KClass<T>, serializer: KSerializer<T>) {
        Logger.debug("Registering packet: ${packet.simpleName}")
        if (packets.any { it.packet == packet }) {
            Logger.error("Packet ${packet.simpleName} is already registered!")
            throw IllegalArgumentException("Packet ${packet.simpleName} is already registered!")
        }

        packets.add(PacketHolder(packet, serializer))
        Logger.info("Registered packet ${packet.simpleName}")
    }

    override fun send(packet: IPacket) {
        Logger.debug("Sending packet: ${packet::class.simpleName}")
        if (!CommunicationProvider.isInitialized)  {
            Logger.fatal("CommunicationProvider is not initialized!")
            throw IllegalStateException("CommunicationProvider is not initialized!")
        }

        Logger.debug("Getting packet holder...")
        val packetHolder = packets.firstOrNull { it.packet == packet::class }
            ?: throw IllegalArgumentException("Packet ${packet::class.simpleName} is not registered!")
        Logger.debug("Packet holder: ${packetHolder.packet.simpleName}")

        Logger.debug("Creating document...")
        val document = JsonDocument()
            .set("packet", packet::class.simpleName ?: "unknown")
            .set("data", packetHolder.encode(packet))
        Logger.debug("Document: $document")

        Logger.debug("Sending packet...")
        CommunicationProvider.send(document.toString())
    }

    override fun <T : IPacket> receiveAll(packet: KClass<T>, callback: (T) -> Unit) = receiveAll {
        if (packet.isInstance(it)) callback(packet.cast(it))
    }

    override fun receiveAll(callback: (IPacket) -> Unit) = unit(listeners.add(callback))

    override fun close() {
        CommunicationProvider.close()
    }
}

data class PacketHolder<T : IPacket>(val packet: KClass<T>, val serializer: KSerializer<T>) {

    fun encode(packet: IPacket): String {
        Logger.debug("Encoding packet: ${packet::class.simpleName}")
        if (!this.packet.isInstance(packet)) {
            Logger.error("Packet ${packet::class.simpleName} is not assignable from ${this.packet.simpleName}!")
            throw IllegalArgumentException("Packet ${packet::class.simpleName} is not assignable from ${this.packet.simpleName}!")
        }

        Logger.debug("Creating JSON string...")
        return Json.encodeToString(serializer, this.packet.cast(packet))
    }

}