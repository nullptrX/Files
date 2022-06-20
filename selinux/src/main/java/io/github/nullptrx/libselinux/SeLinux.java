package io.github.nullptrx.libselinux;

import android.system.ErrnoException;

import androidx.annotation.NonNull;

import java.io.FileDescriptor;

public class SeLinux {

  static {
    System.loadLibrary("selinux-jni");
  }

  private SeLinux() {
  }

  @NonNull
  public static native byte[] fgetfilecon(@NonNull FileDescriptor fd) throws ErrnoException;

  public static native void fsetfilecon(@NonNull FileDescriptor fd, @NonNull byte[] context)
      throws ErrnoException;

  @NonNull
  public static native byte[] getfilecon(@NonNull byte[] path) throws ErrnoException;

  public static native boolean is_selinux_enabled();

  @NonNull
  public static native byte[] lgetfilecon(@NonNull byte[] path) throws ErrnoException;

  public static native void lsetfilecon(@NonNull byte[] path, @NonNull byte[] context)
      throws ErrnoException;

  public static native boolean security_getenforce() throws ErrnoException;

  public static native void setfilecon(@NonNull byte[] path, @NonNull byte[] context)
      throws ErrnoException;
}
