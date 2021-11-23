package com.xabe.vertx.data;

import io.vertx.core.buffer.Buffer;
import io.vertx.core.eventbus.MessageCodec;
import io.vertx.core.json.JsonObject;

public class ArticleMessageCodec implements MessageCodec<Article, Article> {

  public static final String ID = "id";

  public static final String CONTENT = "content";

  public static final String AUTHOR = "author";

  public static final String DATE_PUBLISHED = "datePublished";

  public static final String WORD_COUNT = "wordCount";

  @Override
  public void encodeToWire(final Buffer buffer, final Article article) {
    // Easiest ways is using JSON object
    final JsonObject jsonToEncode = new JsonObject();
    jsonToEncode.put(ID, article.getId());
    jsonToEncode.put(CONTENT, article.getContent());
    jsonToEncode.put(AUTHOR, article.getAuthor());
    jsonToEncode.put(DATE_PUBLISHED, article.getDatePublished());
    jsonToEncode.put(WORD_COUNT, article.getWordCount());

    // Encode object to string
    final String jsonToStr = jsonToEncode.encode();

    // Length of JSON: is NOT characters count
    final int length = jsonToStr.getBytes().length;

    // Write data into given buffer
    buffer.appendInt(length);
    buffer.appendString(jsonToStr);
  }

  @Override
  public Article decodeFromWire(final int position, final Buffer buffer) {
    // My custom message starting from this *position* of buffer
    int _pos = position;

    // Length of JSON
    final int length = buffer.getInt(_pos);

    // Get JSON string by it`s length
    // Jump 4 because getInt() == 4 bytes
    final String jsonStr = buffer.getString(_pos += 4, _pos += length);
    final JsonObject contentJson = new JsonObject(jsonStr);

    // Get fields
    final String id = contentJson.getString(ID);
    final String content = contentJson.getString(CONTENT);
    final String author = contentJson.getString(AUTHOR);
    final String datePublished = contentJson.getString(DATE_PUBLISHED);
    final int wordCount = contentJson.getInteger(WORD_COUNT);

    // We can finally create custom message object
    return new Article(id, content, author, datePublished, wordCount);
  }

  @Override
  public Article transform(final Article article) {
    // If a message is sent *locally* across the event bus.
    // This example sends message just as is
    return article;
  }

  @Override
  public String name() {
    return this.getClass().getSimpleName();
  }

  @Override
  public byte systemCodecID() {
    return -1;
  }
}
