package me.fapcs.shared.communication.handling

import kotlinx.serialization.KSerializer
import kotlinx.serialization.json.Json
import me.fapcs.shared.communication.CommunicationHandler
import me.fapcs.shared.communication.IPacket
import me.fapcs.shared.communication.UnknownPacket
import me.fapcs.shared.expect.CommunicationProvider
import me.fapcs.shared.log.Logger
import me.fapcs.shared.module.brightness.packet.BrightnessChangePacket
import me.fapcs.shared.module.brightness.packet.BrightnessRequestPacket
import me.fapcs.shared.module.camouflage.packet.SetDarkenInsidePacket
import me.fapcs.shared.module.camouflage.packet.SetDarkenOutsidePacket
import me.fapcs.shared.module.camouflage.packet.SetIRLightPacket
import me.fapcs.shared.module.general.packet.KeepAlivePacket
import me.fapcs.shared.module.light.packet.SendLightConfigurationPacket
import me.fapcs.shared.module.matrix.big.packet.DisableBigMatrixPacket
import me.fapcs.shared.module.matrix.big.packet.UpdateBigMatrixPacket
import me.fapcs.shared.module.matrix.small.packet.DisableSmallMatrixSidePacket
import me.fapcs.shared.module.matrix.small.packet.UpdateSmallMatrixPacket
import me.fapcs.shared.module.siren.packet.SetMicrophoneStatePacket
import me.fapcs.shared.module.siren.packet.SetSirenPacket
import me.fapcs.shared.module.stripe.packet.SetLedPacket
import me.fapcs.shared.module.stripe.packet.SetLedsPacket
import me.fapcs.shared.util.json.JsonDocument
import me.fapcs.shared.util.setInterval
import me.fapcs.shared.util.unit
import kotlin.reflect.KClass
import kotlin.reflect.cast

internal class CommunicationHandler : ICommunicationHandler {

    private val packets = mutableListOf<PacketHolder<*>>(
        PacketHolder(UnknownPacket::class, UnknownPacket.serializer()),
        PacketHolder(KeepAlivePacket::class, KeepAlivePacket.serializer()),

        PacketHolder(BrightnessChangePacket::class, BrightnessChangePacket.serializer()),
        PacketHolder(BrightnessRequestPacket::class, BrightnessRequestPacket.serializer()),

        PacketHolder(SetLedPacket::class, SetLedPacket.serializer()),
        PacketHolder(SetLedsPacket::class, SetLedsPacket.serializer()),

        PacketHolder(SendLightConfigurationPacket::class, SendLightConfigurationPacket.serializer()),

        PacketHolder(SetDarkenInsidePacket::class, SetDarkenInsidePacket.serializer()),
        PacketHolder(SetDarkenOutsidePacket::class, SetDarkenOutsidePacket.serializer()),
        PacketHolder(SetIRLightPacket::class, SetIRLightPacket.serializer()),

        PacketHolder(SetMicrophoneStatePacket::class, SetMicrophoneStatePacket.serializer()),
        PacketHolder(SetSirenPacket::class, SetSirenPacket.serializer()),

        PacketHolder(DisableSmallMatrixSidePacket::class, DisableSmallMatrixSidePacket.serializer()),
        PacketHolder(UpdateSmallMatrixPacket::class, UpdateSmallMatrixPacket.serializer()),

        PacketHolder(DisableBigMatrixPacket::class, DisableBigMatrixPacket.serializer()),
        PacketHolder(UpdateBigMatrixPacket::class, UpdateBigMatrixPacket.serializer()),
    )
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

        Logger.debug("Initializing keep-alive...")
        setInterval(10000) {
            Logger.debug("Sending keep-alive...")

            if (CommunicationProvider.isInitialized) CommunicationHandler.send(KeepAlivePacket)
            else this.clear()

            Logger.debug("Keep-alive sent!")
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