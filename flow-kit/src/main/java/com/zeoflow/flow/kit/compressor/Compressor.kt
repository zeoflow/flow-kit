package com.zeoflow.flow.kit.compressor

import com.zeoflow.flow.kit.compressor.constraint.Compression
import com.zeoflow.flow.kit.compressor.constraint.default
import com.zeoflow.flow.kit.initializer.ZeoFlowApp.getContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.File
import kotlin.coroutines.CoroutineContext

object Compressor {
    suspend fun compress(
            imageFile: File,
            coroutineContext: CoroutineContext = Dispatchers.IO,
            compressionPatch: Compression.() -> Unit = { default() }
    ) = withContext(coroutineContext) {
        val compression = Compression().apply(compressionPatch)
        var result = copyToCache(getContext(), imageFile)
        compression.constraints.forEach { constraint ->
            while (constraint.isSatisfied(result).not()) {
                result = constraint.satisfy(result)
            }
        }
        return@withContext result
    }
}