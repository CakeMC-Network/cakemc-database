package net.cakemc.database.cursor

import net.cakemc.database.api.Piece

/**
 * The interface Piece cursor supplier.
 */
interface PieceCursorSupplier : CursorSupplier<Piece?, Cursor<Piece?>?>
