package com.example.`in`.common.validator

import com.example.`in`.common.annotation.FileExtension
import jakarta.validation.ConstraintValidator
import jakarta.validation.ConstraintValidatorContext
import org.springframework.web.multipart.MultipartFile

class FileExtensionValidator : ConstraintValidator<FileExtension, MultipartFile> {
    private lateinit var allowedExtensions: Array<String>

    override fun initialize(constraintAnnotation: FileExtension) {
        allowedExtensions = constraintAnnotation.allowExtensions
    }

    override fun isValid(value: MultipartFile?, context: ConstraintValidatorContext?): Boolean {
        if (value == null) return true

        if (allowedExtensions.isEmpty()) return true

        val fileName = value.originalFilename ?: return false

        val fileExtension = fileName.substring(fileName.lastIndexOf(".") + 1)

        if (fileExtension.isEmpty()) return false

        for (extension in allowedExtensions) {
            if (extension.lowercase() == fileExtension.lowercase()) return true
        }

        return false
    }
}
