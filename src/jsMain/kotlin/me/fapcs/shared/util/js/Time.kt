package me.fapcs.shared.util.js

external fun setTimeout(function: () -> Unit, delay: Long): Int
external fun clearTimeout(handle: Int)
external fun setInterval(function: () -> Unit, delay: Long): Int
external fun clearInterval(id: Int)