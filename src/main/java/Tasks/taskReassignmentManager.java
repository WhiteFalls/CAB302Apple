package Tasks;
import java.time.LocalDate;
import java.util.HashMap;

public class taskReassignmentManager {

        private static taskReassignmentManager instance;
        private HashMap<Task, LocalDate> reassignedTasks;

        // Private constructor to prevent instantiation from outside
        private taskReassignmentManager() {
            reassignedTasks = new HashMap<>();
        }
        public static taskReassignmentManager getInstance() {
            if (instance == null) {
                instance = new taskReassignmentManager();
            }
            return instance;
        }


        public HashMap<Task, LocalDate> getReassignedTasks() {
            return reassignedTasks;
        }

        public void addReassignedTask(Task task, LocalDate date) {
            reassignedTasks.put(task, date);
        }


        public void removeReassignedTask(Task task) {
            reassignedTasks.remove(task);
        }

        public void clearReassignedTasks() {
            reassignedTasks.clear();
        }
}
