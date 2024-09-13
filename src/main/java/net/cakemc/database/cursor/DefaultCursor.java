package net.cakemc.database.cursor;

import net.cakemc.database.api.Piece;
import net.cakemc.database.filter.Filter;

import java.util.Comparator;
import java.util.List;

/**
 * The type Default cursor.
 */
public class DefaultCursor extends Cursor<Piece> {

    private List<Piece> pieces;

    /**
     * Instantiates a new Default cursor.
     *
     * @param pieces the pieces
     */
    public DefaultCursor(List<Piece> pieces) {
        this.pieces = pieces;
    }

    /**
     * Gets pieces.
     *
     * @return the pieces
     */
    public List<Piece> getPieces() {
        return pieces;
    }


    @Override
    public Cursor<Piece> limit(int number) {
        this.pieces = pieces.stream().limit(number).toList();
        return this;
    }

    @Override
    public Cursor<Piece> sort(Comparator<Piece> comparator) {
        pieces.sort(comparator);
        return this;
    }

    @Override
    public int index(Piece element) {
        return pieces.indexOf(element);
    }

    @Override
    public int index(Filter<Piece> filter) {
        Piece single = pieces.stream()
                .filter(filter::matches)
                .findFirst()
                .orElse(null);

        if (single == null)
            return -1;

        return pieces.indexOf(single);
    }

    @Override
    public List<Piece> collect() {
        return pieces;
    }
}
