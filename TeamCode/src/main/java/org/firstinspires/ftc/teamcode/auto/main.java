
package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;
@Autonomous(name="DummyPlugSpecimen", group="Linear OpMode")

public class DummmyPlugSpecimen extends LinearOpMode {

    // Declare OpMode members for each of the 4 motors.
    //initializare motoare DC
    private DcMotor fataStanga;
    private DcMotor fataDreapta;
    private DcMotor spateStanga;
    private DcMotor spateDreapta;
    private DcMotor viper1;
    private DcMotor viper2;

    //initializare motoare Servo
    private Servo cuvaDreapta;
    private Servo cuvaStanga;
    private CRServo intakeStanga;
    private CRServo intakeDreapta;
    private Servo gheara;
    private Servo articulatieJos;
    private Servo articulatieSus;
    private CRServo extenderDreapta;
    private CRServo extenderStanga;

    @Override
    public void runOpMode() {

        // Initialize the hardware variables. Note that the strings used here must correspond
        // to the names assigned during the robot configuration step on the DS or RC devices.
        fataStanga = hardwareMap.get(DcMotor.class, "fataStanga");
        fataDreapta = hardwareMap.get(DcMotor.class, "fataDreapta");
        spateStanga = hardwareMap.get(DcMotor.class, "spateStanga");
        spateDreapta = hardwareMap.get(DcMotor.class, "spateDreapta");
        viper1 = hardwareMap.get(DcMotor.class, "viper1");
        viper2 = hardwareMap.get(DcMotor.class, "viper2");

        // Initializare Motoare Servo
        gheara = hardwareMap.get(Servo.class, "gheara");
        cuvaDreapta = hardwareMap.get(Servo.class, "cuvaDreapta");
        cuvaStanga = hardwareMap.get(Servo.class, "cuvaStanga");
        intakeStanga = hardwareMap.get(CRServo.class, "intakeStanga");
        intakeDreapta = hardwareMap.get(CRServo.class, "intakeDreapta");
        articulatieJos = hardwareMap.get(Servo.class, "articulatieJos");
        articulatieSus = hardwareMap.get(Servo.class, "articulatieSus");
        extenderStanga = hardwareMap.get(CRServo.class, "extenderStanga");
        extenderDreapta = hardwareMap.get(CRServo.class, "extenderDreapta");

        //Directie adjustment
        fataDreapta.setDirection(DcMotor.Direction.REVERSE);
        spateDreapta.setDirection(DcMotor.Direction.REVERSE);

        //Timer
        ElapsedTime timer = new ElapsedTime();
        gheara.setPosition(0);
        waitForStart();

        // run until the end of the match (driver presses STOP)
        if (opModeIsActive()){
            while (opModeIsActive()) {
                timer.reset();
                while(timer.seconds()<10)
                {
                /*if(timer.seconds()>=0 && timer.seconds()<0.7)
                {
                fataStanga.setPower(1);
                fataDreapta.setPower(1);
                spateStanga.setPower(1);
                spateDreapta.setPower(1);
                }*/
                    if(timer.seconds()>=0 && timer.seconds()<0.6)
                    {
                        fataStanga.setPower(1);
                        fataDreapta.setPower(1);
                        spateStanga.setPower(1);
                        spateDreapta.setPower(1);
                        if(timer.seconds()>=0 && timer.seconds()<0.5)
                        {
                            articulatieSus.setPosition(1);
                            articulatieJos.setPosition(0);
                        }
                    }
                    else
                    {
                        fataStanga.setPower(0);
                        fataDreapta.setPower(0);
                        spateStanga.setPower(0);
                        spateDreapta.setPower(0);
                    }
                    if(timer.seconds()>=2 && timer.seconds()<2.8)
                    {
                        viper1.setPower(-1);
                        viper2.setPower(-1);
                    }
                    else
                    {
                        viper1.setPower(0);
                        viper2.setPower(0);
                    }
                    if(timer.seconds()>=2.9 && timer.seconds()<3)
                    {
                        gheara.setPosition(1);
                    }
                    if(timer.seconds()>=3.1 && timer.seconds()<3.9)
                    {
                        viper1.setPower(1);
                        viper2.setPower(1);
                    }
                    else if (timer.seconds>=3.1)
                    {
                        viper1.setP
                    }


                }

            }}}}
