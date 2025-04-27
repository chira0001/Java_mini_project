package HomePage.UG;

import DBCONNECTION.DBCONNECTION;
import HomePage.UndergraduateHomePage;

import javax.swing.table.DefaultTableModel;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class UGATTENDANCE {
    DBCONNECTION db = new DBCONNECTION();
    Connection conn = db.Conn();

    UndergraduateHomePage homePage;
    PreparedStatement prepStatement;

    public UGATTENDANCE(UndergraduateHomePage homePage){
        this.homePage = homePage;
    }

    public void valuesforAttendanceTable(int level_no, int semester_no, String tgno){
        String AttendanceTableValues = "select courses.course_id,course_name,course_status,week_no,atten_status,med_id from attendance join courses where attendance.course_id = courses.course_id and tgno = ? and level_no = ? and semester_no = ?";
        DefaultTableModel tblmodel = (DefaultTableModel) homePage.AttendanceTable.getModel();
        try{
            prepStatement = conn.prepareStatement(AttendanceTableValues);
            prepStatement.setString(1,tgno);
            prepStatement.setInt(2,level_no);
            prepStatement.setInt(3,semester_no);
            ResultSet result = prepStatement.executeQuery();
            while (result.next()){
                String courseID = result.getString("course_id");
                String courseName = result.getString("course_name");
                String course_status = result.getString("course_status");
                String week_no = result.getString("week_no");
                String atten_status = result.getString("atten_status");
                String med_id = result.getString("med_id");

                Object[] AttendanceTableData = new Object[6];

                AttendanceTableData[0] = week_no;
                AttendanceTableData[1] = courseID;
                AttendanceTableData[2] = courseName;
                AttendanceTableData[3] = course_status;
                AttendanceTableData[4] = atten_status;
                AttendanceTableData[5] = med_id;

                tblmodel.addRow(AttendanceTableData);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void LoadAttendanceCourseNumber(String tgno, int level_no, int semester_no){
        String Atten_Course_Code;
        try{
            String noticeLoadQuery = "select distinct courses.course_id from attendance join courses where attendance.course_id = courses.course_id and tgno = ? and level_no = ? and semester_no = ?";

            prepStatement = conn.prepareStatement(noticeLoadQuery);
            prepStatement.setString(1,tgno);
            prepStatement.setInt(2,level_no);
            prepStatement.setInt(3,semester_no);

            ResultSet result = prepStatement.executeQuery();

            homePage.AttendanceSubjectCode.removeAllItems();
            homePage.AttendanceSubjectCode.addItem("");

            while(result.next()){
                Atten_Course_Code = result.getString("course_id");
                homePage.AttendanceSubjectCode.addItem(Atten_Course_Code);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void LoadAttendanceCourseStatus(String tgno, String course_id){
        String Atten_Course_Status;
        try{
            String Atten_Course_StatusLoadQuery = "select distinct attendance.course_status from attendance join courses where attendance.course_id = courses.course_id and tgno = ? and courses.course_id = ?";

            prepStatement = conn.prepareStatement(Atten_Course_StatusLoadQuery);
            prepStatement.setString(1,tgno);
            prepStatement.setString(2,course_id);

            ResultSet result = prepStatement.executeQuery();

            homePage.AttendanceSubjectStatus.removeAllItems();
            homePage.AttendanceSubjectStatusPerc.removeAllItems();

            homePage.AttendanceSubjectStatus.addItem("");
            homePage.AttendanceSubjectStatusPerc.addItem("");

            while(result.next()){
                Atten_Course_Status = result.getString("course_status");
                homePage.AttendanceSubjectStatus.addItem(Atten_Course_Status);
                homePage.AttendanceSubjectStatusPerc.addItem(Atten_Course_Status);
            }

            int itemCount = homePage.AttendanceSubjectStatusPerc.getItemCount();
            if(itemCount>2){
                homePage.AttendanceSubjectStatusPerc.addItem("Theory/Practical");
            }

        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void LoadAttendanceTable(String tgno, int level_no, int semester_no, String course_id, String course_status) {
        try{
//            _TableSet.AttendanceTableSetMethod();
            new TableSetMeth(homePage).AttendanceTableSetMethod();
            DefaultTableModel tblmodel = (DefaultTableModel) homePage.AttendanceTable.getModel();

            String LoadAttendanceQuery = "select attendance.week_no,courses.course_id,courses.course_name,atten_status,attendance.med_id,medical.med_reason from attendance join courses on attendance.course_id = courses.course_id left join medical on attendance.med_id = medical.medical_no where attendance.tgno = ? and level_no = ? and semester_no = ? and attendance.course_id = ? and attendance.course_status = ?";

            prepStatement = conn.prepareStatement(LoadAttendanceQuery);

            prepStatement.setString(1,tgno);
            prepStatement.setInt(2,level_no);
            prepStatement.setInt(3,semester_no);
            prepStatement.setString(4,course_id);
            prepStatement.setString(5,course_status);

            ResultSet resultSet = prepStatement.executeQuery();

            while (resultSet.next()){
                String table_week_no = resultSet.getString("week_no");
                String table_course_id = resultSet.getString("course_id");
                String table_course_name = resultSet.getString("course_name");
                String table_atten_status = resultSet.getString("atten_status");
                String table_med_id = resultSet.getString("med_id");
                String med_reason = resultSet.getString("med_reason");

                if(med_reason == "NULL"){
                    med_reason = "";
                }

                Object[] attendanceTableData = new Object[6];

                attendanceTableData[0] = table_week_no;
                attendanceTableData[1] = table_course_id;
                attendanceTableData[2] = table_course_name;
                attendanceTableData[3] = table_atten_status;
                attendanceTableData[4] = table_med_id;
                attendanceTableData[5] = med_reason;

                tblmodel.addRow(attendanceTableData);
            }

        }catch(Exception e){
            e.printStackTrace();
        }
    }

    public void LoadAttendancePercentages(String atten_status, String tgno,String course_id){
        try{
            String Atten_Perc_Query_without_Med = "", Atten_Perc_Query_with_Med = "";

            if(atten_status != null && !atten_status.isEmpty()){
                if(atten_status.equals("theory")||atten_status.equals("Theory")){
                    Atten_Perc_Query_without_Med = "SELECT((SELECT COUNT(*) FROM attendance WHERE tgno = ? AND course_id = ? AND course_status IN ('theory') AND atten_status IN ('present'))/(SELECT COUNT(*) FROM attendance WHERE tgno = ? AND course_id = ? AND course_status IN ('theory')) * 100) AS percentage"; //4
                    Atten_Perc_Query_with_Med = "SELECT((SELECT COUNT(*) FROM attendance WHERE tgno = ? AND course_id = ? AND course_status IN ('theory') AND atten_status IN ('present','medical'))/(SELECT COUNT(*) FROM attendance WHERE tgno = ? AND course_id = ? AND course_status IN ('theory')) * 100) AS percentage";//4
                }
                else if(atten_status.equals("practical")||atten_status.equals("Practical")) {
                    Atten_Perc_Query_without_Med = "SELECT((SELECT COUNT(*) FROM attendance WHERE tgno = ? AND course_id = ? AND course_status IN ('practical') AND atten_status IN ('present'))/(SELECT COUNT(*) FROM attendance WHERE tgno = ? AND course_id = ? AND course_status IN ('practical')) * 100) AS percentage";//4
                    Atten_Perc_Query_with_Med = "SELECT((SELECT COUNT(*) FROM attendance WHERE tgno = ? AND course_id = ? AND course_status IN ('practical') AND atten_status IN ('present','medical'))/(SELECT COUNT(*) FROM attendance WHERE tgno = ? AND course_id = ? AND course_status IN ('practical')) * 100) AS percentage";//4
                }
                else if(atten_status.equals("theory/practical")||atten_status.equals("Theory/Practical")){
                    Atten_Perc_Query_without_Med = "SELECT((SELECT COUNT(*) FROM attendance WHERE tgno = ? AND course_id = ? AND course_status IN ('practical', 'theory') AND atten_status IN ('present'))/(SELECT COUNT(*) FROM attendance WHERE tgno = ? AND course_id = ? AND course_status IN ('practical', 'theory')) * 100) AS percentage";//4
                    Atten_Perc_Query_with_Med = "SELECT((SELECT COUNT(*) FROM attendance WHERE tgno = ? AND course_id = ? AND course_status IN ('practical', 'theory') AND atten_status IN ('present','medical'))/(SELECT COUNT(*) FROM attendance WHERE tgno = ? AND course_id = ? AND course_status IN ('practical', 'theory')) * 100) AS percentage";//4
                }
            }

            if(!Atten_Perc_Query_without_Med.isEmpty()){
                prepStatement = conn.prepareStatement(Atten_Perc_Query_without_Med);
                prepStatement.setString(1,tgno);
                prepStatement.setString(2,course_id);
                prepStatement.setString(3,tgno);
                prepStatement.setString(4,course_id);

                ResultSet resultSet = prepStatement.executeQuery();
                while (resultSet.next()){
                    String perc_without_med_Str = resultSet.getString("percentage");
                    if(perc_without_med_Str.isEmpty()){
                        homePage.AttendancePercWithoutMed.setText("-%");
                    }else{
                        float perc_without_med_Int = Float.parseFloat(perc_without_med_Str);
                        homePage.AttendancePercWithoutMed.setText(perc_without_med_Int + "%");
                    }
                }
            }

            if(!Atten_Perc_Query_with_Med.isEmpty()){
                prepStatement = conn.prepareStatement(Atten_Perc_Query_with_Med);
                prepStatement.setString(1,tgno);
                prepStatement.setString(2,course_id);
                prepStatement.setString(3,tgno);
                prepStatement.setString(4,course_id);

                ResultSet resultSet = prepStatement.executeQuery();
                while (resultSet.next()){
                    String perc_with_med_Str = resultSet.getString("percentage");
                    if(perc_with_med_Str.isEmpty()){
                        homePage.AttendancePercWithMed.setText("-%");
                    }else{
                        float perc_with_med_Int = Float.parseFloat(perc_with_med_Str);
                        homePage.AttendancePercWithMed.setText(perc_with_med_Int + "%");
                    }
                }
            }
        }catch(Exception e){
            e.printStackTrace();
        }
    }

}
