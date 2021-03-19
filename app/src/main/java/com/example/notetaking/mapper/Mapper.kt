package com.example.notetaking.mapper

/**
 * Base generic mapper class
 */
abstract class Mapper<in I, out O> {
    abstract fun from(i: I): O
    fun from(list: List<I>): List<O> = list.map { from(it) }
}