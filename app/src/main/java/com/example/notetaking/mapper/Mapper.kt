package com.example.notetaking.mapper

/**
 * Base generic mapper class
 */
abstract class Mapper<I, O> {
    abstract fun from(i: I): O
    fun from(list: List<I>): List<O> = list.map { from(it) }

    abstract fun to(o: O): I
    fun to(list: List<O>): List<I> = list.map { to(it) }
}