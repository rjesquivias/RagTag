package com.rjesquivias.todoist;


import java.util.Collection;

interface ICommentDao {

    Collection<Comment> getAllInProject(long projectId);

    Collection<Comment> getAllInTask(long taskId);

    Comment create(Arguments.CreateCommentArgs args);

    Comment get(long commentId);

    void update(long commentId, String content);

    void delete(long commentId);
}
