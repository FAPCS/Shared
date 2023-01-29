package me.fapcs.shared.communication

import kotlinx.serialization.KSerializer
import me.fapcs.shared.communication.handling.ICommunicationHandler
import kotlin.reflect.KClass

@Suppress("unused")
object CommunicationHandler {

    private var handler: ICommunicationHandler? = null

    @Suppress("MemberVisibilityCanBePrivate")
    fun getHandler(): ICommunicationHandler {
        if (handler == null) handler = ICommunicationHandler.create()
        return handler!!
    }

    fun <T : IPacket> register(packet: KClass<T>, serializer: KSerializer<T>) = getHandler().register(packet, serializer)

    inline fun <reified T : IPacket> register(serializer: KSerializer<T>) = register(T::class, serializer)

    fun send(packet: IPacket) = getHandler().send(packet)

    fun <T : IPacket> receive(packet: KClass<T>, callback: (T) -> Unit) = getHandler().receiveAll(packet, callback)

    inline fun <reified T : IPacket> receive(noinline callback: (T) -> Unit) = receive(T::class, callback)

    fun receiveAll(callback: (IPacket) -> Unit) = getHandler().receiveAll(callback)

    fun close() = getHandler().close()

}