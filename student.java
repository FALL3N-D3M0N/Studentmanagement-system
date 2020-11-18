import javax.script.ScriptContext;
import java.util.Scanner;
import java.io.*;
import java.util.logging.FileHandler;

class marks implements Serializable{     //serializabale is used to write a object to a file
    int math=0;
    int physics=0;
    int chemistry=0;
    int english=0;
    int computerscience=0;

}
public class student implements Serializable{
    String name;
    String rollno;
    String DOB;
    int year_of_joining;
    int mark;
    float totalclassesattended=0;
    float totalnumberofclasses=0;
    float attendancepercentage=0;
    int years;
    marks obj[]=new marks[10];

    void viewattendacepercentage() //calculates attendace percentage
    {
        try {
            System.out.println("Total classes held        :" + totalnumberofclasses);
            System.out.println("Total classes attended    :" + totalclassesattended);
            attendancepercentage = (totalclassesattended / totalnumberofclasses) * 100;
            System.out.println("Attendance percentage     :" + attendancepercentage+"%");
        }
        catch(ArithmeticException e) //in case of 0/0 error
        {
            System.out.println("No classes have been conducted yet");
        }
    }
    void viewperdetails()//displays personal details
    {
        System.out.println("Name:"+name);
        System.out.println("rollno:"+rollno);
        System.out.println("Date of birth:"+DOB);
        System.out.println("year of joining:"+year_of_joining);
    }
    void viewmarks()//displays marks
    {
        for(int i=1;i<=years;i++)
        {
            System.out.println("YEAR---"+i);
            System.out.println("Subject : math                marks:"+obj[i].math);
            System.out.println("Subject : physics             marks:"+obj[i].physics);
            System.out.println("Subject : chemistry           marks:"+obj[i].chemistry);
            System.out.println("Subject : english             marks:"+obj[i].english);
            System.out.println("Subject : computer science    marks:"+obj[i].computerscience);
        }
    }
    void generatereport()             //calcs average for each year and prints the appropriate performance report
    {
        if(years<2)
        {
            System.out.println("Not enough data to generate report");
            return;
        }
        int[] average=new int[10]; //java array syntax
        for(int i=1;i<=years;i++)  //calculates average here
        {
            average[i]=(obj[i].math+obj[i].computerscience+obj[i].english+obj[i].chemistry+obj[i].physics)/5;
            System.out.println("Your average in year "+i+" is "+average[i]);
        }
        int flag=0;
        for(int i=1;i<=years;i++)
        {
            if(i<years-2)
            {
                if(average[i]>average[i+1])
                {
                    flag=1;
                    break;
                }
                else if(average[i]<average[i+1]&&average[i+1]>average[i+2])
                {
                    flag=2;
                    break;
                }
            }
        }

        if(flag==0)
            System.out.println("Your performance is improving keep up the good job");
        if(flag==1)
            System.out.println("Your performance is dropping please try to  improve");
        if(flag==2)
            System.out.println("Your  performance is lacking focus");
    }
}

class admin extends student{
void setdetails(student s1)         //sets the details and marks
{
    Scanner input=new Scanner(System.in);
    System.out.println("Enter date of birth");
    s1.DOB=input.nextLine();
    System.out.println("Enter year of joining");
    s1.year_of_joining=input.nextInt();
    System.out.println("Would you like to enter marks now?");//asks= the user to add marks
    System.out.println("Press <1> for yes\n Press <2>for no");
    int choice=input.nextInt();
    if(choice==1)
    setmarks(s1);
}
void setmarks(student s1)               //sets the marks
{
    Scanner input=new Scanner(System.in);

    System.out.println("Enter number of years you wish to enter the mark for");
    s1.years= input.nextInt();

    for(int i=1;i<=s1.years;i++)
    {
        s1.obj[i]=new marks();
        System.out.println("Enter marks for math year"+i);
        s1.obj[i].math=input.nextInt();
        System.out.println("Enter marks for physics year"+i);
        s1.obj[i].physics=input.nextInt();
        System.out.println("Enter marks for chemistry year"+i);
        s1.obj[i].chemistry=input.nextInt();
        System.out.println("Enter marks for english year"+i);
        s1.obj[i].english=input.nextInt();
        System.out.println("Enter marks for computer science year"+i);
        s1.obj[i].computerscience=input.nextInt();
    }

}

}

class driver{           //contains the main function
    public static void createstudent()        //gets student details and stores in a .bin file
    {
        Scanner input =new Scanner(System.in);
        String name;
        String rollno;
        System.out.println("Enter name");
        name=input.nextLine();
        System.out.println("Enter roll no");
        rollno=input.nextLine();
        student s1=new student();
        s1.name=name;
        s1.rollno=rollno;
        admin a1=new admin();
        a1.setdetails(s1);
        try{
            FileOutputStream f=new FileOutputStream(new File("Student.bin"),true);//opens a stream to output to file
            ObjectOutputStream o=new ObjectOutputStream(f);//object output stream because we are writing objects
            o.writeObject(s1);
            o.close();
            f.close();
            System.out.println("User created");
        }
        catch(Exception e)
        {
            System.out.println("AN error has occured "+e);
        }
        finally
        {
            menu();
        }


    }
    public static void stufunc( student s2)  //menu to show student details
    {

        Scanner input =new Scanner(System.in);
        System.out.println("Welcome :\n"+s2.name +"\nYOU MADE IT THIS FAR IN 2020!keep it up ");
        System.out.println("what would you like to do");
        System.out.println("1.View personal details\n2.view marks\n3.view attendance percentage\n4.Generate performance report");
        int choice=input.nextInt();
        if(choice==1) {
            s2.viewperdetails();
            menu();
        }
        if(choice==2) {
            s2.viewmarks();
            menu();
        }
        if(choice==3) {
            s2.viewattendacepercentage();
            menu();
        }
        if(choice==4) {
            s2.generatereport();
            menu();
        }
    }
    public static void addattendance()
    {
        Scanner input=new Scanner(System.in);
       System.out.println("are you sure u wish to add attendance?");
       System.out.println("Press <1> to confirm\npress <2> to go back");
       int choice=input.nextInt();
       if(choice==1)
       {
           try{
               FileInputStream fi=new FileInputStream(new File("Student.bin"));
               FileOutputStream f= new FileOutputStream(new File("temp.bin"));
               ObjectOutputStream o=new ObjectOutputStream(f);
               while(true)
               {
                   ObjectInputStream oi=new ObjectInputStream(fi);
                   student s1=(student)oi.readObject();
                   System.out.println("Name="+s1.name+"Roll no:"+s1.rollno);
                   System.out.println("press <1> to mark as present\nPress <2> to mark as absent");
                   choice=input.nextInt();
                   if(choice==1)
                   {
                       s1.totalclassesattended++;
                       s1.totalnumberofclasses++;
           o.writeObject(s1);
           continue;
                    }
                   else if(choice==2)
          {
           s1.totalnumberofclasses++;
           o.writeObject(s1);
           continue;
       }

    }

}
           catch(StreamCorruptedException e)
           {
               try{
                   FileInputStream fi=new FileInputStream(new File("Student.bin"));
                   FileOutputStream f= new FileOutputStream(new File("temp.bin"));
                   ObjectOutputStream o=new ObjectOutputStream(f);
                   ObjectInputStream oi=new ObjectInputStream(fi);
                   while(true)
                   {

                       student s1=(student)oi.readObject();
                       System.out.println("Name="+s1.name+"Roll no:"+s1.rollno);
                       System.out.println("press <1> to mark as present\nPress <2> to mark as absent");
                       choice=input.nextInt();
                       if(choice==1)
                       {
                           s1.totalclassesattended++;
                           s1.totalnumberofclasses++;
                       }
                       else if(choice==2)
                       {
                           s1.totalnumberofclasses++;

                       }
                        o.writeObject(s1);
                   }

               }
               catch(EOFException q)
               {
                   try{
                       FileOutputStream f=new FileOutputStream( new File("Student.bin"));
                       ObjectOutputStream o=new ObjectOutputStream(f);
                       FileInputStream fi=new FileInputStream(new File("temp.bin"));
                       ObjectInputStream oi=new ObjectInputStream(fi);
                       while(true)
                       {

                           o.writeObject(oi.readObject());
                       }
                   }
                   catch(EOFException t)
                   {
                       System.out.println("Files updated");
                   }
                   catch(Exception t)
                   {
                       System.out.println("an error has occured"+t);
                   }
               }
               catch(Exception q)
               {
                   System.out.println("An error has occured2"+q);
               }
           }
          catch(EOFException e)     //need not have dont this if f.renameto() had worked
           {
              try{
                  FileOutputStream f=new FileOutputStream( new File("Student.bin"));
                  ObjectOutputStream o=new ObjectOutputStream(f);
                  FileInputStream fi=new FileInputStream(new File("temp.bin"));
                  ObjectInputStream oi=new ObjectInputStream(fi);
                  while(true)
                  {

                      o.writeObject(oi.readObject());
                  }
              }
              catch(EOFException q)
              {
                  System.out.println("Files updated");
              }
              catch (Exception q)
              {
                  System.out.println("an error has occured2"+q);
              }
           }
           catch(Exception e)
           {
               System.out.println("An error has occured1"+e);
           }
       }
       else if(choice==2)
       menu();
    }


    public static void admin()      //menu to show the admin operations
    {
        Scanner input =new Scanner(System.in);
        System.out.println("1.Create user\n2.Add attendance");
        int choice;
        choice=input.nextInt();
        if(choice==1)
         {
            createstudent();
        }
        if(choice==2)
            addattendance();



    }
    public  static void stu() {                  //searches for the given roll number

        Scanner input = new Scanner(System.in);
        System.out.println("Enter Roll no");
        String rno;
        rno = input.nextLine();
        try {
            FileInputStream fi = new FileInputStream(new File("Student.bin"));

            while (true) {
                ObjectInputStream oi = new ObjectInputStream(fi);
                System.out.println("1");
                student s1 = (student) oi.readObject();
                if (rno.equals(s1.rollno)) {
                    System.out.println("2");
                    System.out.println("Match found");
                    stufunc(s1);
                    break;
                }

            }


        } catch (EOFException e) {
            System.out.println("MAtch not found\n please try again");
            menu();

        }
        catch (StreamCorruptedException e)
        {
            try {
                FileInputStream fi = new FileInputStream(new File("Student.bin"));
                ObjectInputStream oi = new ObjectInputStream(fi);
                while (true) {
                    System.out.println("1");
                    student s1 = (student) oi.readObject();
                    if (rno.equals(s1.rollno)) {
                        System.out.println("2");
                        System.out.println("Match found");
                        stufunc(s1);
                        break;
                    }
                }
            }
            catch ( EOFException t)
            {
               System.out.println("Match Not found\n Please try again");
               menu();

            }
            catch (Exception t)
            {
                System.out.println("An error has occured"+t);
            }

        }
        catch (Exception e) {
            System.out.println("An error has occured " + e);
            menu();
        }

    }

    public static void menu(){      //SHOWS THE MENU

        Scanner input =new Scanner(System.in);
        System.out.println("Student Management System");
        System.out.println("Enter your Choice:");
        System.out.println("1.Admin\n2.Student");
        int choice;
        choice=input.nextInt();
        if(choice==1)
            admin();
        else if(choice==2)
            stu();
        else
        {
            System.out.println("Invalid input please try again");
            menu();
        }
    }

    public static void main(String []args)
    {
        menu();        //calls menu
    }

}






