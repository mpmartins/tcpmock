package com.inovarie.tcpmock;

import com.inovarie.tcpmock.app.gui.TCPMockController;
import com.inovarie.tcpmock.app.gui.TCPMockView;
import com.inovarie.tcpmock.app.gui.model.TCPMockModel;

import javax.inject.Inject;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * App class for this application.
 */
@SpringBootApplication
public class App implements CommandLineRunner {

    private final TCPMockController tcpMockController;
    private final TCPMockModel tcpMockModel;
    private final TCPMockView tcpMockView;

    @Inject
    public App(TCPMockController tcpMockController,
               TCPMockModel tcpMockModel,
               TCPMockView tcpMockView) {
        this.tcpMockController = tcpMockController;
        this.tcpMockModel = tcpMockModel;
        this.tcpMockView = tcpMockView;
    }

    public static void main(String[] args) {
        SpringApplication app = new SpringApplication(App.class);

        app.setHeadless(false);
        app.run(args);
    }

    @Override
    public void run(String... args) {
        tcpMockController.init(tcpMockModel, tcpMockView);
    }

}
