package com.example.`in`.common.annotation

import com.example.`in`.common.validator.FileExtensionListValidator
import com.example.`in`.common.validator.FileExtensionValidator
import jakarta.validation.Constraint
import jakarta.validation.Payload
import kotlin.reflect.KClass

@Target(AnnotationTarget.VALUE_PARAMETER)
@Retention(AnnotationRetention.RUNTIME)
@Constraint(validatedBy = [FileExtensionValidator::class, FileExtensionListValidator::class])
annotation class FileExtension(
    val allowExtensions: Array<String>,
    val message: String = "Invalid file extension",
    val groups: Array<KClass<*>> = [],
    val payload: Array<KClass<out Payload>> = [],
)
