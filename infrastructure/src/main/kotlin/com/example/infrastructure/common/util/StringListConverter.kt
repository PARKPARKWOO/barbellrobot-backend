package com.example.infrastructure.common.util

import jakarta.persistence.AttributeConverter
import jakarta.persistence.Converter

@Converter
class StringListConverter : AttributeConverter<MutableList<String>, String> {

    override fun convertToDatabaseColumn(attribute: MutableList<String>?): String {
        return attribute?.joinToString(",") ?: ""
    }

    override fun convertToEntityAttribute(dbData: String?): MutableList<String> {
        return dbData?.split(",")?.toMutableList() ?: mutableListOf()
    }
}
