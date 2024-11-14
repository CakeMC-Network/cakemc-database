package test

import net.cakemc.database.AbstractDatabase
import net.cakemc.database.DefaultDatabase
import net.cakemc.database.api.Piece
import net.cakemc.database.callbacks.AsyncCallBack
import net.cakemc.database.callbacks.DatabaseListener
import net.cakemc.database.collection.Collection
import net.cakemc.database.filter.Filters.eq
import java.nio.file.Paths

object DatabaseTest {
    @JvmStatic
    fun main(args: Array<String>) {
        val database: AbstractDatabase = DefaultDatabase(Paths.get("./database_test/"))
        database.load()

        test1()

        println(database.getCollection("players")!!.collect())
    }

    fun test1() {
        val database: AbstractDatabase = DefaultDatabase(Paths.get("./database_test/"))
        database.load()

        val collection: Collection<*> = database.getCollection("players")

        collection.singlePieceAsync(
            eq("name", "u64Lisa"),
            { piece: Piece?, state: AsyncCallBack.State?, exception: Exception? ->
                when (state) {
                    AsyncCallBack.State.FOUND -> {
                        println("found document: ${piece}")
                    }

                    AsyncCallBack.State.NOT_FOUND -> {
                        val newPiece = collection.defineOnePeace()
                        newPiece.add("name", "u64Lisa")
                        newPiece.add("coins", 1)
                        newPiece.add("setting", true)

                        collection.insertOnePieceAsync(newPiece
                        ) { state1: DatabaseListener.State?, exception1: Exception? ->
                            when (state1) {
                                DatabaseListener.State.FAILED -> exception1?.printStackTrace()
                                DatabaseListener.State.SUCCESS -> {
                                    println("created new player document!")
                                    database.save()
                                }

                                null -> {}
                            }
                        }
                    }

                    AsyncCallBack.State.ERROR -> exception?.printStackTrace()
                    null -> {}
                }
            })
    }
}
