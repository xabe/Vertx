package com.xabe.vertx.data;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Value;

@Value
@AllArgsConstructor
@Builder(toBuilder = true)
public class Article {

  private String id;

  private String content;

  private String author;

  private String datePublished;

  private int wordCount;
}
