package algorithm;

public abstract class AbstractAlgorithm {
    protected Object result = null;

    public void run() {
        this.exec();
    }

    public Object getResult() {
        return this.result;
    }

    public abstract void exec();

    public abstract String getDescription();
}

