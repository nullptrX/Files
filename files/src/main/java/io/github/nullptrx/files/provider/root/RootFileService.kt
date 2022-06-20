package io.github.nullptrx.files.provider.root

import android.annotation.SuppressLint
import android.content.Context
import android.util.Log
import io.github.nullptrx.files.FileSystemProviders
import io.github.nullptrx.files.provider.remote.RemoteFileService
import io.github.nullptrx.files.provider.remote.RemoteInterface
import io.github.nullptrx.files.util.lazyReflectedMethod

@SuppressLint("StaticFieldLeak")
lateinit var rootContext: Context
  private set

object RootFileService : RemoteFileService(
  RemoteInterface {
    if (SuiFileServiceLauncher.isSuiAvailable()) {
      SuiFileServiceLauncher.launchService()
    } else {
      LibSuFileServiceLauncher.launchService()
    }
  }
) {
  const val TIMEOUT_MILLIS = 30 * 1000L

  private val LOG_TAG = RootFileService::class.java.simpleName

  // Not actually restricted because there's no restriction when running as root.
  //@RestrictedHiddenApi
  private val activityThreadCurrentActivityThreadMethod by lazyReflectedMethod(
    "android.app.ActivityThread", "currentActivityThread"
  )

  //@RestrictedHiddenApi
  private val activityThreadGetSystemContextMethod by lazyReflectedMethod(
    "android.app.ActivityThread", "getSystemContext"
  )

  fun run() {
    Log.i(LOG_TAG, "Creating package context")
    rootContext = createPackageContext("io.github.nullptrx.files.example")
    // rootContext = createPackageContext("")
    Log.i(LOG_TAG, "Installing file system providers")
    FileSystemProviders.install()
    FileSystemProviders.overflowWatchEvents = true
  }

  private fun createPackageContext(packageName: String): Context {
    val activityThread = activityThreadCurrentActivityThreadMethod.invoke(null)
    val systemContext = activityThreadGetSystemContextMethod.invoke(activityThread) as Context
    return systemContext.createPackageContext(
      packageName, Context.CONTEXT_IGNORE_SECURITY or Context.CONTEXT_INCLUDE_CODE
    )
  }
}
