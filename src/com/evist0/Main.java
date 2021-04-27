package com.evist0;

import com.evist0.client.AppController;
import com.evist0.client.config.Config;
import com.evist0.client.models.AppModel;
import com.evist0.client.views.app.AppView;

public class Main {
    public static void main(String[] args) {
        var config = new Config("config.cfg");

        var model = new AppModel(config);
        var controller = new AppController(model);

        var view = new AppView(controller, model);

        view.setVisible(true);
    }
}
