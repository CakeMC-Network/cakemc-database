package test;

import net.cakemc.database.AbstractDatabase;
import net.cakemc.database.DefaultDatabase;
import net.cakemc.database.api.Piece;
import net.cakemc.database.collection.Collection;
import net.cakemc.database.filter.Filters;

import java.nio.file.Paths;

public class DatabaseTest {

    public static void main(String[] args) {
        AbstractDatabase database = new DefaultDatabase(Paths.get("./database_test/"));
        database.load();

        System.out.println(database.getCollection("players").collect());
    }

    public static void test1() {
        AbstractDatabase database = new DefaultDatabase(Paths.get("./database_test/"));
        database.load();

        Collection<?> collection = database.getCollection("players");

        collection.singlePieceAsync(Filters.eq("name", "u64Lisa"), (piece, state, exception) -> {
            switch (state) {
                case FOUND -> {
                    System.out.println("found document: %s".formatted(piece));
                }
                case NOT_FOUND -> {
                    Piece newPiece = collection.defineOnePeace();
                    newPiece.add("name", "u64Lisa");
                    newPiece.add("coins", 1);
                    newPiece.add("setting", true);

                    collection.insertOnePieceAsync(newPiece, (state1, exception1) -> {
                        switch (state1) {
                            case FAILED -> exception1.printStackTrace();
                            case SUCCESS -> {
                                System.out.println("created new player document!");
                                database.save();
                            }
                        }
                    });
                }
                case ERROR -> exception.printStackTrace();
            }
        });
    }

}
