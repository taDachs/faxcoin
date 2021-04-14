package com.faxcoin.rest;

import com.faxcoin.communication.messenger.*;
import java.util.Collections;

import com.faxcoin.server.node.Node;
import com.faxcoin.server.node.SimpleServerNode;
import com.faxcoin.server.node.UrlNodeAddress;
import org.apache.commons.cli.*;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;


@SpringBootApplication
public class FaxcoinApplication {
  private static String nodeAddress;
  private static String port;

  public static void main(String[] args) {
    Options options = new Options();

    Option nodeAddressArg = new Option("a", "address", true, "Address for this node");
    nodeAddressArg.setRequired(true);
    options.addOption(nodeAddressArg);

    Option portArg = new Option("p", "port", true, "Port for the app to run on");
    portArg.setRequired(false);
    options.addOption(portArg);


    CommandLineParser parser = new DefaultParser();
    HelpFormatter formatter = new HelpFormatter();
    CommandLine cmd = null;

    try {
      cmd = parser.parse(options, args);
    } catch (org.apache.commons.cli.ParseException e) {
      System.out.println(e.getMessage());
      formatter.printHelp("utility-name", options);

      System.exit(1);
    }

    nodeAddress = cmd.getOptionValue("address");
    port = cmd.getOptionValue("port", "8080");

    SpringApplication app = new SpringApplication(FaxcoinApplication.class);
    app.setDefaultProperties(Collections.singletonMap("server.port", port));
    app.run(args);
  }

  @Bean
  public Node getNode() {
    Node node = new SimpleServerNode(new UrlNodeAddress(nodeAddress), new PersistentMessengerFactory());

    return node;
  }
}
