package org.firstinspires.ftc.teamcode.parts;

import static org.firstinspires.ftc.robotcore.external.BlocksOpModeCompanion.telemetry;

import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

public class servoes {
    private Servo gheara, articulatieJos, articulatieSus, extenderStanga, extenderDreapta;
    public servoes(HardwareMap hardwareMap)
    {
        gheara=hardwareMap.get(Servo.class, "gheara");
        articulatieJos=hardwareMap.get(Servo.class, "articulatieJos");
        articulatieSus=hardwareMap.get(Servo.class, "articulatieSus");
        extenderStanga=hardwareMap.get(Servo.class, "extenderStanga");
        extenderDreapta=hardwareMap.get(Servo.class, "extenderDreapta");

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
}
