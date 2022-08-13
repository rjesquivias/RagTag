package com.rjesquivias.todoist;

import java.util.Collection;

interface IProjectDao {

    Collection<Project> getAll();

    Project get(long id);

    Project create(Arguments.CreateProjectArgs args);

    void update(long id, Arguments.UpdateProjectArgs args);

    void delete(long id);
}
