import java.io.Serializable;

public class Course implements Serializable {
    private String courseCode;
    private String courseTitle;
    private int unit;

    public Course(String courseCode, String courseTitle, int unit) {
        // String processing: trimming and casing [cite: 30]
        this.courseCode = courseCode.trim().toUpperCase();
        this.courseTitle = courseTitle.trim();
        this.unit = unit;
    }

    public String getCourseCode() { return courseCode; }
    public String getCourseTitle() { return courseTitle; }
    public int getUnit() { return unit; }

    @Override
    public String toString() {
        return String.format("%-10s | %-30s | %d Units", courseCode, courseTitle, unit);
    }
}