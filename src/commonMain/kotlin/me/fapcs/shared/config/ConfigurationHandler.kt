package me.fapcs.shared.config

import me.fapcs.shared.config.handling.IConfigurationHandler
import kotlin.reflect.KClass

object ConfigurationHandler {

    private var handler: IConfigurationHandler? = null

    @Suppress("MemberVisibilityCanBePrivate")
    fun getHandler(): IConfigurationHandler {
        if (handler == null) handler = IConfigurationHandler.create()
        return handler!!
    }

    fun <T : Any> create(klass: KClass<T>, key: String, value: T) = getHandler().create(klass, key, value)

    inline fun <reified T : Any> create(key: String, value: T) = create(T::class, key, value)

    fun <T : Any> get(klass: KClass<T>, key: String): T? = getHandler().get(klass, key)

    inline fun <reified T : Any> get(key: String): T? = get(T::class, key)

    fun <T : Any> get(klass: KClass<T>, key: String, default: T, create: Boolean = true): T =
        getHandler().get(klass, key, default, create)

    inline fun <reified T : Any> get(key: String, default: T, create: Boolean = true): T =
        get(T::class, key, default, create)

}