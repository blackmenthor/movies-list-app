package tech.arifandi.movielistapp.utils

internal interface Converter<I, O> {
    fun convert(input: I): O
}