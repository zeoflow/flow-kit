package com.zeoflow.compressor

import android.graphics.Bitmap
import android.graphics.Bitmap.CompressFormat
import com.zeoflow.initializer.ZeoFlowApp
import com.zeoflow.utils.ImageUtil
import io.reactivex.Flowable
import java.io.File
import java.io.IOException
import java.util.concurrent.Callable

class JavaCompressor {
    //max width and height values of the compressed image is taken as 612x816
    private var maxWidth = 612
    private var maxHeight = 816
    private var compressFormat = CompressFormat.JPEG
    private var quality = 80
    private var destinationDirectoryPath: String
    fun setMaxWidth(maxWidth: Int): JavaCompressor {
        this.maxWidth = maxWidth
        return this
    }

    fun setMaxHeight(maxHeight: Int): JavaCompressor {
        this.maxHeight = maxHeight
        return this
    }

    fun setCompressFormat(compressFormat: CompressFormat): JavaCompressor {
        this.compressFormat = compressFormat
        return this
    }

    fun setQuality(quality: Int): JavaCompressor {
        this.quality = quality
        return this
    }

    fun setDestinationDirectoryPath(destinationDirectoryPath: String): JavaCompressor {
        this.destinationDirectoryPath = destinationDirectoryPath
        return this
    }

    @JvmOverloads
    @Throws(IOException::class)
    fun compressToFile(imageFile: File, compressedFileName: String = imageFile.name): File {
        return ImageUtil.compressImage(imageFile, maxWidth, maxHeight, compressFormat, quality,
                destinationDirectoryPath + File.separator + compressedFileName)
    }

    @Throws(IOException::class)
    fun compressToBitmap(imageFile: File?): Bitmap {
        return ImageUtil.decodeSampledBitmapFromFile(imageFile, maxWidth, maxHeight)
    }

    @JvmOverloads
    fun compressToFileAsFlowable(imageFile: File, compressedFileName: String = imageFile.name): Flowable<File> {
        return Flowable.defer(Callable<Flowable<File>> {
            try {
                return@Callable Flowable.just(compressToFile(imageFile, compressedFileName))
            } catch (e: IOException) {
                return@Callable Flowable.error<File>(e)
            }
        })
    }

    fun compressToBitmapAsFlowable(imageFile: File?): Flowable<Bitmap> {
        return Flowable.defer(Callable<Flowable<Bitmap>> {
            try {
                return@Callable Flowable.just(compressToBitmap(imageFile))
            } catch (e: IOException) {
                return@Callable Flowable.error<Bitmap>(e)
            }
        })
    }

    init {
        destinationDirectoryPath = ZeoFlowApp.getContext().cacheDir.path + File.separator + "images"
    }
}