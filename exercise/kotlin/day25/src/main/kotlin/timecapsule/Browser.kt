package timecapsule

import java.awt.Desktop.*
import java.io.File

object Browser {
    fun open(filePath: String) = if (isDesktopSupported() && getDesktop().isSupported(Action.OPEN))
        getDesktop().open(File(filePath))
    else println("Opening files is not supported on this system.")
}