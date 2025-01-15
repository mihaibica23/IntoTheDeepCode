package org.firstinspires.ftc.teamcode.teleop;

import static org.firstinspires.ftc.robotcore.external.BlocksOpModeCompanion.telemetry;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.parts.driveTrain;
import org.firstinspires.ftc.teamcode.parts.lift;
import org.firstinspires.ftc.teamcode.parts.servoes;


@TeleOp(name = "Unit_02")
public class main extends LinearOpMode {
    driveTrain masina;
    lift viper;
    servoes servoes;
    @Override
    public void runOpMode() throws InterruptedException {
        masina = new driveTrain(hardwareMap);
        viper = new lift(hardwareMap);
        servoes = new servoes(hardwareMap);

        //Variabile
        boolean last_cycle=false, curr_cycle=false;
        ElapsedTime timer = new ElapsedTime();

        //innit
        servoes.deschidereGheara(1);
        servoes.pozitieBrat(0,11);

        waitForStart();
        while(opModeIsActive() && !isStopRequested())
        {
            // Driver 1
            masina.drive(-gamepad1.right_stick_y, gamepad1.right_stick_x, gamepad1.right_trigger - gamepad1.left_trigger);

            //Driver 2
            viper.ascend(gamepad2.right_stick_y,0);
            servoes.controlExtender(gamepad2.left_stick_y);
            telemetry.addData("Power: ", gamepad2.left_stick_y);
            telemetry.addData("Stanga: ", Math.abs((1+gamepad2.left_stick_y)/2));
            telemetry.addData("Dreapta: ", Math.abs(gamepad2.left_stick_y/2));
            telemetry.update();
            //
            last_cycle = curr_cycle;
            curr_cycle = gamepad2.y;
            if(curr_cycle && !last_cycle)
            {
                servoes.deschidereGheara(1);
            }

            last_cycle = curr_cycle;
            curr_cycle = gamepad2.dpad_up;
            if(curr_cycle && !last_cycle)
            {
                timer.reset();
                while(timer.seconds() <=0.4)
                {
                    if(timer.seconds()>=0.1 && timer.seconds()<0.2)
                    {
                        servoes.deschidereGheara(0);
                    }
                    if(timer.seconds()>=0.3 && timer.seconds()<0.4)
                    {
                        servoes.pozitieBrat(0,1);
                        break;
                    }
                }
            }

            last_cycle = curr_cycle;
            curr_cycle = gamepad2.dpad_down;
            if(curr_cycle && !last_cycle)
            {
                viper.ascend(1,400);
                servoes.deschidereGheara(0.7);
                servoes.pozitieBrat(0.1,0.35);
            }

        }
    }
}
