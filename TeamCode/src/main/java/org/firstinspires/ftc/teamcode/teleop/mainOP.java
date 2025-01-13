package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

import org.firstinspires.ftc.teamcode.parts.driveTrain;

@TeleOp(name="unit_02")

public class Unit_02 extends LinearOpMode {

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
    boolean cuva=true;

    private driveTrain drive;

    @Override
    public void runOpMode()
    {
        drive = new driveTrain(hardwareMap);

        // Initializae Motoare Dc
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
        boolean last_b = false, ciclu_b = false, last_x=false, ciclu_x=false, last_y=false,ciclu_y=false, last_down=false,ciclu_down=false, last_up=false,ciclu_up=false;
        cuvaDreapta.setPosition(0.4);
        cuvaStanga.setPosition(0.6);
        timer.reset();
        while(timer.seconds() < 1.5)
        {
            if(timer.seconds() > 0.1)
            {
                articulatieSus.setPosition(1);
            }

            if(timer.seconds() > 1)
            {
                articulatieJos.setPosition(0);
                break;
            }
        }

        waitForStart();
        if (opModeIsActive())
        {

            while (opModeIsActive())
            {
                //cuvaDreapta.setPosition(0.45);
                //cuvaStanga.setPosition(0.55);
                //Driver 1

                // Mers Fata - Spate

                fataStanga.setPower(gamepad1.left_stick_y);
                fataDreapta.setPower(gamepad1.left_stick_y);
                spateStanga.setPower(gamepad1.left_stick_y);
                spateDreapta.setPower(gamepad1.left_stick_y);

                //Glisare Dreapta
                fataStanga.setPower(-1*gamepad1.right_trigger);
                fataDreapta.setPower(gamepad1.right_trigger);
                spateStanga.setPower(gamepad1.right_trigger);
                spateDreapta.setPower(-1*gamepad1.right_trigger);

                //Glisare Stanga
                fataStanga.setPower(gamepad1.left_trigger);
                fataDreapta.setPower(-1*gamepad1.left_trigger);
                spateStanga.setPower(-1*gamepad1.left_trigger);
                spateDreapta.setPower(gamepad1.left_trigger);

                //Intoarcere 360
                fataStanga.setPower(-1*gamepad1.right_stick_x);
                fataDreapta.setPower(gamepad1.right_stick_x);
                spateStanga.setPower(-1*gamepad1.right_stick_x);
                spateDreapta.setPower(gamepad1.right_stick_x);

                //Driver 2

                //Aspirator
                intakeStanga.setPower(gamepad2.right_trigger);
                intakeDreapta.setPower(-1*gamepad2.right_trigger);

                while(gamepad2.a)
                {
                    intakeStanga.setPower(-1*gamepad2.right_trigger);
                    intakeDreapta.setPower(gamepad2.right_trigger);
                }
                //intakeStanga.setPower(-1*gamepad2.left_trigger);
                //intakeDreapta.setPower(gamepad2.left_trigger);

                //Extender
                extenderStanga.setPower(-1*gamepad2.left_stick_y);
                extenderDreapta.setPower(gamepad2.left_stick_y);

                //Viper
                viper1.setPower(gamepad2.right_stick_y);
                viper2.setPower(gamepad2.right_stick_y);

                //Gheara
                /*last_b = ciclu_b;
                ciclu_b = gamepad2.a;
                if(ciclu_b && !last_b)
                {
                    timer.reset();
                    while(timer.seconds() <= 2)
                    {
                        articulatieSus.setPower(1);
                    }
                    articulatieSus.setPower(0);
                }*/
                last_b = ciclu_b;
                ciclu_b = gamepad2.b;
                telemetry.addData("Cuva Dreaptaa: ", cuvaDreapta.getPosition());
                telemetry.addData("Cuva Stanga: ", cuvaStanga.getPosition());


                if(ciclu_b && !last_b)
                {
                    telemetry.addData("Position: ", cuvaDreapta.getPosition());
                    if(cuva==true)
                    {
                        cuvaDreapta.setPosition(1);
                        cuvaStanga.setPosition(0);
                        cuva=false;
                    }
                    else
                    {
                        cuvaDreapta.setPosition(0.4);
                        cuvaStanga.setPosition(0.6);
                        cuva=true;
                    }
                }
                last_x = ciclu_x;
                ciclu_x = gamepad2.x;
                if(ciclu_x && !last_x)
                {
                    cuvaDreapta.setPosition(0.45);
                    cuvaStanga.setPosition(0.55);
                    articulatieSus.setPosition(0.4);
                    articulatieJos.setPosition(0.49);
                    gheara.setPosition(1);
                    timer.reset();
                    while(timer.seconds() < 3)
                    {
                        if(timer.seconds() > 1)
                        {
                            gheara.setPosition(0);
                        }

                        if(timer.seconds() > 2)
                        {
                            articulatieSus.setPosition(1);
                            break;
                        }
                    }
                    articulatieJos.setPosition(0);
                    cuvaDreapta.setPosition(0.4);
                    cuvaStanga.setPosition(0.6);
                    //gheara.setPosition(1);
                }

                last_y = ciclu_y;
                ciclu_y = gamepad2.y;
                if(ciclu_y && !last_y)
                {
                    gheara.setPosition(1);
                }
                last_down = ciclu_down;
                ciclu_down = gamepad2.dpad_down;
                if(ciclu_down && !last_down)
                {
                    articulatieSus.setPosition(0.35);
                    articulatieJos.setPosition(0.1);
                    gheara.setPosition(1);
                }
                last_up = ciclu_up;
                ciclu_up = gamepad2.dpad_up;
                if(ciclu_up && !last_up)
                {
                    timer.reset();
                    while(timer.seconds() < 2)
                    {
                        if(timer.seconds() > 0.5)
                        {
                            gheara.setPosition(0);
                        }

                        if(timer.seconds() > 1)
                        {
                            articulatieSus.setPosition(1);
                            break;
                        }
                        articulatieJos.setPosition(0);
                    }
                }
                telemetry.update();
            }
        }

    }
}