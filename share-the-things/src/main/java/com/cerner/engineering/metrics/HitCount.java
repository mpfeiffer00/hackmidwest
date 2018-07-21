package com.cerner.engineering.metrics;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.FileLock;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

/**
 * @author Aaron McGinn (am025347)
 */
public class HitCount
{
    final private static String COUNTER_FILE_NAME = "/counter.txt";
    private static String actualCounterLocation;

    /**
     * Ensure the file that tracks page hits exists.
     */
    public static void ensureCountExists()
    {
        final Path tempDir = Paths.get(System.getProperty("java.io.tmpdir"));
        actualCounterLocation = tempDir + COUNTER_FILE_NAME;
        System.out.println(actualCounterLocation);

        try (FileChannel channel = FileChannel.open(Paths.get(actualCounterLocation), StandardOpenOption.CREATE, StandardOpenOption.WRITE))
        {
            if (channel.size() == 0)
            {
                channel.write(ByteBuffer.wrap("0\n".getBytes()));
            }
        }
        catch (final IOException e)
        {
            System.out.println("Error checking if counter file exists.");
            e.printStackTrace();
        }
    }

    /**
     * Get the hit count of the application. Updates the file tracking the hit count.
     * @return The number of hits that the service has received.
     */
    public static long getCount()
    {
        long count = 0;
        try (FileChannel channel = FileChannel.open(Paths.get(actualCounterLocation), StandardOpenOption.READ, StandardOpenOption.WRITE);)
        {
            try (FileLock fileLock = channel.lock())
            {
                final ByteBuffer buffer = ByteBuffer.allocate(20);
                int bytesRead = channel.read(buffer);

                while (bytesRead != -1)
                {
                    buffer.flip();

                    if (buffer.hasArray())
                    {
                        final byte[] array = buffer.array();
                        final String fileContents = new String(array);

                        try
                        {
                            count = Long.valueOf(fileContents.replaceAll("[^0-9]", ""));
                        }
                        catch (@SuppressWarnings("unused") final NumberFormatException e)
                        {
                            System.out.println("Current count " + fileContents + " could not be read, so resetting to 0.");
                            count = 0;
                            break;
                        }
                    }

                    buffer.clear();
                    bytesRead = channel.read(buffer);
                }
            }

        }
        catch (final Exception e)
        {
            System.out.println("Error reading hit count file.");
            e.printStackTrace();
        }

        count += 1;

        try (FileChannel channel = FileChannel.open(Paths.get(actualCounterLocation), StandardOpenOption.TRUNCATE_EXISTING,
                StandardOpenOption.WRITE);)
        {
            try (FileLock fileLock = channel.lock())
            {
                final ByteBuffer buffer = ByteBuffer.wrap(Long.toString(count).getBytes());
                channel.write(buffer);
            }
        }
        catch (final Exception e)
        {
            System.out.println("Error writing to hit count file.");
            e.printStackTrace();
        }

        return count;
    }
}
