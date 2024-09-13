package test;

import net.cakemc.database.file.AbstractDatabaseFile;
import net.cakemc.database.file.AbstractDatabaseFolder;
import net.cakemc.database.file.NioFolder;

import java.nio.charset.StandardCharsets;
import java.nio.file.Paths;

public class NioFileTest {

    public static void main(String[] args) throws Throwable {
        AbstractDatabaseFolder folder = new NioFolder(Paths.get("./database_test/"));
        folder.create();

        {
            AbstractDatabaseFile file = folder.getFile("test.db.bin");
            file.getMemoryFile().setData("Hello, World!".getBytes(StandardCharsets.UTF_8));

            System.out.println("writing: %s".formatted(new String(file.getMemoryFile().getData())));
            file.write();
        }

        {
            AbstractDatabaseFile file = folder.getFile("test.db.bin");
            file.read();
            System.out.println(new String(file.getMemoryFile().getData()));
        }
    }

}
