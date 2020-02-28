package Part2;

import java.util.ArrayList;
import java.util.Scanner;

public class CMD
{
    /**CMD Inserted Character*/
    private String Command;

    /**CMD Default Constructor*/
    public CMD()
    {
    }

    /** Create a Scanner object to read the the Command Line*/
    protected void ExecuteCommand()
    {
        Scanner CMDReader = new Scanner(System.in);
        System.out.println("CMD> ");
        String mCommand = CMDReader.nextLine();
        
    }

    /**Check whether the inserted character is valid*/
    protected boolean ValidateCommand(String mCommand)
    {
        ArrayList<String> ValidCommands = new ArrayList<>();
        ValidCommands.add("^");
        ValidCommands.add("$");
        ValidCommands.add("-");
        ValidCommands.add("+");
        ValidCommands.add("a");
        ValidCommands.add("t");
        ValidCommands.add("d");
        ValidCommands.add("l");
        ValidCommands.add("n");
        ValidCommands.add("p");
        ValidCommands.add("q");
        ValidCommands.add("w");
        ValidCommands.add("x");
        ValidCommands.add("=");
        ValidCommands.add("#");
        ValidCommands.add("c");
        ValidCommands.add("v");
        ValidCommands.add("s");
        ValidCommands.add("b");
        return ValidCommands.contains(mCommand);
    }

    /**CMD Inserted String getter*/
    public String getCommand()
    {
        return Command;
    }

    /**CMD Inserted String setter*/
    public void setCommand(String command)
    {
        Command = command;
    }
}
