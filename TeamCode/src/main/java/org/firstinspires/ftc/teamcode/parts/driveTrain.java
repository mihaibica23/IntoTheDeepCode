package org.firstinspires.ftc.teamcode.parts;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;

public class driveTrain {
    private DcMotor fataStanga;
    private DcMotor fataDreapta;
    private DcMotor spateStanga;
    private DcMotor spateDreapta;

    public driveTrain(HardwareMap hardwareMap){
        fataStanga = hardwareMap.get(DcMotor.class, "fataStanga");
        fataDreapta = hardwareMap.get(DcMotor.class, "fataDreapta");
        spateStanga = hardwareMap.get(DcMotor.class, "spateStanga");
        spateDreapta = hardwareMap.get(DcMotor.class, "spateDreapta");

        fataDreapta.setDirection(DcMotor.Direction.REVERSE);
        spateDreapta.setDirection(DcMotor.Direction.REVERSE);

        fataDreapta.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        fataStanga.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        spateDreapta.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        spateStanga.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
    }

    public void vroom(double forward, double strafe, double rotate) {
        double denominator = Math.max(Math.abs(forward) + Math.abs(strafe) + Math.abs(rotate), 1);
        double frontLeftPower = (forward + strafe + rotate) / denominator;
        double backLeftPower = (forward - strafe + rotate) / denominator;
        double frontRightPower = (forward - strafe - rotate) / denominator;
        double backRightPower = (forward + strafe - rotate) / denominator;

        fataStanga.setPower(frontLeftPower);
        spateStanga.setPower(backLeftPower);
        fataDreapta.setPower(frontRightPower);
        spateDreapta.setPower(backRightPower);
    }

}

