package com.rjesquivias.todoist;

import java.util.Collection;

interface ITaskDao {

    Collection<Task> getAllActive(Arguments.GetAllActiveTasksArgs args);

    Task create(Arguments.CreateTaskArgs args);

    Task getActive(long id);

    void update(long id, Arguments.UpdateTaskArgs args);

    void close(long id);

    void reOpen(long id);

    void delete(long id);
}
