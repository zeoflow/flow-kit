package com.zeoflow.compressor

import com.zeoflow.compressor.constraint.Compression
import com.zeoflow.compressor.constraint.default
import com.zeoflow.initializer.ZeoFlowApp.getContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.File
import kotlin.coroutines.CoroutineContext

object Compressor {

    @JvmStatic
    fun javaVariant(): JavaCompressor {
        return JavaCompressor()
    }

    @JvmStatic
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