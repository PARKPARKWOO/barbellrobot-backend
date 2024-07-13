package com.example.infrastructure.adapter.multimedia

import com.amazonaws.services.s3.AmazonS3
import com.amazonaws.services.s3.model.ObjectMetadata
import com.amazonaws.services.s3.model.PutObjectRequest
import com.example.core.multimedia.dto.MultimediaDto
import com.example.core.multimedia.port.out.S3Port
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.coroutineScope
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component
import java.util.UUID

@Component
class S3Adapter(
    private val amazonS3: AmazonS3,
    @Value("\${cloud-front.domain}")
    private val cloudFrontDomain: String,
    @Value("\${aws.s3.bucket-name}")
    private val bucketName: String,
) : S3Port {
    override suspend fun uploadFiles(files: List<MultimediaDto>): List<String>? {
        return coroutineScope {
            files.map { file ->
                val metadata = ObjectMetadata().apply {
                    this.contentType = file.contentType
                    this.contentLength = file.contentLength
                }
                async {
                    val fileName = UUID.randomUUID().toString()
                    val putObjectRequest = PutObjectRequest(
                        bucketName,
                        fileName,
                        file.inputStream,
                        metadata,
                    )
                    amazonS3.putObject(putObjectRequest)
                    "$cloudFrontDomain/$fileName"
                }
            }
        }.awaitAll()
    }
}
