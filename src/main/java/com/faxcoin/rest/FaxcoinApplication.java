package com.faxcoin.rest;

import com.faxcoin.communication.Address;
import com.faxcoin.print.LinuxPrinter;
import com.faxcoin.print.TerminalPrinter;
import com.faxcoin.server.Node;
import com.faxcoin.server.PrintService;
import com.faxcoin.server.SimpleServerNode;
import org.apache.commons.cli.*;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class FaxcoinApplication {
  private static String name;
  private static String printerName;
  private static boolean useTerminal;

  public static void main(String[] args) {
    Options options = new Options();

    Option nameArg = new Option("n", "name", true, "Your name");
    nameArg.setRequired(true);
    options.addOption(nameArg);

    Option printerNameArg = new Option("p", "printer-name", true, "Your printers name");
    printerNameArg.setRequired(true);
    options.addOption(printerNameArg);

    Option useTerminalPrintArg = new Option("t", "terminal", false, "If set, prints to terminal");
    useTerminalPrintArg.setRequired(false);
    options.addOption(useTerminalPrintArg);



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

    name = cmd.getOptionValue("name");
    printerName = cmd.getOptionValue("printer-name");
    useTerminal = cmd.hasOption("terminal");
    SpringApplication.run(FaxcoinApplication.class, args);
  }

  @Bean
  public Node getNode() {
    PrintService printer = null;
    if (useTerminal) {
      printer = new TerminalPrinter();
    } else {
      printer = new LinuxPrinter(printerName);
    }
    return new SimpleServerNode(new Address(name), printer);
  }
}
