package com.rjesquivias.todoist;

import java.util.Collection;

interface ISectionDao {

    Collection<Section> getAll();

    Collection<Section> getAll(long project_id);

    Section create(Arguments.CreateSectionArgs args);

    Section get(long id);

    void update(long id, String name);

    void delete(long id);
}
