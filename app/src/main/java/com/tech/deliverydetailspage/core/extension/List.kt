package com.tech.deliverydetailspage.core.extension

fun <T> MutableList<T>.replace(newValue: T, block: (T) -> Boolean): MutableList<T> {
    return map {
        if (block(it)) newValue else it
    }.toMutableList()
}