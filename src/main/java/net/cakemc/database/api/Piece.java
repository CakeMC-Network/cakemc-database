package net.cakemc.database.api;

import java.io.Serializable;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * The type Piece.
 */
public class Piece extends DatabaseRecord implements Serializable {

    private final Map<String, Object> elements;

    /**
     * Instantiates a new Piece.
     *
     * @param index    the index
     * @param id       the id
     * @param elements the elements
     */
    public Piece(int index, long id, Map<String, Object> elements) {
        super(index, id);
        this.elements = elements;
    }

    /**
     * Add.
     *
     * @param key   the key
     * @param value the value
     */
    public Piece add(String key, int value) {
        this.elements.put(key, value);
        return this;
    }

    /**
     * Add.
     *
     * @param key   the key
     * @param value the value
     */
    public Piece add(String key, long value) {
        this.elements.put(key, value);
        return this;
    }

    /**
     * Add.
     *
     * @param key   the key
     * @param value the value
     */
    public Piece add(String key, double value) {
        this.elements.put(key, value);
        return this;
    }

    /**
     * Add.
     *
     * @param key   the key
     * @param value the value
     */
    public Piece add(String key, float value) {
        this.elements.put(key, value);
        return this;
    }

    /**
     * Add.
     *
     * @param key   the key
     * @param value the value
     */
    public Piece add(String key, boolean value) {
        this.elements.put(key, value);
        return this;
    }

    /**
     * Add.
     *
     * @param key   the key
     * @param value the value
     */
    public Piece add(String key, char value) {
        this.elements.put(key, value);
        return this;
    }

    /**
     * Add.
     *
     * @param key   the key
     * @param value the value
     */
    public Piece add(String key, byte value) {
        this.elements.put(key, value);
        return this;
    }

    /**
     * Add.
     *
     * @param key   the key
     * @param value the value
     */
    public Piece add(String key, short value) {
        this.elements.put(key, value);
        return this;
    }

    /**
     * Add.
     *
     * @param key   the key
     * @param value the value
     */
    public Piece add(String key, String value) {
        this.elements.put(key, value);
        return this;
    }

    /**
     * Set.
     *
     * @param <T>   the type parameter
     * @param key   the key
     * @param value the value
     */
    public <T> Piece set(String key, T value) {
        this.elements.put(key, value);
        return this;
    }

    /**
     * Get t.
     *
     * @param <T>  the type parameter
     * @param key  the key
     * @param type the type
     * @return the t
     */
    public <T> T get(String key, Class<T> type) {
        Object value = this.elements.get(key);
        if (type.isInstance(value)) {
            return type.cast(value);
        }
        throw new ClassCastException("Value for key '" + key + "' is not of type " + type.getName());
    }


    /**
     * Gets int.
     *
     * @param key the key
     * @return the int
     */
    public int getInt(String key) {
        Object value = this.elements.get(key);
        if (value instanceof Integer) {
            return (int) value;
        }
        throw new ClassCastException("Value for key '" + key + "' is not of type int.");
    }

    /**
     * Gets long.
     *
     * @param key the key
     * @return the long
     */
    public long getLong(String key) {
        Object value = this.elements.get(key);
        if (value instanceof Long) {
            return (long) value;
        }
        throw new ClassCastException("Value for key '" + key + "' is not of type long.");
    }

    /**
     * Gets double.
     *
     * @param key the key
     * @return the double
     */
    public double getDouble(String key) {
        Object value = this.elements.get(key);
        if (value instanceof Double) {
            return (double) value;
        }
        throw new ClassCastException("Value for key '" + key + "' is not of type double.");
    }

    /**
     * Gets float.
     *
     * @param key the key
     * @return the float
     */
    public float getFloat(String key) {
        Object value = this.elements.get(key);
        if (value instanceof Float) {
            return (float) value;
        }
        throw new ClassCastException("Value for key '" + key + "' is not of type float.");
    }

    /**
     * Gets boolean.
     *
     * @param key the key
     * @return the boolean
     */
    public boolean getBoolean(String key) {
        Object value = this.elements.get(key);
        if (value instanceof Boolean) {
            return (boolean) value;
        }
        throw new ClassCastException("Value for key '" + key + "' is not of type boolean.");
    }

    /**
     * Gets char.
     *
     * @param key the key
     * @return the char
     */
    public char getChar(String key) {
        Object value = this.elements.get(key);
        if (value instanceof Character) {
            return (char) value;
        }
        throw new ClassCastException("Value for key '" + key + "' is not of type char.");
    }

    /**
     * Gets byte.
     *
     * @param key the key
     * @return the byte
     */
    public byte getByte(String key) {
        Object value = this.elements.get(key);
        if (value instanceof Byte) {
            return (byte) value;
        }
        throw new ClassCastException("Value for key '" + key + "' is not of type byte.");
    }

    /**
     * Gets short.
     *
     * @param key the key
     * @return the short
     */
    public short getShort(String key) {
        Object value = this.elements.get(key);
        if (value instanceof Short) {
            return (short) value;
        }
        throw new ClassCastException("Value for key '" + key + "' is not of type short.");
    }

    /**
     * Gets string.
     *
     * @param key the key
     * @return the string
     */
    public String getString(String key) {
        Object value = this.elements.get(key);
        if (value instanceof String) {
            return (String) value;
        }
        throw new ClassCastException("Value for key '" + key + "' is not of type short.");
    }

    /**
     * Contains boolean.
     *
     * @param key the key
     * @return the boolean
     */
    public boolean contains(String key) {
        return this.elements.containsKey(key);
    }

    /**
     * Remove.
     *
     * @param key the key
     */
    public void remove(String key) {
        this.elements.remove(key);
    }

    @Override
    public int size() {
        return elements.size();
    }

    public Set<String> keySet() {
        return elements.keySet();
    }

    /**
     * Gets elements.
     *
     * @return the elements
     */
    public Map<String, Object> getElements() {
        return this.elements;
    }

    @Override
    public String toString() {
        return "Piece{" +
                "elements=" + elements +
                ", index=" + index +
                ", id=" + id +
                '}';
    }
}
