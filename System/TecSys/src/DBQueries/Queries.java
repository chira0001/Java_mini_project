package DBQueries;

public class Queries {

    String q1 = "select c_material from course_materials join courses on course_materials.c_id = courses.course_id where courses.level_no = ? and courses.semester_no = ? and c_id = ?";
}
