public interface ITask {

    boolean isCompleted();

    void assignTo(IPerson person);

    String getTaskName();
}
