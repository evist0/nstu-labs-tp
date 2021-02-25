package com.evist0;

import com.evist0.controllers.Controller;
import com.evist0.models.Model;
import com.evist0.views.View;

public class Main {
    public static void main(String[] args) {
        var view = new View();
        var model = new Model();

        var controller = new Controller(model, view);
    }
}
