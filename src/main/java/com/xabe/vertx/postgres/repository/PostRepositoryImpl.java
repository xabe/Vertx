package com.xabe.vertx.postgres.repository;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import com.xabe.vertx.postgres.repository.data.Post;
import io.vertx.core.AsyncResult;
import io.vertx.core.Handler;
import io.vertx.pgclient.PgPool;
import io.vertx.sqlclient.Row;
import io.vertx.sqlclient.RowSet;
import io.vertx.sqlclient.SqlResult;
import io.vertx.sqlclient.Tuple;

public class PostRepositoryImpl implements PostRepository {

  public static final String SELECT_ALL = "SELECT * FROM posts ORDER BY id ASC";

  public static final String SELECT_ID = "SELECT * FROM posts WHERE id=$1";

  public static final String INSERT_POSTS = "INSERT INTO posts(title, content) VALUES ($1, $2) RETURNING (id)";

  public static final String UPDATE_POSTS = "UPDATE posts SET title=$1, content=$2 WHERE id=$3";

  public static final String DELETE_ALL = "DELETE FROM posts";

  public static final String DELETE_POSTS = "DELETE FROM posts WHERE id=$1";

  private static final Function<Row, Post> MAPPER = (row) ->
      Post.of(
          row.getUUID("id"),
          row.getString("title"),
          row.getString("content"),
          row.getLocalDateTime("created_at")
      );

  private final PgPool pgPool;

  public PostRepositoryImpl(final PgPool pgPool) {
    this.pgPool = pgPool;
  }

  @Override
  public PostRepository findAll(final Handler<AsyncResult<List<Post>>> resultHandler) {
    this.pgPool.query(SELECT_ALL)
        .execute()
        .map(rs -> StreamSupport.stream(rs.spliterator(), false)
            .map(MAPPER)
            .collect(Collectors.toList())
        ).onComplete(resultHandler);
    return this;
  }

  @Override
  public PostRepository findById(final String id, final Handler<AsyncResult<Post>> resultHandler) {
    this.pgPool.preparedQuery(SELECT_ID)
        .execute(Tuple.of(id))
        .map(RowSet::iterator)
        .map(iterator -> {
              if (iterator.hasNext()) {
                return MAPPER.apply(iterator.next());
              }
              throw new RuntimeException(id);
            }
        ).onComplete(resultHandler);
    return this;
  }

  @Override
  public PostRepository save(final Post data, final Handler<AsyncResult<String>> resultHandler) {
    this.pgPool.preparedQuery(INSERT_POSTS)
        .execute(Tuple.of(data.getTitle(), data.getContent()))
        .map(rs -> rs.iterator().next().getUUID("id").toString())
        .onComplete(resultHandler);
    return this;
  }

  @Override
  public PostRepository update(final Post data, final Handler<AsyncResult<Integer>> resultHandler) {
    this.pgPool.preparedQuery(UPDATE_POSTS)
        .execute(Tuple.of(data.getTitle(), data.getContent(), data.getId()))
        .map(SqlResult::rowCount).onComplete(resultHandler);
    return this;
  }

  @Override
  public PostRepository deleteAll(final Handler<AsyncResult<Integer>> resultHandler) {
    this.pgPool.query(DELETE_ALL).execute()
        .map(SqlResult::rowCount).onComplete(resultHandler);
    return this;
  }

  @Override
  public PostRepository deleteById(final String id, final Handler<AsyncResult<Integer>> resultHandler) {
    this.pgPool.preparedQuery(PostRepositoryImpl.DELETE_POSTS).execute(Tuple.of(id))
        .map(SqlResult::rowCount).onComplete(resultHandler);
    return this;
  }
}
