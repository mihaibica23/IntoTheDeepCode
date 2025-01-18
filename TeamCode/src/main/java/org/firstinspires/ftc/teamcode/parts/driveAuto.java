package org.firstinspires.ftc.teamcode.parts;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;

public class driveAuto extends LinearOpMode {
    @Override
    public void runOpMode()
    {

    }
    private DcMotor fataStanga, fataDreapta, spateStanga, spateDreapta;
    private boolean isStopRequested;
    public driveAuto(HardwareMap hardwareMap)
    {
        fataStanga = hardwareMap.get(DcMotor.class, "fataStanga");
        fataDreapta = hardwareMap.get(DcMotor.class, "fataDreapta");
        spateStanga = hardwareMap.get(DcMotor.class, "spateStanga");
        spateDreapta = hardwareMap.get(DcMotor.class, "spateDreapta");

        fataStanga.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        fataDreapta.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        spateStanga.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        spateDreapta.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        fataStanga.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        fataDreapta.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        spateStanga.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        spateDreapta.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        fataDreapta.setDirection(DcMotorSimple.Direction.REVERSE);
        spateDreapta.setDirection(DcMotorSimple.Direction.REVERSE);
    }
    public void drive(int posFS, int posFD, int posSS, int posSD, double speed)
    {
        if(fataStanga.getCurrentPosition()!=posFS) fataStanga.setTargetPosition(posFS);
        if(fataDreapta.getCurrentPosition()!=posFD) fataDreapta.setTargetPosition(posFD);
        if(spateStanga.getCurrentPosition()!=posSS) spateStanga.setTargetPosition(posSS);
        if(spateDreapta.getCurrentPosition()!=posSD) spateDreapta.setTargetPosition(posSD);

        fataStanga.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        fataDreapta.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        spateStanga.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        spateDreapta.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        do {
            if(isStopRequested())
            {
                break;
            }
            fataStanga.setPower(speed);
            fataDreapta.setPower(speed);
            spateStanga.setPower(speed);
            spateDreapta.setPower(speed);
            if(fataStanga.getCurrentPosition() == posFS) break;
            if(fataDreapta.getCurrentPosition() == posFD) break;
            if(spateStanga.getCurrentPosition() == posSS) break;
            if(spateDreapta.getCurrentPosition() == posSD) break;

        }while(fataStanga.getCurrentPosition() != posFS && fataDreapta.getCurrentPosition() != posFD && spateStanga.getCurrentPosition() != posSS && spateDreapta.getCurrentPosition()!=posSD);

        fataStanga.setPower(0);
        fataDreapta.setPower(0);
        spateStanga.setPower(0);
        spateDreapta.setPower(0);
    }
}
