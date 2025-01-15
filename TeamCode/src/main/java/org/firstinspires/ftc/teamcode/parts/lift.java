package org.firstinspires.ftc.teamcode.parts;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;

public class lift {
    private DcMotor viper1;
    private DcMotor viper2;
    public int extindere = 0;
    public lift(HardwareMap hardwareMap)
    {
        viper1 = hardwareMap.get(DcMotor.class, "viper1");
        viper2 = hardwareMap.get(DcMotor.class, "viper2");

        viper1.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        viper2.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        viper1.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        viper2.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        viper1.setDirection(DcMotorSimple.Direction.REVERSE);
        viper2.setDirection(DcMotorSimple.Direction.REVERSE);
    }
    public void ascend(double power, int pozitie)
    {
        if(extindere<=0) extindere = 0;
        if(pozitie==0) {
            extindere -= 15 * power;
            if (extindere > 4015) extindere = 4015;
        }
        else
        {
            extindere = pozitie;
        }
        viper1.setTargetPosition(extindere);
        viper1.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        viper1.setPower(1);

        viper2.setTargetPosition(extindere);
        viper2.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        viper2.setPower(1);
    }
}
