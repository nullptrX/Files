package io.github.nullptrx.files.provider.content

import android.net.Uri
import android.os.Parcelable
import io.github.nullptrx.files.provider.common.attrs.FileTimeParceler
import io.github.nullptrx.files.provider.common.content.AbstractContentProviderFileAttributes
import java8.nio.file.attribute.FileTime
import kotlinx.parcelize.Parcelize
import kotlinx.parcelize.WriteWith
import org.threeten.bp.Instant

@Parcelize
internal class ContentFileAttributes(
  override val lastModifiedTime: @WriteWith<FileTimeParceler> FileTime,
  override val mimeType: String?,
  override val size: Long,
  override val fileKey: Parcelable
) : AbstractContentProviderFileAttributes() {
  companion object {
    fun from(mimeType: String?, size: Long, uri: Uri): ContentFileAttributes {
      val lastModifiedTime = FileTime.from(Instant.EPOCH)
      val fileKey = uri
      return ContentFileAttributes(lastModifiedTime, mimeType, size, fileKey)
    }
  }
}
