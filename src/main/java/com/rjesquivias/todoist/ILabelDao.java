package com.rjesquivias.todoist;

import java.util.Collection;

interface ILabelDao {

    Collection<Label> getAll();

    Label create(Arguments.CreateLabelArgs args);

    Label get(long id);

    void update(long id, Arguments.UpdateLabelArgs args);

    void delete(long id);
}
