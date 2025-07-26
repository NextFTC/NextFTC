package dev.nextftc.ftc

import android.content.Context
import com.qualcomm.robotcore.util.WebHandlerManager
import dev.nextftc.ftc.components.LOG_ROOT
import fi.iki.elonen.NanoHTTPD
import fi.iki.elonen.NanoHTTPD.IHTTPSession
import org.firstinspires.ftc.ftccommon.external.WebHandlerRegistrar
import java.io.File
import java.io.FileInputStream

object LogServer {
    @WebHandlerRegistrar
    @JvmStatic
    fun registerRoutes(context: Context, manager: WebHandlerManager) {
        manager.register("/nextftc/logs") { session ->
            val fs = LOG_ROOT.listFiles()!!.sortedByDescending { it.lastModified() }
            val html = buildString {
                append("<!doctype html><html><head><title>Logs</title></head><body><ul>")
                append(
                    fs.joinToString("\n") { f ->
                        """
                        <li>
                            <a href="/logs/download?file=${f.name}" download="${f.name}">${f.name}</a>
                            (${f.length()} bytes)
                        </li>
                        """.trimIndent()
                    }
                )
                append("</ul></body></html>")
            }
            NanoHTTPD.newFixedLengthResponse(NanoHTTPD.Response.Status.OK,
                NanoHTTPD.MIME_HTML, html)
        }

        manager.register("/nextftc/logs/download") { session: IHTTPSession ->
            val pairs = session.queryParameterString.split("&".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
            if (pairs.size != 1) {
                return@register NanoHTTPD.newFixedLengthResponse(NanoHTTPD.Response.Status.BAD_REQUEST,
                    NanoHTTPD.MIME_PLAINTEXT, "expected one query parameter, got " + pairs.size)
            }
            val parts = pairs[0].split("=".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
            if (parts[0] != "file") {
                return@register NanoHTTPD.newFixedLengthResponse(NanoHTTPD.Response.Status.BAD_REQUEST,
                    NanoHTTPD.MIME_PLAINTEXT, "expected file query parameter, got " + parts[0])
            }
            val f = File(LOG_ROOT, parts[1])
            if (!f.exists()) {
                return@register NanoHTTPD.newFixedLengthResponse(NanoHTTPD.Response.Status.NOT_FOUND,
                    NanoHTTPD.MIME_PLAINTEXT, "file $f doesn't exist")
            }
            NanoHTTPD.newChunkedResponse(NanoHTTPD.Response.Status.OK,
                "application/json", FileInputStream(f))
        }
    }
}
