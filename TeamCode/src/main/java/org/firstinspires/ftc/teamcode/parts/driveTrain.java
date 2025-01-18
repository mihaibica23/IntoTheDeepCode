package org.firstinspires.ftc.teamcode.parts;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;

public class driveTrain
{
    private DcMotor fataStanga;
    private DcMotor fataDreapta;
    private DcMotor spateStanga;
    private DcMotor spateDreapta;

    public driveTrain(HardwareMap hardwareMap)
    {
        fataStanga = hardwareMap.get(DcMotor.class, "fataStanga");
        fataDreapta = hardwareMap.get(DcMotor.class, "fataDreapta");
        spateStanga = hardwareMap.get(DcMotor.class, "spateStanga");
        spateDreapta = hardwareMap.get(DcMotor.class, "spateDreapta");

        fataDreapta.setDirection(DcMotorSimple.Direction.REVERSE);
        spateDreapta.setDirection(DcMotorSimple.Direction.REVERSE);


    }

    public void drive(double fata, double glisare, double rotate)
    {
        double denom = Math.max(Math.abs(fata) + Math.abs(glisare) + Math.abs(rotate), 1);
        double frontLeftPower = (fata + glisare + rotate) / denom;
        double frontRightPower = (fata - glisare - rotate) / denom;
        double backLeftPower = (fata - glisare + rotate) / denom;
        double backRightPower = (fata + glisare - rotate) / denom;

        fataStanga.setPower(frontLeftPower);
        fataDreapta.setPower(frontRightPower);
        spateStanga.setPower(backLeftPower);
        spateDreapta.setPower(backRightPower);
    }
}
