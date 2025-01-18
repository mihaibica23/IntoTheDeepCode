package org.firstinspires.ftc.teamcode.parts;

import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

public class servoes {
    private Servo gheara, articulatieJos, articulatieSus, extenderStanga, extenderDreapta, cuvaStanga, cuvaDreapta,hang;
    private CRServo intake;
    public servoes(HardwareMap hardwareMap)
    {
        gheara=hardwareMap.get(Servo.class, "gheara");
        articulatieJos=hardwareMap.get(Servo.class, "articulatieJos");
        articulatieSus=hardwareMap.get(Servo.class, "articulatieSus");
        extenderStanga=hardwareMap.get(Servo.class, "extenderStanga");
        extenderDreapta=hardwareMap.get(Servo.class, "extenderDreapta");
        cuvaStanga=hardwareMap.get(Servo.class, "cuvaStanga");
        cuvaDreapta=hardwareMap.get(Servo.class, "cuvaDreapta");
        intake = hardwareMap.get(CRServo.class, "intake");
        hang = hardwareMap.get(Servo.class, "hang");

    }

    public void deschidereGheara(double factor)
    {
        gheara.setPosition(factor);
    }
    public void pozitieBrat(double artJos, double artSus)
    {
        articulatieJos.setPosition(artJos);
        articulatieSus.setPosition(artSus);
    }
    public void controlExtender(double power)
    {
        extenderStanga.setPosition(Math.abs((1+power)/2));
        extenderDreapta.setPosition(Math.abs((-power + 1)/2));

    }
    public void aspirator(double power)
    {
        intake.setPower(power);
    }
    public void cuva(double unghi)
    {
        cuvaStanga.setPosition(unghi);
        cuvaDreapta.setPosition(1-unghi);
    }
    public void pullUp(double unghi)
    {
        hang.setPosition(unghi);
    }
}