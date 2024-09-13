package net.cakemc.database.filter;

/**
 * The interface Filter.
 *
 * @param <Document> the type parameter
 */
public interface Filter<Document> {
    /**
     * Matches boolean.
     *
     * @param document the document
     * @return the boolean
     */
    public boolean matches(Document document);

}