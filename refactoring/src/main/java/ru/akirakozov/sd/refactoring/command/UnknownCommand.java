package ru.akirakozov.sd.refactoring.command;

 public class UnknownCommand extends ProductCommand {

     private final String command;

     public UnknownCommand(String command) {
         this.command = command;
     }

     @Override
     public String toHtml() {
         return "Unknown command: " + command;
     }
 }