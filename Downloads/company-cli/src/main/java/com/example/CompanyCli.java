
package com.example;

import picocli.CommandLine;
import picocli.CommandLine.Command;
import picocli.CommandLine.Option;

// simple CLI that prints the name of your company

@Command(name = "company-cli", mixinStandardHelpOptions = true, version = "company-cli 1.0",
         description = "Prints the name of your company.")
public class CompanyCli implements Runnable {

    @Option(names = {"-c", "--company"}, description = "Company name to print", defaultValue = "Your Company")
    private String company;

    @Option(names = {"-m", "--message"}, description = "Optional message to show before the company name")
    private String message;

    public static void main(String[] args) {
        int exitCode = new CommandLine(new CompanyCli()).execute(args);
        System.exit(exitCode);
    }

    @Override
    public void run() {
        if (message != null && !message.isEmpty()) {
            System.out.println(message);
        }
        System.out.println("==> " + company + " <==");
    }
}
