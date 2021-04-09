package com.faxcoin.print;

import com.faxcoin.communication.Message;
import com.faxcoin.server.PrintService;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class LinuxPrinter implements PrintService {
    private final String printerName;

    public LinuxPrinter(String printerName) {
        this.printerName = printerName;
    }

    public void printMessage(Message msg) {
        String commandTemplate = "sh -c 'echo \"%s\" | lp -d %s'";
        Runtime run = Runtime.getRuntime();
        String printCmd = String.format(commandTemplate, msg.toString(), this.printerName);
        String[] cmd = {"/bin/sh", "-c", printCmd};
        try {
            Process pr = run.exec(cmd);
            pr.waitFor();
            BufferedReader buf = new BufferedReader(new InputStreamReader(pr.getInputStream()));
            String line = "";
            while ((line = buf.readLine()) != null) {
                System.out.println(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
