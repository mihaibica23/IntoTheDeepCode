package org.firstinspires.ftc.teamcode.parts;

import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

public class servoes {
    private Servo gheara, articulatieJos, articulatieSus;
    public servoes(HardwareMap hardwareMap)
    {
        gheara=hardwareMap.get(Servo.class, "gheara");
        articulatieJos=hardwareMap.get(Servo.class, "articulatieJos");
        articulatieSus=hardwareMap.get(Servo.class, "articulatieSus");
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
}
