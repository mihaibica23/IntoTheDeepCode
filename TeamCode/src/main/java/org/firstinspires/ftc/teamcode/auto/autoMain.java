package org.firstinspires.ftc.teamcode.auto;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.parts.driveAuto;
import org.firstinspires.ftc.teamcode.parts.lift;
import org.firstinspires.ftc.teamcode.parts.servoes;

@Autonomous(name = "DummyPlugRight")

public class autoMain extends LinearOpMode {
    private DcMotor fataStanga, fataDreapta, spateDreapta, spateStanga;
    private Servo extenderStanga, extenderDreapta, gheara;

    private DcMotor viper1, viper2;
    @Override
    public void runOpMode() throws InterruptedException {

        fataStanga = hardwareMap.get(DcMotor.class, "fataStanga");
        fataDreapta = hardwareMap.get(DcMotor.class, "fataDreapta");
        spateStanga = hardwareMap.get(DcMotor.class, "spateStanga");
        spateDreapta = hardwareMap.get(DcMotor.class, "spateDreapta");
        viper1 = hardwareMap.get(DcMotor.class, "viper1");
        viper2 = hardwareMap.get(DcMotor.class, "viper2");
        gheara = hardwareMap.get(Servo.class, "gheara");

        extenderStanga = hardwareMap.get(Servo.class, "extenderStanga");
        extenderDreapta = hardwareMap.get(Servo.class, "extenderDreapta");

        fataStanga.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        fataDreapta.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        spateStanga.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        spateDreapta.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        viper1.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        viper2.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        fataStanga.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        fataDreapta.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        spateStanga.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        spateDreapta.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        viper1.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        viper2.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        fataDreapta.setDirection(DcMotorSimple.Direction.REVERSE);
        spateDreapta.setDirection(DcMotorSimple.Direction.REVERSE);
        viper1.setDirection(DcMotorSimple.Direction.REVERSE);
        viper2.setDirection(DcMotorSimple.Direction.REVERSE);

        double fata, glisare, rotate;
        int extindere = 0;
        ElapsedTime timer = new ElapsedTime();

        driveAuto drive;
        drive = new driveAuto(hardwareMap);

        lift lift;
        lift = new lift(hardwareMap);
        extenderDreapta.setPosition(0);
        extenderStanga.setPosition(1);
        servoes servoes;
        servoes = new servoes(hardwareMap);
        servoes.cuva(0);
        waitForStart();
        servoes.deschidereGheara(0);
        //servoes.pozitieBrat(0.4,0.5);
        servoes.pozitieBrat(0,0.90);
        boolean back1=false, back2=false, back3=false;
        boolean forward1 = false, forward2 = false;
        boolean ridicare_viper1=false, ridicare_viper2=false;
        boolean rotire_servo1 = false, rotire_servo2=false, rotire_servo3=false;
        boolean glisare1=false, glisare2=false, glisare3=false;

        timer.reset();
        while(opModeIsActive() && !isStopRequested())
        {
            if(!back1) drive.drive(-1600, -1600, -1600, -1600, 0.4);
            if(fataStanga.getCurrentPosition()<=-1590 && fataDreapta.getCurrentPosition()<=-1590 && spateStanga.getCurrentPosition()<=-1590 && spateDreapta.getCurrentPosition()<=-1590)
            {
                back1=true;
            }
            if(back1 && !ridicare_viper1)
            {
                lift.ascend(1,1600);
                if(viper1.getCurrentPosition()>=1600 && viper2.getCurrentPosition()>=1600)
                {
                    ridicare_viper1=true;
                    servoes.deschidereGheara(1);
                }
            }
            if(ridicare_viper1 && !rotire_servo1)
            {
                if(gheara.getPosition() == 1) lift.ascend(0,1);
                if(viper1.getCurrentPosition()<=30 && viper2.getCurrentPosition()<=30)
                {

                    servoes.deschidereGheara(1);
                    servoes.pozitieBrat(0.2,0.45 );

                    rotire_servo1 = true;
                }
            }
            if(rotire_servo1 && !forward1)
            {
                drive.drive(-500, -500, -500, -500,0.4);
                if(fataStanga.getCurrentPosition()<=-485 && fataDreapta.getCurrentPosition()<=-485 && spateStanga.getCurrentPosition()<=-485 && spateDreapta.getCurrentPosition()<=-485)
                {
                    forward1=true;
                }
            }
            if(forward1 && !glisare1)
            {
                drive.drive(-3000, 2000, 2000, -3000,0.4);
                if(fataStanga.getCurrentPosition()<=-2980 && fataDreapta.getCurrentPosition()>=1480 && spateStanga.getCurrentPosition()>=1480 && spateDreapta.getCurrentPosition()<=-2980)
                {
                    glisare1=true;
                }
            }
            if(glisare1 && !forward2)
            {
                drive.drive(-2600, 2400, 2400, -2600,0.15);

                if(fataStanga.getCurrentPosition()>=-2620 && fataDreapta.getCurrentPosition()>=2380 && spateStanga.getCurrentPosition()>=2380 && spateDreapta.getCurrentPosition()>=-2620)
                {
                    forward2=true;
                }
            }
            if(forward2 && !back2)
            {
                servoes.deschidereGheara(0);
                servoes.pozitieBrat(0,0.92);
                drive.drive(-3000, 2000, 2000, -3000,0.2);
                if(fataStanga.getCurrentPosition()<=-2980 && fataDreapta.getCurrentPosition()>=1480 && spateStanga.getCurrentPosition()>=1480 && spateDreapta.getCurrentPosition()<=-2980)
                {
                    back2=true;
                }
            }
            if(back2 && !glisare2)
            {
                drive.drive(-400, -600, -600, -400,0.4);
                telemetry.addData("FS:", fataStanga.getCurrentPosition());
                telemetry.addData("FD:", fataDreapta.getCurrentPosition());
                telemetry.update();
                if(fataStanga.getCurrentPosition()>=-430 && fataDreapta.getCurrentPosition()<=-580 && spateStanga.getCurrentPosition()<=-580 && spateDreapta.getCurrentPosition()>=-430)
                {
                    glisare2=true;
                }
            }
            /*if(glisare2 && !rotire_servo2)
            {
                servoes.pozitieBrat(0,0.9);
                rotire_servo2 = true;
            }*/
            if(glisare2 && !back3)
            {
                drive.drive(-1850, -1850, -1850, -1850, 0.4);
                telemetry.addData("FS:", fataStanga.getCurrentPosition());
                telemetry.addData("FD:", fataDreapta.getCurrentPosition());
                telemetry.update();
                if(fataStanga.getCurrentPosition()<=-1640 && fataDreapta.getCurrentPosition()<=-1640 && spateStanga.getCurrentPosition()<=-1640 && spateDreapta.getCurrentPosition()<=-1640)
                {
                    back3=true;
                }
            }
            /*if(back3 && !glisare3)
            {
                drive.drive(-1600, -2100, -2100, -1600, 0.4);
                if(fataStanga.getCurrentPosition()>=-1680 && fataDreapta.getCurrentPosition()<=-2020 && spateStanga.getCurrentPosition()>=-1680 && spateDreapta.getCurrentPosition()<=-2020)
                {
                    glisare3=true;
                }
            }*/
            if(back3 && !ridicare_viper2)
            {
                lift.ascend(1,1600);
                if(viper1.getCurrentPosition()>=1600 && viper2.getCurrentPosition()>=1600)
                {
                    ridicare_viper2=true;
                    servoes.deschidereGheara(1);
                }
            }
            if(ridicare_viper2 && !rotire_servo3)
            {
                if(gheara.getPosition() == 1) lift.ascend(0,1);
                if(viper1.getCurrentPosition()<=30 && viper2.getCurrentPosition()<=30)
                {
                    servoes.deschidereGheara(1);
                    servoes.pozitieBrat(0,0.45 );

                    rotire_servo3 = true;
                }
            }


            //1605
        }
    }
}
