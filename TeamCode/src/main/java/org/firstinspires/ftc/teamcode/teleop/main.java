package org.firstinspires.ftc.teamcode.teleop;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;

import org.firstinspires.ftc.teamcode.parts.driveTrain;
import org.firstinspires.ftc.teamcode.parts.lift;

@TeleOp(name = "Unit_02")
public class main extends LinearOpMode {
    driveTrain masina;
    lift viper;
    @Override
    public void runOpMode() throws InterruptedException {
        masina = new driveTrain(hardwareMap);
        viper = new lift(hardwareMap);
        waitForStart();
        while(opModeIsActive() && !isStopRequested())
        {
            // Driver 1
            masina.drive(-gamepad1.right_stick_y, gamepad1.right_stick_x, gamepad1.right_trigger - gamepad1.left_trigger);

            //Driver 2
            viper.ascend(gamepad2.right_stick_y);
        }
    }
}
