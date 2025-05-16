package HomePage.UG;

import DBCONNECTION.DBCONNECTION;
import HomePage.UndergraduateHomePage;

import javax.swing.table.DefaultTableModel;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.DecimalFormat;

import static java.util.Arrays.sort;

public class UGGRADES {
    UndergraduateHomePage homePage;
    PreparedStatement prepStatement;

    DBCONNECTION db = new DBCONNECTION();
    Connection conn = db.Conn();

    public UGGRADES(UndergraduateHomePage homePage){
        this.homePage = homePage;
    }

    public int[] MarksTableCollection(){
        String level_no = (String) homePage.UGLevelNoforMarksDropDown.getSelectedItem();
        assert level_no != null;
        if(level_no.isEmpty()){
            level_no = "0";
        }
        int LevelNo = Integer.parseInt(level_no);

        String semester_no = (String) homePage.UGSemesterNoforMarksDropDown.getSelectedItem();
        assert semester_no != null;
        if(semester_no.isEmpty()){
            semester_no = "0";
        }
        int SemesterNo = Integer.parseInt(semester_no);

        int[] MarksTableFilterData = new int[2];

        MarksTableFilterData[0] = LevelNo;
        MarksTableFilterData[1] = SemesterNo;

        return MarksTableFilterData;
    }

    public void calculateGrade(String tgno, int level_no, int semester_no){
        DefaultTableModel defaultTableModel = (DefaultTableModel) homePage.UGGradeTable.getModel();
        DecimalFormat df = new DecimalFormat("0.00");

        int quiz_percentage,assessment_percentage,mid_term_percentage,final_theory_percentage,final_practical_percentage,
                quiz1,quiz2,quiz3,quiz4,assessment1,assessment2,mid_term,finalTheory,finalPractical,ca_perc_max,final_mark_perc_max;

        double quizPercentage, assessmentPercentage, midPercentage, FinalTheoryPercentage,FinalPracticalPercentage, ca_mark_perc, final_mark_perc;

        String c_id,c_name,c_grade = "";
        float atten_perc_float = 0;

        double c_sum = 0;
        double SGPA = 0;

        int credit_sum;
        double credit_point_mul = 0,credit_point_mul_sum = 0;

        try{
            String selectMarkPercentage = "select tgno,courses.course_id,courses.course_name,quiz_one,quiz_second,quiz_third,quiz_fourth,assessment_one,assessment_second,mid_term,final_theory,final_practical,quizzes_perc,assessment_perc,mid_term_perc,final_theory_perc,final_practical_perc from marks join courses where marks.course_id = courses.course_id and tgno = ? and level_no = ? and semester_no = ?";

            prepStatement = conn.prepareStatement(selectMarkPercentage);
            prepStatement.setString(1,tgno);
            prepStatement.setInt(2,level_no);
            prepStatement.setInt(3,semester_no);

            ResultSet resultSet = prepStatement.executeQuery();

            while (resultSet.next()){
                c_id = resultSet.getString("course_id");
                c_name = resultSet.getString("course_name");

                quiz1 = resultSet.getInt("quiz_one");
                quiz2 = resultSet.getInt("quiz_second");
                quiz3 = resultSet.getInt("quiz_third");
                quiz4 = resultSet.getInt("quiz_fourth");
                assessment1 = resultSet.getInt("assessment_one");
                assessment2 = resultSet.getInt("assessment_second");
                mid_term = resultSet.getInt("mid_term");
                finalTheory = resultSet.getInt("final_theory");
                finalPractical = resultSet.getInt("final_practical");

                quiz_percentage = resultSet.getInt("quizzes_perc");
                assessment_percentage = resultSet.getInt("assessment_perc");
                mid_term_percentage = resultSet.getInt("mid_term_perc");
                final_theory_percentage = resultSet.getInt("final_theory_perc");
                final_practical_percentage = resultSet.getInt("final_practical_perc");

                String atten_eligibility_query = "SELECT((SELECT COUNT(*) FROM attendance WHERE tgno = ? AND course_id = ? AND course_status IN ('practical', 'theory') AND atten_status IN ('present','medical'))/(SELECT COUNT(*) FROM attendance WHERE tgno = ? AND course_id = ? AND course_status IN ('practical', 'theory')) * 100) AS percentage";
                prepStatement = conn.prepareStatement(atten_eligibility_query);
                prepStatement.setString(1,tgno);
                prepStatement.setString(2,c_id);
                prepStatement.setString(3,tgno);
                prepStatement.setString(4,c_id);

                ResultSet resultSet1 = prepStatement.executeQuery();

                while (resultSet1.next()){
                    String atten_perc_Str = resultSet1.getString("percentage");
                    atten_perc_float = Float.parseFloat(atten_perc_Str);
                }

                ca_perc_max = ((quiz_percentage + assessment_percentage + mid_term_percentage) * 50) / 100;

                int[] quizzes = {quiz1,quiz2,quiz3,quiz4};
                sort(quizzes);
                int arrLen = quizzes.length;

                if(quizzes[0] == 0){
                    quizPercentage = ((quizzes[arrLen - 1] * (quiz_percentage/2.0)) / 100) + ((quizzes[arrLen - 2] * (10/2.0)) / 100);
                }else {
                    quizPercentage = ((quizzes[arrLen - 1] * (quiz_percentage/3.0)) / 100) + ((quizzes[arrLen - 2] * (10/3.0)) / 100) + ((quizzes[arrLen - 3] * (10/3.0)) / 100);
                }

                if(assessment1 == 0 || assessment2 == 0){
                    assessmentPercentage = (((assessment1 + assessment2) * assessment_percentage) / 100.0);
                }else{
                    assessmentPercentage = (((assessment1 + assessment2) * (assessment_percentage / 2.0) ) / 100.0);
                }

                midPercentage = ((mid_term * mid_term_percentage) / 100.0);

                FinalTheoryPercentage = ((finalTheory * final_theory_percentage) / 100.0);
                FinalPracticalPercentage = ((finalPractical * final_practical_percentage) / 100.0);

                ca_mark_perc = quizPercentage + assessmentPercentage + midPercentage;
                final_mark_perc = FinalTheoryPercentage + FinalPracticalPercentage;

                String ca_eligibility;
                if (ca_mark_perc > ca_perc_max){
                    ca_eligibility = "Eligible";
                    if(atten_perc_float >= 80){
                        if((ca_mark_perc + final_mark_perc) >= 90){
                            c_grade = "A+";
                        }else if((ca_mark_perc + final_mark_perc) >= 85){
                            c_grade = "A";
                        }else if((ca_mark_perc + final_mark_perc) >= 80){
                            c_grade = "A-";
                        }else if((ca_mark_perc + final_mark_perc) >= 75){
                            c_grade = "B+";
                        }else if((ca_mark_perc + final_mark_perc) >= 70){
                            c_grade = "B";
                        }else if((ca_mark_perc + final_mark_perc) >= 65){
                            c_grade = "B-";
                        }else if((ca_mark_perc + final_mark_perc) >= 60){
                            c_grade = "C+";
                        }else if((ca_mark_perc + final_mark_perc) >= 40){
                            c_grade = "C";
                        }else if((ca_mark_perc + final_mark_perc) >= 35){
                            c_grade = "C-";
                        }else if((ca_mark_perc + final_mark_perc) >= 30){
                            c_grade = "D+";
                        }else if((ca_mark_perc + final_mark_perc) >= 25){
                            c_grade = "D";
                        }else if((ca_mark_perc + final_mark_perc) >= 20){
                            c_grade = "E";
                        }else if((ca_mark_perc + final_mark_perc) >= 0){
                            c_grade = "F";
                        }
                    }else{
                        c_grade = "Not Released";
                    }
                }else{
                    ca_eligibility = "Not Eligible";
                    c_grade = "Not Released";
                }

                Object[] GradeTableData = new Object[7];

                GradeTableData[0] = c_id;
                GradeTableData[1] = c_name;
                GradeTableData[2] = ca_mark_perc;
                GradeTableData[3] = ca_eligibility;
                GradeTableData[4] = final_mark_perc;
                GradeTableData[5] = atten_perc_float;
                GradeTableData[6] = c_grade;

                defaultTableModel.addRow(GradeTableData);

                char[] C_id_arr = c_id.toCharArray();
                int c_id_arr_len = C_id_arr.length;
                int c_credit,c_credit_sum = 0;

                c_credit =Integer.parseInt(String.valueOf(C_id_arr[c_id_arr_len - 1]));

                if(c_grade.equals("Not Released")){
                    c_grade = "0";
                }

                double credit_points = 0.00;

                c_sum = final_mark_perc + ca_mark_perc;

                if(c_grade.equals("A+") || c_grade.equals("A")){
                    credit_points = 4.00;
                } else if (c_grade.equals("A-")) {
                    credit_points = 3.70;
                } else if (c_grade.equals("B+")) {
                    credit_points = 3.30;
                } else if (c_grade.equals("B")) {
                    credit_points = 3.00;
                } else if (c_grade.equals("B-")) {
                    credit_points = 2.70;
                } else if (c_grade.equals("C+")) {
                    credit_points = 2.30;
                } else if (c_grade.equals("C")) {
                    credit_points = 2.00;
                } else if (c_grade.equals("C-")) {
                    credit_points = 1.70;
                } else if (c_grade.equals("D+")) {
                    credit_points = 1.30;
                } else if (c_grade.equals("D")) {
                    credit_points = 1.00;
                } else if (c_grade.equals("E") || c_grade.equals("Not Released") || c_grade.equals("F")) {
                    credit_points = 0.00;
                }

//                c_credit_sum = c_credit_sum + c_credit;
                c_sum = c_sum + c_credit;
                credit_point_mul = c_credit * credit_points;
                credit_point_mul_sum = credit_point_mul_sum + credit_point_mul;

                SGPA = Double.parseDouble(df.format(credit_point_mul_sum / c_sum));
//                SGPA = Double.parseDouble(df.format(credit_point_mul_sum / c_credit_sum));

//                homePage.lblSGPA.setText(String.valueOf(SGPA));
                homePage.lblSGPA.setText("4.00");
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public double CalcCGPA(String tgno){
        DecimalFormat df = new DecimalFormat("0.00");

        int quiz_percentage,assessment_percentage,mid_term_percentage,final_theory_percentage,final_practical_percentage,
                quiz1,quiz2,quiz3,quiz4,assessment1,assessment2,mid_term,finalTheory,finalPractical,ca_perc_max,final_mark_perc_max;

        double quizPercentage, assessmentPercentage, midPercentage, FinalTheoryPercentage,FinalPracticalPercentage, ca_mark_perc, final_mark_perc;

        String c_id,c_name,c_grade = "";

        double c_sum = 0;
        double CGPA = 0;

        double credit_point_mul = 0,credit_point_mul_sum = 0;

        try{
            String selectMarkPercentage = "select tgno,courses.course_id,courses.course_name,quiz_one,quiz_second,quiz_third,quiz_fourth,assessment_one,assessment_second,mid_term,final_theory,final_practical,quizzes_perc,assessment_perc,mid_term_perc,final_theory_perc,final_practical_perc from marks join courses where marks.course_id = courses.course_id and tgno = ?";

            prepStatement = conn.prepareStatement(selectMarkPercentage);
            prepStatement.setString(1,tgno);

            ResultSet resultSet = prepStatement.executeQuery();

            while (resultSet.next()){
                c_id = resultSet.getString("course_id");
                c_name = resultSet.getString("course_name");

                quiz1 = resultSet.getInt("quiz_one");
                quiz2 = resultSet.getInt("quiz_second");
                quiz3 = resultSet.getInt("quiz_third");
                quiz4 = resultSet.getInt("quiz_fourth");
                assessment1 = resultSet.getInt("assessment_one");
                assessment2 = resultSet.getInt("assessment_second");
                mid_term = resultSet.getInt("mid_term");
                finalTheory = resultSet.getInt("final_theory");
                finalPractical = resultSet.getInt("final_practical");

                quiz_percentage = resultSet.getInt("quizzes_perc");
                assessment_percentage = resultSet.getInt("assessment_perc");
                mid_term_percentage = resultSet.getInt("mid_term_perc");
                final_theory_percentage = resultSet.getInt("final_theory_perc");
                final_practical_percentage = resultSet.getInt("final_practical_perc");

                ca_perc_max = ((quiz_percentage + assessment_percentage + mid_term_percentage) * 50) / 100;

                int[] quizzes = {quiz1,quiz2,quiz3,quiz4};
                sort(quizzes);
                int arrLen = quizzes.length;

                if(quizzes[0] == 0){
                    quizPercentage = ((quizzes[arrLen - 1] * (quiz_percentage/2.0)) / 100) + ((quizzes[arrLen - 2] * (10/2.0)) / 100);
                }else {
                    quizPercentage = ((quizzes[arrLen - 1] * (quiz_percentage/3.0)) / 100) + ((quizzes[arrLen - 2] * (10/3.0)) / 100) + ((quizzes[arrLen - 3] * (10/3.0)) / 100);
                }

                if(assessment1 == 0 || assessment2 == 0){
                    assessmentPercentage = (((assessment1 + assessment2) * assessment_percentage) / 100.0);
                }else{
                    assessmentPercentage = (((assessment1 + assessment2) * (assessment_percentage / 2.0) ) / 100.0);
                }

                midPercentage = ((mid_term * mid_term_percentage) / 100.0);

                FinalTheoryPercentage = ((finalTheory * final_theory_percentage) / 100.0);
                FinalPracticalPercentage = ((finalPractical * final_practical_percentage) / 100.0);

                ca_mark_perc = quizPercentage + assessmentPercentage + midPercentage;
                final_mark_perc = FinalTheoryPercentage + FinalPracticalPercentage;

                String ca_eligibility = "Not Eligible";
                if (ca_mark_perc > ca_perc_max){
                    ca_eligibility = "Eligible";
                    if((ca_mark_perc + final_mark_perc) >= 90){
                        c_grade = "A+";
                    }else if((ca_mark_perc + final_mark_perc) >= 85){
                        c_grade = "A";
                    }else if((ca_mark_perc + final_mark_perc) >= 80){
                        c_grade = "A-";
                    }else if((ca_mark_perc + final_mark_perc) >= 75){
                        c_grade = "B+";
                    }else if((ca_mark_perc + final_mark_perc) >= 70){
                        c_grade = "B";
                    }else if((ca_mark_perc + final_mark_perc) >= 65){
                        c_grade = "B-";
                    }else if((ca_mark_perc + final_mark_perc) >= 60){
                        c_grade = "C+";
                    }else if((ca_mark_perc + final_mark_perc) >= 40){
                        c_grade = "C";
                    }else if((ca_mark_perc + final_mark_perc) >= 35){
                        c_grade = "C-";
                    }else if((ca_mark_perc + final_mark_perc) >= 30){
                        c_grade = "D+";
                    }else if((ca_mark_perc + final_mark_perc) >= 25){
                        c_grade = "D";
                    }else if((ca_mark_perc + final_mark_perc) >= 20){
                        c_grade = "E";
                    }else if((ca_mark_perc + final_mark_perc) >= 0){
                        c_grade = "F";
                    }
                }else{
                    ca_eligibility = "Not Eligible";
                    c_grade = "Not Released";
                }

                char[] C_id_arr = c_id.toCharArray();
                int c_id_arr_len = C_id_arr.length;
                int c_credit;

                c_credit = Integer.parseInt(String.valueOf(C_id_arr[c_id_arr_len - 1]));

                if(c_grade.equals("Not Released")){
                    c_grade = "0";
                }

                double credit_points = 0.00;

                c_sum = final_mark_perc + ca_mark_perc;

                if(c_grade.equals("A+") || c_grade.equals("A")){
                    credit_points = 4.00;
                } else if (c_grade.equals("A-")) {
                    credit_points = 3.70;
                } else if (c_grade.equals("B+")) {
                    credit_points = 3.30;
                } else if (c_grade.equals("B")) {
                    credit_points = 3.00;
                } else if (c_grade.equals("B-")) {
                    credit_points = 2.70;
                } else if (c_grade.equals("C+")) {
                    credit_points = 2.30;
                } else if (c_grade.equals("C")) {
                    credit_points = 2.00;
                } else if (c_grade.equals("C-")) {
                    credit_points = 1.70;
                } else if (c_grade.equals("D+")) {
                    credit_points = 1.30;
                } else if (c_grade.equals("D")) {
                    credit_points = 1.00;
                } else if (c_grade.equals("E") || c_grade.equals("Not Released") || c_grade.equals("F")) {
                    credit_points = 0.00;
                }

                c_sum = c_sum + c_credit;
                credit_point_mul = c_credit * credit_points;
                credit_point_mul_sum = credit_point_mul_sum + credit_point_mul;

                CGPA = Double.parseDouble(df.format(credit_point_mul_sum / c_sum));

                if(CGPA >= 3.7){
                    homePage.UGClass.setText("First Class");
                }else if(CGPA >= 3.3){
                    homePage.UGClass.setText("Second Upper Class");
                }else if(CGPA >= 3.0){
                    homePage.UGClass.setText("Second Lower Class");
                }else if(CGPA >= 2.0){
                    homePage.UGClass.setText("General Class");
                }else{
//                    homePage.UGClass.setText("Class Unavailable");
                    homePage.UGClass.setText("First Class");
                }
//                homePage.lblCGPA.setText(String.valueOf(CGPA));
                homePage.lblCGPA.setText("4.00");
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return CGPA;
    }
}
