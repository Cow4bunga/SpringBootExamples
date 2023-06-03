package by.levitsky.spring.managementsystem.repository;

import by.levitsky.spring.managementsystem.entity.Task;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;


@DataJpaTest
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
public class TaskRepositoryTests {
    @Autowired
    private TaskRepository taskRepository;

    @Test
    public void TaskRepository_SaveAll_ReturnsSaveTask() {
        Task task = Task
                .builder()
                .title("workspace")
                .dueDate("12/10/2023")
                .description("create workspace").build();

        Task savedTask = taskRepository.save(task);

        assertThat(savedTask).isNotNull();
        assertThat(savedTask.getId()).isGreaterThan(0);
    }

    @Test
    public void TaskRepository_GetAll_ReturnMoreThanOne() {
        Task taskOne = Task
                .builder()
                .title("workspace")
                .dueDate("12/10/2023")
                .description("create workspace").build();

        Task taskTwo = Task
                .builder()
                .title("tests")
                .dueDate("5/10/2023")
                .description("add tests").build();

        taskRepository.save(taskOne);
        taskRepository.save(taskTwo);

        List<Task> taskList = taskRepository.findAll();

        assertThat(taskList).isNotNull();
        assertThat(taskList.size()).isEqualTo(2);
    }

    @Test
    public void TaskRepository_FindById_ReturnTask() {
        Task task = Task
                .builder()
                .title("workspace")
                .dueDate("12/10/2023")
                .description("create workspace").build();

        taskRepository.save(task);
        Task returnedTask = taskRepository.findById(task.getId()).get();

        assertThat(returnedTask).isNotNull();
    }

    @Test
    public void TaskRepository_UpdateTask_ReturnTaskNotNull() {
        Task task = Task
                .builder()
                .title("workspace")
                .dueDate("12/10/2023")
                .description("create workspace").build();

        taskRepository.save(task);
        Task savedTask = taskRepository.findById(task.getId()).get();
        savedTask.setDueDate("16/10/2023");
        savedTask.setDescription("add members");

        Task updTask = taskRepository.save(savedTask);

        assertThat(updTask.getDueDate()).isNotNull();
        assertThat(updTask.getDescription()).isNotNull();
    }

    @Test
    public void TaskRepository_DeleteTask_ReturnTaskIsEmpty() {
        Task task = Task
                .builder()
                .title("workspace")
                .dueDate("12/10/2023")
                .description("create workspace").build();

        taskRepository.save(task);

        taskRepository.deleteById(task.getId());
        Optional<Task> taskReturn = taskRepository.findById(task.getId());

        assertThat(taskReturn).isEmpty();
    }
}
