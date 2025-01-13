package org.firstinspires.ftc.teamcode.teleop;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.parts.driveTrain;
@TeleOp(name = "Test")
public class test extends LinearOpMode {

    private driveTrain masina;

    @Override
    public void runOpMode() throws InterruptedException {

        masina = new driveTrain(hardwareMap);

        waitForStart();
        while(opModeIsActive() && !isStopRequested() ){
            masina.vroom(-gamepad1.left_stick_y, gamepad1.left_stick_x, gamepad1.right_trigger-gamepad1.left_trigger);
        }


    }
}
