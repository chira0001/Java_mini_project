package HomePage.UG;

import DBCONNECTION.DBCONNECTION;
import HomePage.UndergraduateHomePage;

import javax.swing.table.DefaultTableModel;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class UGMEDICAL {
    UndergraduateHomePage homePage;
    PreparedStatement prepStatement;

    DBCONNECTION db = new DBCONNECTION();
    Connection conn = db.Conn();

    public UGMEDICAL(UndergraduateHomePage homePage){
        this.homePage = homePage;
    }

    public int[] MedicalTableCollection(){
        String SemesterforMedicalStr = (String) homePage.SemesterNoforMedical.getSelectedItem();
        assert SemesterforMedicalStr != null;
        if(SemesterforMedicalStr.isEmpty()){
            SemesterforMedicalStr = "0";
        }
        int SemesterforMedicalInt = Integer.parseInt(SemesterforMedicalStr);

        String LevelforMedicalStr = (String) homePage.LevelNoforMedical.getSelectedItem();
        assert LevelforMedicalStr != null;
        if(LevelforMedicalStr.isEmpty()){
            LevelforMedicalStr = "0";
        }
        int LevelforMedicalInt = Integer.parseInt(LevelforMedicalStr);

        int[] MedicalTableFilterData = new int[2];

        MedicalTableFilterData[0] = LevelforMedicalInt;
        MedicalTableFilterData[1] = SemesterforMedicalInt;

        return MedicalTableFilterData;
    }

    public void LoadMedicalTable(String tgno, int level_no, int semester_no){
        DefaultTableModel tblmodel = (DefaultTableModel) homePage.UGMedicalTable.getModel();
        try{
            String medLoadQuery = "select medical_no,courses.course_id,courses.course_name,course_status,medical.week_no,med_reason from attendance join medical on attendance.med_id = medical.medical_no join courses on attendance.course_id = courses.course_id where medical.tgno = ? and level_no = ? and semester_no = ?";
            prepStatement = conn.prepareStatement(medLoadQuery);
            prepStatement.setString(1,tgno);
            prepStatement.setString(2, String.valueOf(level_no));
            prepStatement.setString(3, String.valueOf(semester_no));

            ResultSet resultSet = prepStatement.executeQuery();
            while(resultSet.next()){
                String med_no = resultSet.getString("medical_no");
                String course_id = resultSet.getString("course_id");
                String course_name = resultSet.getString("course_name");
                String course_status = resultSet.getString("course_status");
                String week_no = resultSet.getString("week_no");
                String med_reason = resultSet.getString("med_reason");

                Object[] med_det = new Object[6];

                med_det[0] = med_no;
                med_det[1] = course_id;
                med_det[2] = course_name;
                med_det[3] = course_status;
                med_det[4] = week_no;
                med_det[5] = med_reason;

                tblmodel.addRow(med_det);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
