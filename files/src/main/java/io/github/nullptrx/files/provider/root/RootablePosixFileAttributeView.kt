package io.github.nullptrx.files.provider.root

import android.os.Parcelable
import io.github.nullptrx.files.provider.common.posix.*
import io.github.nullptrx.files.provider.common.protobuf.ByteString
import java8.nio.file.Path
import java8.nio.file.attribute.FileTime
import java.io.IOException

abstract class RootablePosixFileAttributeView(
  private val path: Path,
  private val localAttributeView: PosixFileAttributeView,
  rootAttributeViewCreator: (PosixFileAttributeView) -> RootPosixFileAttributeView
) : PosixFileAttributeView, Parcelable {
  private val rootAttributeView: RootPosixFileAttributeView = rootAttributeViewCreator(this)

  override fun name(): String = localAttributeView.name()

  @Throws(IOException::class)
  override fun setTimes(
    lastModifiedTime: FileTime?,
    lastAccessTime: FileTime?,
    createTime: FileTime?
  ) {
    callRootable(path) { setTimes(lastModifiedTime, lastAccessTime, createTime) }
  }

  @Throws(IOException::class)
  override fun readAttributes(): PosixFileAttributes = callRootable(path) { readAttributes() }

  @Throws(IOException::class)
  override fun setOwner(owner: PosixUser) {
    callRootable(path) { setOwner(owner) }
  }

  @Throws(IOException::class)
  override fun setGroup(group: PosixGroup) {
    callRootable(path) { setGroup(group) }
  }

  @Throws(IOException::class)
  override fun setMode(mode: Set<PosixFileModeBit>) {
    callRootable(path) { setMode(mode) }
  }

  @Throws(IOException::class)
  override fun setSeLinuxContext(context: ByteString) {
    callRootable(path) { setSeLinuxContext(context) }
  }

  @Throws(IOException::class)
  override fun restoreSeLinuxContext() {
    callRootable(path) { restoreSeLinuxContext() }
  }

  @Throws(IOException::class)
  private fun <R> callRootable(path: Path, block: PosixFileAttributeView.() -> R): R =
    callRootable(path, true, localAttributeView, rootAttributeView, block)
}
