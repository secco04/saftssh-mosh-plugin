/*
 * SaftSSH Mosh Plugin — MoshBinaryProvider
 * Copyright (C) 2024  Alessandro Lo Bianco
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, version 3.
 *
 * mosh-client binary is © Keith Winstein et al., GPLv3.
 * Source: https://github.com/mobile-shell/mosh
 */
package de.lobianco.saftssh.mosh

import android.content.ContentProvider
import android.content.ContentValues
import android.database.Cursor
import android.net.Uri
import android.os.Build
import android.os.ParcelFileDescriptor
import android.util.Log
import java.io.File
import java.io.FileNotFoundException

private const val TAG = "MoshBinaryProvider"

/**
 * Exposes the mosh-client binary to the SaftSSH host app via a ContentProvider.
 *
 * URI:  content://de.lobianco.saftssh.mosh.provider/binary
 *
 * The main app first tries to exec the binary directly from this plugin's
 * [nativeLibraryDir] (which is exec-mounted by Android).  This provider serves
 * as a documented fallback and for future extensibility (e.g. version negotiation).
 *
 * Security: callers must hold `de.lobianco.saftssh.mosh.READ_BINARY` permission
 * (declared as `protectionLevel="normal"` — any app can request it).  Since
 * mosh-client is open source GPLv3 software there is no secret to protect; the
 * permission is mainly a courtesy gate.
 */
class MoshBinaryProvider : ContentProvider() {

    override fun onCreate(): Boolean = true

    /**
     * Returns a read-only [ParcelFileDescriptor] for the mosh-client binary.
     * The binary is read from this plugin's own [nativeLibraryDir] which Android
     * installs with exec permissions.
     */
    override fun openFile(uri: Uri, mode: String): ParcelFileDescriptor {
        if (uri.pathSegments.firstOrNull() != "binary") {
            throw FileNotFoundException("Unknown path: $uri")
        }

        val ctx = context ?: throw FileNotFoundException("No context")
        val nativeDir = ctx.applicationInfo.nativeLibraryDir
        val binary = File(nativeDir, "libmosh-client.so")

        Log.i(TAG, "openFile: ${binary.absolutePath} exists=${binary.exists()}")

        if (!binary.exists()) {
            throw FileNotFoundException(
                "libmosh-client.so not found in nativeLibraryDir ($nativeDir). " +
                "Reinstall the SaftSSH Mosh Plugin."
            )
        }

        return ParcelFileDescriptor.open(binary, ParcelFileDescriptor.MODE_READ_ONLY)
    }

    override fun getType(uri: Uri): String = "application/octet-stream"

    // ── Unused stubs required by ContentProvider ─────────────────────────────
    override fun query(u: Uri, p: Array<out String>?, s: String?, sA: Array<out String>?, so: String?): Cursor? = null
    override fun insert(u: Uri, v: ContentValues?): Uri? = null
    override fun delete(u: Uri, s: String?, sA: Array<out String>?): Int = 0
    override fun update(u: Uri, v: ContentValues?, s: String?, sA: Array<out String>?): Int = 0
}
