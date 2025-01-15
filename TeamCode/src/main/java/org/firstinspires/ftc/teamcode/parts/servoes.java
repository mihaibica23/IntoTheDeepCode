package org.firstinspires.ftc.teamcode.parts;

import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

public class servoes {
    private Servo gheara, articulatieJos, getArticulatieSus;
    public servoes(HardwareMap hardwareMap)
    {
        gheara=hardwareMap.get(Servo.class, "gheara");
        articulatieJos=hardwareMap.get(Servo.class, "articulatieJos");
        getArticulatieSus=hardwareMap.get(Servo.class, "getArticulatieSus");
    }
}
