package me.fapcs.shared.config.handling

import kotlin.reflect.KClass

interface IConfigurationHandler {

    fun <T : Any> create(klass: KClass<T>, key: String, value: T)

    fun <T : Any> get(klass: KClass<T>, key: String): T?

    fun <T : Any> get(klass: KClass<T>, key: String, default: T, create: Boolean = true): T

    companion object {

        fun create(): IConfigurationHandler = ConfigurationHandler()

    }


}