package me.fapcs.shared.communication.handling

import kotlinx.serialization.KSerializer
import me.fapcs.shared.communication.IPacket
import me.fapcs.shared.util.json.JsonDocument
import kotlin.reflect.KClass

interface ICommunicationHandler {

    fun <T : IPacket> register(packet: KClass<T>, serializer: KSerializer<T>)

    fun send(packet: IPacket)

    fun sendRaw(data: JsonDocument)

    fun <T : IPacket> receiveAll(packet: KClass<T>, callback: (T) -> Unit)

    fun receiveAll(callback: (IPacket) -> Unit)

    fun close()

    companion object {

        fun create(): ICommunicationHandler = CommunicationHandler()

    }

}