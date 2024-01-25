import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Scanner;

public class Gulie {
    private final static String name = "Güliedistodiez";
    private final static String line = "____________________________________________________________";
    private ArrayList<Task> list;

    public Gulie() {
        System.out.println(line);
        this.greet();
        System.out.println(line);
        this.list = new ArrayList<>();
    }

    public boolean input(String inp) {
        System.out.println(line);
        try {
            switch (inp.split(" ")[0]) {
                case "bye": {
                    this.exit();
                    System.out.println(line);
                    return false;
                } case "list": {
                    this.list();
                    break;
                } case "mark": {
                    this.mark(Gulie.getArgument(inp, "mark", "index"));
                    break;
                } case "unmark": {
                    this.unmark(Gulie.getArgument(inp, "unmark", "index"));
                    break;
                } case "todo": {
                    this.store(new ToDo(Gulie.getArgument(inp, "todo", "name")));
                    break;
                } case "deadline": {
                    String name = Gulie.getArgument(inp, "deadline", "name");
                    String by = Gulie.getArgument(inp, "/by");
                    this.store(new Deadline(name, by));
                    break;
                } case "event": {
                    String name = Gulie.getArgument(inp, "event", "name");
                    String from = Gulie.getArgument(inp, "/from");
                    String to = Gulie.getArgument(inp, "/to");
                    this.store(new Event(name, from, to));
                    break;
                } default:
                    throw new GulieException("Apologies. I do not understand.");
            }
        } catch (GulieException e){
            System.out.println(e.getMessage());
        }
        System.out.println(line);
        return true;
    }

    private static String getArgument(String inp, String arg, String argname) throws GulieException {
        arg = " " + arg + " ";
        inp = " " + inp;
        int i = inp.indexOf(arg);
        if (i == -1)
            throw new GulieException("You must provide the argument '" + argname + "'");
        String str = inp.substring(i + arg.length());
        if (str.indexOf(" /") == -1)
            return str;
        else
            return str.substring(0, str.indexOf(" /"));
    }

    private static String getArgument(String inp, String arg) throws GulieException {
        return Gulie.getArgument(inp, arg, arg.substring(1));
    }

    private void greet() {
        System.out.println(String.format(" Greetings. I am %s.\n What can I do for you?", name));
    }

    private void exit() {
        System.out.println(" Goodbye.");
    }

    private void list() {
        for (int i = 0; i < list.size(); i++) {
            System.out.println(String.format(" %d. %s", i + 1, list.get(i)));
        }
    }

    private void store(Task task) {
        System.out.println(" Understood. I have added this task:\n   " + task);
        this.list.add(task);
        System.out.println(String.format("You now have %d tasks in the list", list.size()));
    }

    private void mark(String str) throws GulieException {
        try {
            int i = Integer.parseInt(str);
            if (i > list.size() || i < 0)
                throw new GulieException("Invalid index: " + i);
            Task task = list.get(i - 1);
            task.setMark(true);
            System.out.println(" I have marked this task as completed:\n   " + task);
        } catch (NumberFormatException e) {
            throw new GulieException("Argument must in integer: " + str);
        }
    }

    private void unmark(String str) throws GulieException {
        try {
            int i = Integer.parseInt(str);
            if (i > list.size() || i < 0)
                throw new GulieException("Invalid index: " + i);
            Task task = list.get(i - 1);
            task.setMark(false);
            System.out.println(" I have marked this task as incomplete:\n   " + task);
        } catch (NumberFormatException e) {
            throw new GulieException("Argument must in integer: " + str);
        }
    }

    public static void main(String[] args) {
        Gulie gulie = new Gulie();
        Scanner scanner = new Scanner(System.in);
        while (gulie.input(scanner.nextLine())) {

        }
    }
}
