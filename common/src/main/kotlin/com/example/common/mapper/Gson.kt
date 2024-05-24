package com.example.common.mapper

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.TypeAdapter
import com.google.gson.reflect.TypeToken
import com.google.gson.stream.JsonReader
import com.google.gson.stream.JsonWriter
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

val gson: Gson = GsonBuilder()
    .registerTypeAdapter(LocalDateTime::class.java, LocalDateTimeAdapter())
    .create()

fun <T> T.serializeToMap(): Map<String, Any> {
    return convert()
}

inline fun <reified T> Map<String, Any>.toDataClass(): T {
    return convert()
}

inline fun <I, reified O> I.convert(): O {
    val json = gson.toJson(this)
    return gson.fromJson(json, object : TypeToken<O>() {}.type)
}

class LocalDateTimeAdapter : TypeAdapter<LocalDateTime>() {
    private val formatter = DateTimeFormatter.ISO_LOCAL_DATE_TIME

    override fun write(out: JsonWriter, value: LocalDateTime) {
        out.value(value.format(formatter))
    }

    override fun read(input: JsonReader): LocalDateTime {
        return LocalDateTime.parse(input.nextString(), formatter)
    }
}
