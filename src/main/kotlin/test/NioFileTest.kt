package test

import net.cakemc.database.file.AbstractDatabaseFolder
import net.cakemc.database.file.NioFolder
import java.nio.charset.StandardCharsets
import java.nio.file.Paths

object NioFileTest {

    @Throws(Throwable::class)
    @JvmStatic
    fun main(args: Array<String>) {
        val folder: AbstractDatabaseFolder = NioFolder(Paths.get("./database_test/"))
        folder.create()

        run {
            val file = folder.getFile("test.db.bin")
            file.memoryFile!!.data = "Hello, World!".toByteArray(StandardCharsets.UTF_8)

            println("writing: ${String(file.memoryFile!!.data)}")
            file.write()
        }

        run {
            val file = folder.getFile("test.db.bin")
            file.read()
            println(String(file.memoryFile!!.data))
        }
    }
}
