package cli;

class Command {
    private final String cliPrompt;
    private final Runnable handler;

    Command(String cliPrompt, Runnable handler) {
        this.cliPrompt = cliPrompt;
        this.handler = handler;
    }

    public String getCliPrompt() {
        return cliPrompt;
    }

    public void execute() {
        handler.run();
    }
}