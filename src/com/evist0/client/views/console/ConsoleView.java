package com.evist0.client.views.console;

import com.evist0.client.Client;
import com.evist0.client.models.AppModel;
import com.evist0.client.views.console.parser.Input;
import com.evist0.client.views.console.parser.Parser;
import com.evist0.client.views.console.parser.ParserFactory;
import com.evist0.client.views.console.parser.Result;
import com.evist0.common.entities.CompanyTaxpayer;
import com.evist0.common.entities.IndividualTaxpayer;

import javax.swing.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.net.InetAddress;

public class ConsoleView extends JFrame {
    private final AppModel appModel;

    private JPanel content;
    private JTextArea outputArea;
    private JTextField inputField;

    private static final ParserFactory<String, Command> getAmountParserFactory = value -> Parser.then(Parser.match("amount"), Parser.value(model -> {
        switch (value) {
            case "individual":
                return String.valueOf(model.getTaxpayers().stream().filter(entity -> entity instanceof IndividualTaxpayer).count());
            case "company":
                return String.valueOf(model.getTaxpayers().stream().filter(entity -> entity instanceof CompanyTaxpayer).count());
        }

        assert false;
        return "";
    }));
    private static final Parser<Command> getParser = Parser.pipe(Parser.either(Parser.match("individual"), Parser.match("company")), value -> Parser.then(Parser.match(' '), getAmountParserFactory.create(value)));
    private static final Parser<Command> getCommandParser = Parser.then(Parser.then(Parser.then(Parser.match("get"), Parser.match(' ')), Parser.ignore(Character::isSpaceChar)), getParser);

    private static final Parser<String> ipParser = input -> {
        StringBuilder sb = new StringBuilder();
        Input remainder = input;

        while (remainder.getCharacter().hasValue() && remainder.getCharacter().getValue() != ' ') {
            sb.append(remainder.getCharacter().getValue());

            remainder = remainder.skip(1);
        }

        return Result.fromValue(sb.toString(), remainder);
    };
    private static final Parser<Command> connectParser = Parser.then(Parser.then(Parser.then(Parser.match("connect"), Parser.match(' ')), Parser.ignore(Character::isSpaceChar)), Parser.pipe(ipParser, value -> Parser.value(model -> {
        try {
            Client.connect(model, InetAddress.getByName(value), 1337);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    })));

    private static final Parser<Command> commandParser = Parser.either(getCommandParser, connectParser);

    public ConsoleView(AppModel appModel) {
        super("Взлом жопы");

        this.appModel = appModel;

        setBounds(0, 0, 300, 500);

        add(content);

        setDefaultCloseOperation(HIDE_ON_CLOSE);

        __initActionListeners();
    }

    private void __initActionListeners() {
        inputField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    Result<Command> result = commandParser.parse(new Input(inputField.getText()));

                    if (result.hasValue()) {
                        Command command = result.getValue();
                        outputArea.append(command.execute(appModel) + '\n');
                    } else {
                        outputArea.append("unknown command '" + inputField.getText() + "'\n");
                    }

                    inputField.setText("");
                }
            }
        });
    }
}
