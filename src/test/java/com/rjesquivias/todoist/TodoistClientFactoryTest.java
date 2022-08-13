package com.rjesquivias.todoist;

import static org.junit.Assert.assertNotNull;

import org.junit.Test;
import static com.rjesquivias.todoist.TestConstants.*;
public class TodoistClientFactoryTest {

  @Test
  public void buildClient() {
    TodoistClient client = TodoistClient.buildClient(testToken);
    assertNotNull(client);
  }
}
