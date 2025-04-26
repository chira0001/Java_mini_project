package HomePage.UG;

import DBCONNECTION.DBCONNECTION;
import HomePage.UndergraduateHomePage;

import javax.swing.*;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Scanner;

public class UGCOURSE {
        UndergraduateHomePage homePage;
        PreparedStatement prepStatement;
        Scanner input;

        DBCONNECTION db = new DBCONNECTION();
        Connection conn = db.Conn();

        public UGCOURSE(UndergraduateHomePage homePage){
            this.homePage = homePage;
        }

    public int[] CourseTableCollection(){
        String SemesterforCoursesStr = (String) homePage.SemesterNoforCourses.getSelectedItem();
        assert SemesterforCoursesStr != null;
        if(SemesterforCoursesStr.isEmpty()){
            SemesterforCoursesStr = "0";
        }
        int SemesterforCoursesInt = Integer.parseInt(SemesterforCoursesStr);

        String LevelforCoursesStr = (String) homePage.LevelNoforCourses.getSelectedItem();
        assert LevelforCoursesStr != null;
        if(LevelforCoursesStr.isEmpty()){
            LevelforCoursesStr = "0";
        }
        int LevelforCoursesInt = Integer.parseInt(LevelforCoursesStr);

        int[] CourseTableFilterData = new int[2];
        CourseTableFilterData[0] = LevelforCoursesInt;
        CourseTableFilterData[1] = SemesterforCoursesInt;

        return CourseTableFilterData;
    }

    public void CourseDetailsforCourses(){

        String level_no = (String) homePage.LevelNoforCourses.getSelectedItem();
        String semester_no = (String) homePage.SemesterNoforCourses.getSelectedItem();

        try{
            String c_id,course_name,c_material,Course;

            String selectCourseQuery = "select distinct(c_id),course_name from course_materials join courses on course_materials.c_id = courses.course_id where courses.level_no = ? and courses.semester_no = ?";
            prepStatement = conn.prepareStatement(selectCourseQuery);
            prepStatement.setInt(1,Integer.parseInt(level_no));
            prepStatement.setInt(2,Integer.parseInt(semester_no));

            ResultSet resultSet = prepStatement.executeQuery();
            homePage.CourseforCourses.removeAllItems();

            while(resultSet.next()){
                c_id = resultSet.getString("c_id");
                course_name = resultSet.getString("course_name");

                Course = c_id + " - " + course_name;
                homePage.CourseforCourses.addItem(Course);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void LoadCourseMaterialDesc(){
        String selectedCourse = (String) homePage.CourseforCourses.getSelectedItem();
        if(selectedCourse != null){
            String regex = "\\s";
            assert selectedCourse != null;
            String[] str_split = selectedCourse.split(regex);
            String course_material = (String) homePage.CourseMaterialforCourses.getSelectedItem();
            try{
                String c_material_desc;

                String selectCourseMatDescQuery = "select c_material_desc from course_materials join courses on course_materials.c_id = courses.course_id where c_id = ? and c_material = ?";
                prepStatement = conn.prepareStatement(selectCourseMatDescQuery);
                prepStatement.setString(1,str_split[0]);
                prepStatement.setString(2,course_material);

                ResultSet resultSet = prepStatement.executeQuery();
                while (resultSet.next()){
                    c_material_desc = resultSet.getString("c_material_desc");

                    File CourseMatDesc = new File(c_material_desc);
                    input = new Scanner(CourseMatDesc);

                    StringBuilder MaterialDescContent = new StringBuilder();

                    while (input.hasNextLine()){
                        MaterialDescContent.append(input.nextLine()).append("\n");
                    }
                    homePage.DescriptionforCourseMaterial.setText(MaterialDescContent.toString());
                }

            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }

    public String selectCourseMaterial(int level_no, int semester_no, String course_id, String course_material){
        String materialPath = "";
        try{
            String SelectCourseMaterialQuery = "select c_material_location from course_materials join courses on course_materials.c_id = courses.course_id where courses.level_no = ? and courses.semester_no = ? and c_id = ? and c_material = ?";
            prepStatement = conn.prepareStatement(SelectCourseMaterialQuery);

            prepStatement.setInt(1,level_no);
            prepStatement.setInt(2,semester_no);
            prepStatement.setString(3,course_id);
            prepStatement.setString(4,course_material);

            ResultSet resultSet = prepStatement.executeQuery();

            while (resultSet.next()){
                materialPath = resultSet.getString("c_material_location");
            }
            return materialPath;

        }catch(Exception e){
            e.printStackTrace();
        }
        return materialPath;
    }

    public void saveResourceToFile(String resourcePath, String filename) throws IOException {
        JFileChooser directoryChooser = new JFileChooser();
        directoryChooser.setDialogTitle("Select Folder to Save File");
        directoryChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        directoryChooser.setAcceptAllFileFilterUsed(false);

        int userSelection = directoryChooser.showSaveDialog(null);

        if (userSelection == JFileChooser.APPROVE_OPTION) {
            File selectedDir = directoryChooser.getSelectedFile();
            File destinationFile = new File(selectedDir,filename);

            Path currentPath = Path.of(resourcePath);

            if (resourcePath == null || resourcePath.equals("")) {
                JOptionPane.showMessageDialog(null, "Resource not found: " + resourcePath);
            }else{
                Files.copy(currentPath, destinationFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
                JOptionPane.showMessageDialog(null, "File saved to: " + destinationFile.getAbsolutePath());
            }
        }
    }
    public void CourseMaterialDetailsforCourses(){
        String course_id = (String) homePage.CourseforCourses.getSelectedItem();

        String regex = "\\s";
        assert course_id != null;
        String[] str_split = course_id.split(regex);
        try{
            String selectCourseMatQuery = "select c_material from course_materials join courses on course_materials.c_id = courses.course_id where c_id = ?";
            prepStatement = conn.prepareStatement(selectCourseMatQuery);
            prepStatement.setString(1,str_split[0]);

            ResultSet resultSet = prepStatement.executeQuery();
            homePage.CourseMaterialforCourses.removeAllItems();

            while(resultSet.next()){
                String c_material = resultSet.getString("c_material");

                homePage.CourseMaterialforCourses.addItem(c_material);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
