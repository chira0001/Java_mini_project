package HomePage.UG;

import DBCONNECTION.DBCONNECTION;
import HomePage.UndergraduateHomePage;

import javax.swing.table.DefaultTableModel;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class TIMETABLE {
    PreparedStatement prepStatement;
    DBCONNECTION db = new DBCONNECTION();
    Connection conn = db.Conn();
    UndergraduateHomePage homepage;

    public TIMETABLE(UndergraduateHomePage homePage){
        this.homepage = homePage;
    }

    public void valuesForTimeTable(int level_no, int semester_no){

        System.out.println("Level " + level_no + " Semester " + semester_no);

        String TimeTableValues = "select time_table_id, Courses.course_id, module_day, course_name, time from timeTable join Courses where timeTable.course_id = Courses.course_id and level_no = ? and semester_no = ? ORDER BY CASE module_day WHEN 'Monday' THEN 1 WHEN 'Tuesday' THEN 2 WHEN 'Wednesday' THEN 3 WHEN 'Thursday' THEN 4 WHEN 'Friday' THEN 5 WHEN 'Saturday' THEN 6 WHEN 'Sunday' THEN 7 END";
        DefaultTableModel tblmodel = (DefaultTableModel) homepage.tableTimeTable.getModel();
        try{
            prepStatement = conn.prepareStatement(TimeTableValues);
            prepStatement.setInt(1,level_no);
            prepStatement.setInt(2,semester_no);
            ResultSet result = prepStatement.executeQuery();
            while (result.next()){
                String courseID = result.getString("course_id");
                String moduleDay = result.getString("module_day");
                String courseName = result.getString("course_name");
                String courseTime = result.getString("time");

                Object[] timeTableData = new Object[4];

                timeTableData[0] = moduleDay;
                timeTableData[1] = courseID;
                timeTableData[2] = courseName;
                timeTableData[3] = courseTime;

                tblmodel.addRow(timeTableData);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    public int[] TimeTableCollection(){
        String level_no = (String) homepage.LevelNoDropDown.getSelectedItem();
        assert level_no != null;
        if(level_no.isEmpty()){
            level_no = "0";
        }
        int LevelNo = Integer.parseInt(level_no);

        String semester_no = (String) homepage.SemesterNoDropDown.getSelectedItem();
        assert semester_no != null;
        if(semester_no.isEmpty()){
            semester_no = "0";
        }
        int SemesterNo = Integer.parseInt(semester_no);

        int[] timetablefilterdata = new int[2];

        timetablefilterdata[0] = LevelNo;
        timetablefilterdata[1] = SemesterNo;

        return timetablefilterdata;
    }
}
