package Part2;

import java.util.ArrayList;

public class CMD
{
    /**CMD Inserted Character*/
    private String Command;

    /**CMD Default Constructor*/
    public CMD() {
    }

    /**CMD Default Constructor*/
    protected void ExecuteCommand()
    {
        if(this.ValidateCommand())
        {
            if(this.Command.equals("^"))
            {
                System.out.println("Pointer to first line.");
            }
        }
        else
        {
            System.out.println("Invalid Command.");
        }
    }

    /**Check whether the inserted character is valid*/
    protected boolean ValidateCommand()
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
        return ValidCommands.contains(this.Command);
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
