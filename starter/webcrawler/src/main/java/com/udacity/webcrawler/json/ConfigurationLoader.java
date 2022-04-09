package com.udacity.webcrawler.json;

import java.io.Reader;
import java.nio.file.Path;
import java.util.Objects;

// 09.04
import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.ObjectMapper;


/**
 * A static utility class that loads a JSON configuration file.
 */
public final class ConfigurationLoader {

  private final Path path;

  /**
   * Create a {@link ConfigurationLoader} that loads configuration from the given {@link Path}.
   */
  public ConfigurationLoader(Path path) {
    this.path = Objects.requireNonNull(path);
  }

  /**
   * Loads configuration from this {@link ConfigurationLoader}'s path
   *
   * @return the loaded {@link CrawlerConfiguration}.
   *  Your load() method will read the JSON string from a file Path which has already been provided to the
   *    * ConfigurationLoader constructor. Pass that string to the read(Reader reader) and return the created
   *    * CrawlerConfiguration. Remember to close the file when you are done!
   */

  public CrawlerConfiguration load()  {
    // Ask yourself waht does the method return and initialize it if necessary.
    //  07.04 Yannick Bihege This is not sufficiently clearly defined. This means reading a file from a path.

    try (Reader reader = Files.newBufferedReader(path)) {
      return read(reader);
    } catch (Exception e) {
      e.getLocalizedMessage();
      return null;
    }
    // This I find confusing
    // return new CrawlerConfiguration.Builder().build();
  }

  /**
   * Loads crawler configuration from the given reader.
   *
   * @param reader a Reader pointing to a JSON string that contains crawler configuration.
   * @return a crawler configuration
   *
   * Y.Bihege 09.04
   * The reader parameter contains JSON input. Your read(Reader reader) method should read the JSON
   * input and parse it into a CrawlerConfiguration using the Jackson JSON library.
   *
   * implement CrawlerConfiguration#read(Reader) by creating a new
   * com.fasterxml.jackson.databind.ObjectMapper and calling ObjectMapper#readValue.
   */
  public static CrawlerConfiguration read(Reader reader) throws IOException {
    // This is here to get rid of the unused variable warning.
    Objects.requireNonNull(reader);
    // 09.04: Fill in this method
    ObjectMapper objectMapper = new ObjectMapper();
    // If you get a "Stream closed" failure in the test, try calling ObjectMapper#disable(Feature) to
    // disable the com.fasterxml.jackson.core.JsonParser.Feature.AUTO_CLOSE_SOURCE.
    objectMapper.disable(JsonParser.Feature.AUTO_CLOSE_SOURCE);
    return objectMapper.readValue(reader , CrawlerConfiguration.Builder.class).build();

    //return new CrawlerConfiguration.Builder().build();
  }
}
