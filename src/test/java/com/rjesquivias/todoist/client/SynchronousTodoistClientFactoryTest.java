package com.rjesquivias.todoist.client;

import static org.junit.Assert.assertNotNull;

import org.junit.Test;

public class SynchronousTodoistClientFactoryTest {

  @Test
  public void buildClient() {
    SynchronousTodoistClient client = SynchronousTodoistClientFactory.buildClient();
    assertNotNull(client);
  }
}
