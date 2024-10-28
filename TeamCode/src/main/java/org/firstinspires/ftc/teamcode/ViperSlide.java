package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;

@TeleOp
@Disabled
public class ViperSlide extends OpMode {
    private DcMotor slide = null;

    @Override
    public void init() {
        slide = hardwareMap.get(DcMotor.class, "Slide");
    }

    @Override
    public void loop() {
        slide.setPower(gamepad1.left_stick_y);
    }
}
