package net.cakemc.database.cursor

import net.cakemc.database.api.Piece

/**
 * The interface Piece cursor supplier.
 */
fun interface PieceCursorSupplier : CursorSupplier<Piece, Cursor<Piece>>
