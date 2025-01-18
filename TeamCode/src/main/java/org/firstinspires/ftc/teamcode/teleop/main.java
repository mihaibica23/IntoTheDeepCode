package org.firstinspires.ftc.teamcode.teleop;

import static org.firstinspires.ftc.robotcore.external.BlocksOpModeCompanion.telemetry;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.parts.driveTrain;
import org.firstinspires.ftc.teamcode.parts.lift;
import org.firstinspires.ftc.teamcode.parts.servoes;


@TeleOp(name = "Unit_02")
public class main extends LinearOpMode {
    driveTrain masina;
    lift viper;
    servoes servoes;
    public static double artJos, artSus;
    private DcMotor viper1, viper2;
    private Servo cuvaStanga, cuvaDreapta;

    boolean cuva=true;
    boolean hang = true;
    @Override
    public void runOpMode() throws InterruptedException {
        masina = new driveTrain(hardwareMap);
        viper = new lift(hardwareMap);
        servoes = new servoes(hardwareMap);
        viper1 = hardwareMap.get(DcMotor.class,"viper1");
        viper2 = hardwareMap.get(DcMotor.class,"viper2");
        cuvaStanga=hardwareMap.get(Servo.class, "cuvaStanga");
        cuvaDreapta=hardwareMap.get(Servo.class, "cuvaDreapta");
        //Variabile
        boolean last_cycle=false, curr_cycle=false, last_circle=false, curr_circle=false,last_right=false, curr_right=false, last_patrat=false, curr_patrat=false;
        ElapsedTime timer = new ElapsedTime();

        //innit
        servoes.deschidereGheara(0);
        servoes.pozitieBrat(0.2,0.45 );
        servoes.pullUp(0);

        servoes.cuva(0);
        waitForStart();
        while(opModeIsActive() && !isStopRequested())
        {
            // Driver 1


            masina.drive(-gamepad1.right_stick_y, gamepad1.right_stick_x, gamepad1.right_trigger - gamepad1.left_trigger);

            //Driver 2
            viper.ascend(gamepad2.right_stick_y,0);
            servoes.controlExtender(gamepad2.left_stick_y);
            servoes.aspirator(gamepad2.right_trigger - gamepad2.left_trigger);

            telemetry.addData("Viper1: ", viper1.getCurrentPosition());
            telemetry.addData("Viper2: ", viper2.getCurrentPosition());
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
                        servoes.pozitieBrat(0,0.95);
                        break;
                    }
                }
            }

            last_cycle = curr_cycle;
            curr_cycle = gamepad2.dpad_down;
            if(curr_cycle && !last_cycle)
            {
                servoes.deschidereGheara(1);
                servoes.pozitieBrat(0.2,0.45 );
                servoes.cuva(0);
            }

            last_circle = curr_circle;
            curr_circle = gamepad2.b;
            if(curr_circle && !last_circle)
            {
                if(cuva)
                {
                    servoes.cuva(0.78);
                    cuva=false;
                    telemetry.addLine("Jos");
                }
                else
                {
                    servoes.cuva(0.60);
                    telemetry.addLine("Sus");
                    cuva=true;
                }
                telemetry.update();
            }
            last_right = curr_right;
            curr_right = gamepad2.dpad_right;
            if(curr_right && !last_right)
            {
                if(hang)
                {
                    servoes.pullUp(0);
                    hang=false;
                }
                else
                {
                    servoes.pullUp(0.5);
                    hang=true;
                }
            }
            last_patrat = curr_patrat;
            curr_patrat = gamepad2.square;
            if(curr_patrat && !last_patrat)
            {
                servoes.deschidereGheara(1);
                timer.reset();
                while(timer.seconds()<=3)
                {
                    if(timer.seconds()>=0 && timer.seconds()<0.1)
                    {

                        servoes.pozitieBrat(0.34, 0.39);
                        servoes.cuva(0.73);
                    }
                    if(timer.seconds()>=1.1) {servoes.deschidereGheara(0); }
                    if(timer.seconds()>=1.5){ servoes.pozitieBrat(0.34,1); servoes.cuva(0.75);break;}
                }
            }

        }
    }
}
