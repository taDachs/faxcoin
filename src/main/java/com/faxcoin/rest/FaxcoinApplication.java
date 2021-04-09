package com.faxcoin.rest;

import com.faxcoin.communication.Address;
import com.faxcoin.print.LinuxPrinter;
import com.faxcoin.print.TerminalPrinter;
import com.faxcoin.server.Node;
import com.faxcoin.server.PrintService;
import com.faxcoin.server.SimpleServerNode;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class FaxcoinApplication {
	private static String name;
	private static String printerName;

	public static void main(String[] args) {
	    name = args[0];
	    printerName = args[1];
		SpringApplication.run(FaxcoinApplication.class, args);
	}

	@Bean
	public Node getNode() {
	    PrintService printer = new LinuxPrinter(printerName);
		//PrintService printer = new TerminalPrinter();
		return new SimpleServerNode(new Address(name), printer);
	}
}
