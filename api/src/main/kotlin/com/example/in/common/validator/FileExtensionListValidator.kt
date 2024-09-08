package com.example.`in`.common.validator

import com.example.`in`.common.annotation.FileExtension
import jakarta.validation.ConstraintValidator
import jakarta.validation.ConstraintValidatorContext
import org.springframework.web.multipart.MultipartFile

class FileExtensionListValidator : ConstraintValidator<FileExtension, List<MultipartFile>> {

    private lateinit var allowedExtensions: Array<String>

    override fun initialize(constraintAnnotation: FileExtension) {
        this.allowedExtensions = constraintAnnotation.allowExtensions
    }

    override fun isValid(value: List<MultipartFile>?, context: ConstraintValidatorContext?): Boolean {
        if (value.isNullOrEmpty()) return true

        return value.all { file ->
            val fileName = file.originalFilename ?: return@all false
            val fileExtension = fileName.substringAfterLast(".", "")
            allowedExtensions.any { it.equals(fileExtension, ignoreCase = true) }
        }
    }
}
