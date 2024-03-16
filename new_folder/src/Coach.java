import java.util.ArrayList;
import java.util.List;

public class Coach {
    private String name;
    private List<Lesson> lessonsTaught;

    public Coach(String name) {
        this.name = name;
        this.lessonsTaught = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Lesson> getLessonsTaught() {
        return lessonsTaught;
    }

    public void setLessonsTaught(List<Lesson> lessonsTaught) {
        this.lessonsTaught = lessonsTaught;
    }

    public void addLesson(Lesson lesson) {
        lessonsTaught.add(lesson);
    }
}
