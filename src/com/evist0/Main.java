package com.evist0;

import com.evist0.application.AppController;
import com.evist0.application.AppModel;
import com.evist0.application.AppView;

public class Main {
    public static void main(String[] args) {
        var model = new AppModel();

        var controller = new AppController(model);
        var view = new AppView(controller, model);
    }
}
