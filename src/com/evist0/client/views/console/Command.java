package com.evist0.client.views.console;

import com.evist0.client.models.AppModel;

public interface Command {
    String execute(AppModel appModel);
}
