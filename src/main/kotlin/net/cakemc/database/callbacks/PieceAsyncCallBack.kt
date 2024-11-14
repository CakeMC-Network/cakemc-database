package net.cakemc.database.callbacks

import net.cakemc.database.api.Piece

/**
 * The interface Piece async call back.
 */
fun interface PieceAsyncCallBack : AsyncCallBack<Piece?> {

    override fun acceptException(exception: Exception) {
        super.acceptException(exception)
    }
}
