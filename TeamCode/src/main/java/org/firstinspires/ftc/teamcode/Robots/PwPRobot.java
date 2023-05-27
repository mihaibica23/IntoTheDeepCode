package org.firstinspires.ftc.teamcode.Robots;

import static org.firstinspires.ftc.teamcode.Components.Claw.ClawStates.CLAW_CLOSED;
import static org.firstinspires.ftc.teamcode.Components.Claw.ClawStates.CLAW_CLOSING;
import static org.firstinspires.ftc.teamcode.Components.Claw.ClawStates.CLAW_OPEN;
import static org.firstinspires.ftc.teamcode.Components.Claw.ClawStates.CLAW_OPENING;
import static org.firstinspires.ftc.teamcode.Components.Claw.ClawStates.CLAW_WIDE;
import static org.firstinspires.ftc.teamcode.Components.Claw.ClawStates.CLAW_WIDING;
import static org.firstinspires.ftc.teamcode.Components.Lift.LiftConstants.LIFT_HIGH_JUNCTION;
import static org.firstinspires.ftc.teamcode.Components.Lift.LiftConstants.LIFT_MED_JUNCTION;
import static org.firstinspires.ftc.teamcode.Components.LiftArm.liftArmStates.ARM_INTAKE;
import static org.firstinspires.ftc.teamcode.Components.LiftArm.liftArmStates.ARM_OUTTAKE;
import static org.firstinspires.ftc.teamcode.Components.RFModules.Devices.RFMotor.RESISTANCE;
import static org.firstinspires.ftc.teamcode.Components.RFModules.Devices.RFMotor.kA;
import static org.firstinspires.ftc.teamcode.Components.Switch.prezzed;
import static org.firstinspires.ftc.teamcode.Components.flippas.flippaStates.FLIP_INTAKE;
import static org.firstinspires.ftc.teamcode.Components.flippas.flippaStates.FLIP_OUTTAKE;
import static org.firstinspires.ftc.teamcode.roadrunner.drive.DriveConstants.TRACK_WIDTH;
import static org.firstinspires.ftc.teamcode.roadrunner.drive.DriveConstants.kV;
import static java.lang.Math.PI;
import static java.lang.Math.abs;
import static java.lang.Math.atan2;
import static java.lang.Math.cos;
import static java.lang.Math.pow;
import static java.lang.Math.sin;
import static java.lang.Math.sqrt;

import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.acmerobotics.roadrunner.geometry.Vector2d;
import com.acmerobotics.roadrunner.trajectory.Trajectory;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.VoltageSensor;

import org.firstinspires.ftc.teamcode.Components.Aligner;
import org.firstinspires.ftc.teamcode.Components.CV.CVMaster;
import org.firstinspires.ftc.teamcode.Components.Claw;
import org.firstinspires.ftc.teamcode.Components.ClawExtension;
import org.firstinspires.ftc.teamcode.Components.Field;
import org.firstinspires.ftc.teamcode.Components.LEDStrip;
import org.firstinspires.ftc.teamcode.Components.Lift;
import org.firstinspires.ftc.teamcode.Components.LiftArm;
import org.firstinspires.ftc.teamcode.Components.RFModules.Devices.RFGamepad;
import org.firstinspires.ftc.teamcode.Components.RFModules.Devices.RFMotor;
import org.firstinspires.ftc.teamcode.Components.Switch;
import org.firstinspires.ftc.teamcode.Components.flippas;
import org.firstinspires.ftc.teamcode.roadrunner.drive.DriveConstants;
import org.firstinspires.ftc.teamcode.roadrunner.drive.PoseStorage;
import org.firstinspires.ftc.teamcode.roadrunner.drive.SampleMecanumDrive;
import org.firstinspires.ftc.teamcode.roadrunner.trajectorysequence.TrajectorySequence;
import org.firstinspires.ftc.teamcode.roadrunner.trajectorysequence.TrajectorySequenceBuilder;
import org.firstinspires.ftc.teamcode.roadrunner.util.IMU;

import java.util.ArrayList;
import java.util.Objects;

public class PwPRobot extends BasicRobot {
    private Aligner aligner = null;
    private Claw claw = null;
    private LiftArm liftArm = null;
    private ClawExtension clawExtension = null;
    private Lift lift = null;
    private RFGamepad gp = null;
    public Switch clawSwitch = null;
    public Field field = null;
    public CVMaster cv = null;
    public SampleMecanumDrive roadrun = null;
    private IMU imu = null;
    private ArrayList<Integer> seq;
    private boolean regularDrive = true;
    private LEDStrip leds = null;
    private VoltageSensor voltageSensor = null;
    private flippas flipper = null;
    boolean finished = false;
    double voltage;
    boolean manualSlides = false;


    public PwPRobot(LinearOpMode opMode, boolean p_isTeleop) {
        super(opMode, p_isTeleop);

//        DriveConstants.MAX_ANG_ACCEL *= 12.8/voltageSensor.getVoltage();
//        DriveConstants.MAX_ANG_VEL *= 12.8/voltageSensor.getVoltage();


//        kV*=13/ voltageSensor.getVoltage();
//        kA *= 13/ voltageSensor.getVoltage();
//        kStatic *= 13/voltageSensor.getVoltage();
//        MAX_ANG_ACCEL /= 13/ voltageSensor.getVoltage();
//        MAX_ANG_VEL /= 13/ voltageSensor.getVoltage();
//        MAX_VEL/=13/ voltageSensor.getVoltage();
//        MAX_ACCEL/= 13/ voltageSensor.getVoltage();
        roadrun = new SampleMecanumDrive(opMode.hardwareMap);
        cv = new CVMaster();
        gp = new RFGamepad();
//        imu = new IMU();
        field = new Field(roadrun, cv, imu, gp);
//        aligner = new Aligner();
        claw = new Claw();
        liftArm = new LiftArm();
//        clawExtension = new ClawExtension();
        lift = new Lift();
        leds = new LEDStrip();
        clawSwitch = new Switch();
        flipper = new flippas();
        finished = true;
        if (isTeleop) {
            roadrun.setPoseEstimate(PoseStorage.currentPose);
            kA*=0.25;
        }
        voltageSensor = op.hardwareMap.voltageSensor.iterator().next();
        voltage = voltageSensor.getVoltage();
        RFMotor.kP *= 13 / voltageSensor.getVoltage();
        RESISTANCE *= 12.5/ voltageSensor.getVoltage();
        RFMotor.kA *= 13 / voltageSensor.getVoltage();
        DriveConstants.TRACK_WIDTH *= 12.7 / voltageSensor.getVoltage();
        kV *= 12.7 / voltageSensor.getVoltage();
        DriveConstants.kA *= 12.7 / voltageSensor.getVoltage();
        DriveConstants.kStatic *= 12.7 / voltageSensor.getVoltage();
    }
//    com.qualcomm.ftcrobotcontroller I/art: Waiting for a blocking GC Alloc
//2023-01-05 14:19:08.807 9944-10985/com.qualcomm.ftcrobotcontroller I/art: Alloc sticky concurrent mark sweep GC freed 340391(7MB) AllocSpace objects, 0(0B) LOS objects, 20% free, 43MB/54MB, paused 2.675ms total 197.819ms
//2023-01-05 14:19:08.807 9944-12811/com.qualcomm.ftcrobotcontroller I/art: WaitForGcToComplete blocked for 689.441ms for cause Alloc
//2023-01-05 14:19:08.807 9944-12811/com.qualcomm.ftcrobotcontroller I/art: Starting a blocking GC Alloc

    public void stop() {
        lift.setLiftPower(0.0);
        logger.logPos(roadrun.getPoseEstimate());
        logger.log("/RobotLogs/GeneralRobot", "program stoped");
    }

    public void queuedStop() {
        if (queuer.queue(false, finished)) {
            lift.setLiftPower(0.0);
            logger.log("/RobotLogs/GeneralRobot", "program stoped");
            finished = true;
        }
    }

    public void setToNow(boolean p_Optional) {
        if (queuer.isFirstLoop()) {
            queuer.queue(false, false, true);
        } else {
            if (queuer.queue(false, false, p_Optional)) {
                done();
                queuer.setToNow();
            }
        }
    }

    public void delay(double p_delay) {
        queuer.addDelay(p_delay);
    }

    public void waitForFinish(int condition) {
        queuer.waitForFinish(condition);
    }

    public void waitForFinish() {
        queuer.waitForFinish();
    }

    public void autoAim() {
        if (queuer.queue(false, !roadrun.isBusy())) {
            if (!roadrun.isBusy() && field.lookingAtPole()) {
                Trajectory trajectory = roadrun.trajectoryBuilder(roadrun.getPoseEstimate()).lineToLinearHeading(
                        field.getDropPosition()).build();
                roadrun.followTrajectoryAsync(trajectory);
            }
        }
    }

    public void done() {
        field.setDoneLookin(true);
        queuer.done();
    }

    public void updateLiftArmStates() {
        liftArm.updateLiftArmStates();
    }

    public void updateTrajectoryWithCam() {
        if (queuer.queue(true, field.isDoneLookin())) {
            if (field.lookingAtPole()) {
                Pose2d target = field.polePos();
                TrajectorySequence trajectory = roadrun.getCurrentTraj();
                roadrun.changeTrajectorySequence(roadrun.trajectorySequenceBuilder(trajectory.start())
                        .setReversed(true)
//                                .splineTo(target.vec(), target.getHeading())
                        .splineTo(target.vec(), target.getHeading())
                        .addTemporalMarker(this::done)
                        .build());
//                field.setDoneLookin(true);
                logger.log("/RobotLogs/GeneralRobot", "mr.obama" + target + "im pole" + roadrun.getPoseEstimate());
//                logger.log("/RobotLogs/GeneralRobot", "coords"+cv.rotatedPolarCoord()[0]+","+cv.rotatedPolarCoord()[1]);
            }
            field.setDoneLookin(false);
        }
    }

    public void updateTrajectoryWithCone() {
        if (queuer.queue(true, field.isDoneLookin())) {
            if (field.lookingAtCone()) {
                Pose2d target = field.conePos();
                TrajectorySequence trajectory = roadrun.getCurrentTraj();
                roadrun.changeTrajectorySequence(roadrun.trajectorySequenceBuilder(trajectory.start())
                        .setReversed(false)
                        .splineToSplineHeading(new Pose2d(target.vec(), trajectory.end().getHeading()), trajectory.end().getHeading())
                        .addTemporalMarker(this::done)
                        .build());
//                field.setDoneLookin(true);
                logger.log("/RobotLogs/GeneralRobot", "mr.obama" + target + "im cone" + roadrun.getPoseEstimate());
//                logger.log("/RobotLogs/GeneralRobot", "coords"+cv.rotatedConarCoord()[0]+","+cv.rotatedConarCoord()[1]);

            }
            field.setDoneLookin(false);
        }
    }

    public void autoTeleCone() {
        if (field.lookingAtCone()) {
            logger.log("/RobotLogs/GeneralRobot", "mr.obama");
            Pose2d target = field.conePos();
            if (roadrun.isBusy()) {
                TrajectorySequence trajectory = roadrun.getCurrentTraj();
                if (roadrun.getPoseEstimate().vec().distTo(trajectory.end().vec()) < 5) {
                    return;
                }
                roadrun.changeTrajectorySequence(roadrun.trajectorySequenceBuilder(trajectory.start())
                        .setReversed(false)
                        .splineToSplineHeading(target, target.getHeading()).build());
//                field.setDoneLookin(true);
                logger.log("/RobotLogs/GeneralRobot", "mr.obama" + target + "im cone" + roadrun.getPoseEstimate());
            } else {
                logger.log("/RobotLogs/GeneralRobot", "mr.brobama");

                roadrun.followTrajectorySequenceAsync(roadrun.trajectorySequenceBuilder(roadrun.getPoseEstimate())
                        .setReversed(false)
                        .splineTo(target.vec(), target.getHeading()).build());
            }
            logger.log("/RobotLogs/GeneralRobot", "coords" + cv.rotatedConarCoord()[0] + "," + cv.rotatedConarCoord()[1]);

        }
    }

    public void autoTelePole() {
        if (field.lookingAtPoleTele()) {
            logger.log("/RobotLogs/GeneralRobot", "mr.obama");
            Pose2d target = field.polePos();
            if (roadrun.isBusy()) {
                TrajectorySequence trajectory = roadrun.getCurrentTraj();
                roadrun.changeTrajectorySequence(roadrun.trajectorySequenceBuilder(trajectory.start())
                        .setReversed(true)
                        .splineTo(target.vec(), target.getHeading()).build());
//                field.setDoneLookin(true);
                logger.log("/RobotLogs/GeneralRobot", "mr.obama" + target + "im cone" + roadrun.getPoseEstimate());
            } else {
                logger.log("/RobotLogs/GeneralRobot", "mr.brobama");

                roadrun.followTrajectorySequenceAsync(roadrun.trajectorySequenceBuilder(roadrun.getPoseEstimate())
                        .setReversed(true)
                        .splineTo(target.vec(), target.getHeading()).build());
            }
//                logger.log("/RobotLogs/GeneralRobot", "coords"+cv.rotatedConarCoord()[0]+","+cv.rotatedConarCoord()[1]);

        }
    }

    public void teleAutoAim(Trajectory trajectory) {
        roadrun.followTrajectoryAsync(trajectory);
    }
    public boolean[] checkIsOb(boolean l, boolean r, Pose2d endPose){
        boolean[] vals = {l,r};
        if(queuer.queue(true, !roadrun.isBusy()||l||r|| clawSwitch.isSwitched()|| CLAW_CLOSING.getStatus())) {
            if (isObL(endPose) && !l && !r) {
                vals[0]=true;
                done();
                roadrun.breakFollowing();
                roadrun.setMotorPowers(0,0,0,0);
            }
            if(isObR(endPose)&&!r&&!l){
                done();
                vals[1]=true;
                roadrun.breakFollowing();
                roadrun.setMotorPowers(0,0,0,0);
            }
        }
        return vals;
    }
    public boolean isObL(Pose2d endPos) {
        if (roadrun.getCurrentTraj()==null||roadrun.getPoseEstimate().vec().distTo(endPos.vec()) > 10) {
            if(roadrun.getCurrentTraj()!=null&&claw.isObL()) {
                logger.log("/RobotLogs/GeneralRobot", "obstacle"+roadrun.getPoseEstimate()+"|" + roadrun.getCurrentTraj().end()+"|" + roadrun.getPoseEstimate().vec().distTo(roadrun.getCurrentTraj().end().vec()));
            }else{
                return false;
            }
            return claw.isObL();
        }
        return false;
    }

    public boolean isObR(Pose2d endPos) {
        if (roadrun.getCurrentTraj()==null||roadrun.getPoseEstimate().vec().distTo(endPos.vec()) > 10) {
            if(roadrun.getCurrentTraj()!=null&&claw.isObR()) {
                logger.log("/RobotLogs/GeneralRobot", "obstacle"+roadrun.getPoseEstimate()+"|" + roadrun.getCurrentTraj().end()+"|" + roadrun.getPoseEstimate().vec().distTo(roadrun.getCurrentTraj().end().vec()));
            }else{
                return false;
            }
            return claw.isObR();
        }
        return false;
    }

    public void followTrajectorySequenceAsync(TrajectorySequence trajectorySequence) {
        if (queuer.queue(false, !roadrun.isBusy() && roadrun.getPoseEstimate().vec().distTo(trajectorySequence.end().vec()) < 3)) {
            if (!roadrun.isBusy()||roadrun.getCurrentTraj().end()!= trajectorySequence.end()) {
                TrajectorySequenceBuilder builder = roadrun.trajectorySequenceBuilder(roadrun.getPoseEstimate());
                for (int i = 0; i < trajectorySequence.size(); i++) {
                    builder.addSequenceSegment(trajectorySequence.get(i));
                }
//                builder.addTemporalMarker(this::done);
                roadrun.followTrajectorySequenceAsync(trajectorySequence);
            }
        }
    }

    public void followTrajectorySequenceAsync(TrajectorySequence trajectorySequence, boolean isOptional) {
        if (queuer.isFirstLoop()) {
            queuer.queue(false, false, true);
        } else {
            if (queuer.queue(false, !roadrun.isBusy() && roadrun.getPoseEstimate().vec().distTo(trajectorySequence.end().vec()) < 3, isOptional)) {
                if (!roadrun.isBusy()||roadrun.getCurrentTraj().end()!= trajectorySequence.end()) {
                    TrajectorySequenceBuilder builder = roadrun.trajectorySequenceBuilder(roadrun.getPoseEstimate());
                    for (int i = 0; i < trajectorySequence.size(); i++) {
                        builder.addSequenceSegment(trajectorySequence.get(i));
                    }
//                    builder.addTemporalMarker(this::done);
                    roadrun.followTrajectorySequenceAsync(builder.build());
                }
            }
        }
    }

    public void splineTo(Pose2d position, double endHeading, double tangentOffset) {
        if (queuer.queue(false, !roadrun.isBusy() && roadrun.getPoseEstimate().vec().distTo(position.vec()) < 3)) {
            if (!roadrun.isBusy()) {
                TrajectorySequence trajectorySequence = roadrun.trajectorySequenceBuilder(roadrun.getPoseEstimate())
                        .setTangentOffset(tangentOffset)
                        .splineToSplineHeading(position, endHeading)
                        .addTemporalMarker(this::done)
                        .build();
                roadrun.followTrajectorySequenceAsync(trajectorySequence);
            }
        }
    }

    public void setTRUEMAXDrivingExperience(double y, double x, double a) {
        a*=0.65;
        double angle = atan2(y, x*1.2);
        double powera = sin(angle + PI / 4);
        double powerb = sin(angle - PI / 4);
        if (abs(powera) > abs(powerb)
        ) {
            powerb *= 1 / abs(powera);
            powera *= 1 / abs(powera);
        } else {
            powera *= 1 / abs(powerb);
            powerb *= 1 / abs(powerb);
        }
        double magnitude = sqrt(x * x + y * y);
        //wheel : y , x
        double[] maxes = {75, 50, 7};
        double[] targetVelocity = {y / kV, x / kV, a / kV / TRACK_WIDTH};
//        Pose2d avoidVelo = field.correctionVelo();
//        targetVelocity[0] += avoidVelo.getX();
//        targetVelocity[1] += avoidVelo.getY();
//        targetVelocity[2] += avoidVelo.getHeading();

        Pose2d actualVelocity = roadrun.getPoseVelocity();
        actualVelocity = field.filteredVelocity(actualVelocity);
        Pose2d pos = roadrun.getPoseEstimate();
        double[] t = {cos(-pos.getHeading()), sin(-pos.getHeading())};
        Vector2d rotVelocity = actualVelocity.vec().rotated(0);
        double[] diffs = {targetVelocity[0] - rotVelocity.getX(), targetVelocity[1] - rotVelocity.getY(), targetVelocity[2] - actualVelocity.getHeading()};
        if (actualVelocity.getX() == 100) {
            diffs[0] = 0;
        }
        if (actualVelocity.getY() == 100) {
            diffs[1] = 0;
        }
        if (actualVelocity.getHeading() == 69) {
            diffs[2] = 0;
        }
        double cAngle = atan2(diffs[0], diffs[1]);
        double cMag = sqrt(diffs[1] * diffs[1] + diffs[0] * diffs[0]) * kV * 2;
        double angleCorrection = diffs[2] * TRACK_WIDTH * kV * 0.1;
        if (a == 0 && diffs[0] * diffs[0] + diffs[1] * diffs[1] > 25) {
            angleCorrection = 0;
        }
        op.telemetry.addData("velocity", actualVelocity);
        op.telemetry.addData("diffsy", diffs[0]);
        op.telemetry.addData("diffsx", diffs[1]);
        op.telemetry.addData("diffsa", diffs[2]);
        double slidesConst = 1 - lift.getLiftPosition() / 1200.0;
        double[] powers = {powerb * magnitude - a - angleCorrection + (diffs[0] - diffs[1]) * .3 * kV * slidesConst,
                powera * magnitude - a - angleCorrection + (diffs[0] + diffs[1]) * .3 * kV * slidesConst,
                powerb * magnitude + a + angleCorrection + (diffs[0] - diffs[1]) * .3 * kV * slidesConst,
                powera * magnitude + a + angleCorrection + (diffs[0] + diffs[1]) * .3 * kV * slidesConst
        };
        if(abs(powers[0]-a)>1){
            powers[2]+=a/2;
            powers[3]+=a/2;
        }
        if(abs(powers[1]-a)>1){
            powers[2]+=a/2;
            powers[3]+=a/2;
        }
        if(abs(powers[2]+a)>1){
            powers[1]-=a/2;
            powers[0]-=a/2;
        }
        if(abs(powers[3]+a)>1){
            powers[1]-=a/2;
            powers[0]-=a/2;
        }
        roadrun.setMotorPowers(powers[0]-a-angleCorrection,
                powers[1]-a-angleCorrection,
                powers[2]+a+ angleCorrection ,
                powers[3]+a+ angleCorrection );

    }

    public void followTrajectoryAsync(Trajectory trajectory) {
        if (queuer.queue(false, !roadrun.isBusy())) {
            if (!roadrun.isBusy()) {
                roadrun.followTrajectoryAsync(trajectory);
            }
        }
    }


    public void setFirstLoop(boolean value) {
        queuer.setFirstLoop(value);
    }

    public void openClaw() {
        openClaw(true);
    }

    public void openClaw(boolean p_asynchronous) {
        if (queuer.queue(p_asynchronous, !CLAW_OPENING.getStatus())) {
            claw.updateClawStates();
            claw.openClaw();
        }
    }

    public double getVoltage() {
        return voltage;
    }

    public void closeClaw() {
        closeClaw(true);

    }

    public void closeClaw(boolean p_asynchronous) {
        if (queuer.queue(p_asynchronous, !CLAW_CLOSING.getStatus())) {
            claw.updateClawStates();
            claw.closeClawRaw();
        }
    }

    public void toggleClawPosition() {
        toggleClawPosition(true);
    }

    public void toggleClawPosition(boolean p_asynchronous) {
        if (queuer.queue(p_asynchronous, op.getRuntime() > claw.clawServoLastSwitchTime +
                claw.CLAW_SERVO_SWITCH_TIME)) {
            claw.toggleClawPosition();
        }
    }

    public boolean isConeReady() {
        return claw.isConeReady(0);
    }

    public void updateClawStates() {
        claw.updateClawStates();
    }

    public void liftToPosition(Lift.LiftConstants targetJunction) {
        if (queuer.queue(true, lift.getLiftTarget() != targetJunction.getValue() || abs(lift.getLiftPosition() - targetJunction.getValue()) < 100)) {
            lift.liftToPosition(targetJunction);
        }
    }

    public void wideClaw() {
        if (queuer.queue(true, !CLAW_WIDING.getStatus())) {
            claw.wideClaw();
        }
    }

    public void wideClaw(boolean asyn) {
        if (queuer.queue(false, !CLAW_WIDING.getStatus())) {
            claw.wideClaw();
        }
    }

    public void liftToTargetAuto() {
        lift.liftToTargetAuto();
    }

    public void liftToPosition(int tickTarget) {
        if (queuer.queue(true, lift.getLiftTarget() != tickTarget || abs(lift.getLiftPosition() - tickTarget) < 100)) {
            lift.liftToPosition(tickTarget);
        }
    }

    public void changeTrajectorySequence(TrajectorySequence trajectorySequence) {
        roadrun.changeTrajectorySequence(trajectorySequence);
    }

    public void changeTrajectorySequence(TrajectorySequence trajectorySequence, boolean isOptional) {
        if (queuer.isFirstLoop()) {
            queuer.queue(true, false, true);
        } else {
            if (queuer.queue(true, !roadrun.isBusy() && roadrun.getPoseEstimate().vec().distTo(trajectorySequence.end().vec()) < 3, isOptional)) {
                if (!roadrun.isBusy()) {
                    roadrun.changeTrajectorySequence(trajectorySequence);

                } else if (roadrun.isBusy()&&roadrun.getCurrentTraj()!= trajectorySequence) {
                    roadrun.changeTrajectorySequence(trajectorySequence);
                }
            }
        }
    }
    public void clearObstacle(Pose2d clearPos, boolean isOptional){
//        if (queuer.isFirstLoop()) {
//            queuer.queue(true, false, true);
//        } else {
//            if (queuer.queue(true, Objects.equals(roadrun.getCurrentTraj().end(), new Pose2d(65, 11.51, 0)), isOptional)) {
//                done();
//            }
//        }
        if (queuer.isFirstLoop()) {
            queuer.queue(false, false, true);
        } else {
            if (queuer.queue(false, !roadrun.isBusy() , isOptional)) {
                TrajectorySequence traj = roadrun.trajectorySequenceBuilder(roadrun.getPoseEstimate())
                        .setReversed(false)
                        .splineToLinearHeading(clearPos, clearPos.getHeading())
                        .setReversed(true)
                        .splineToSplineHeading(new Pose2d(51, 11.5, Math.toRadians(0)), Math.toRadians(180))
                        .setReversed(false)
                        .splineTo(new Vector2d(65, 11.51), Math.toRadians(0))
                        .build();
                if (!roadrun.isBusy()) {
                    roadrun.followTrajectorySequence(traj);

                } else if (roadrun.isBusy()&&roadrun.getCurrentTraj()!= traj) {
                    roadrun.followTrajectorySequence(traj);
                }
            }
        }
    }

    public void liftToPosition(int tickTarget, boolean p_asynchronous) {
        if (queuer.queue(p_asynchronous, lift.getLiftTarget() != tickTarget || abs(lift.getLiftPosition() - tickTarget) < 100)) {
            lift.liftToPosition(tickTarget);
        }
    }

    public void setLiftPower(double p_power) {
        lift.setLiftPower(p_power);
    }

    public void lowerLiftArmToIntake() {
        lowerLiftArmToIntake(true);
    }

    public void lowerLiftArmToIntake(boolean p_asynchronous) {
        if (queuer.queue(p_asynchronous, ARM_INTAKE.getStatus())) {

            liftArm.updateLiftArmStates();
            liftArm.lowerLiftArmToIntake();
        }
    }

    public void cycleLiftArmToCycle(boolean p_asynchronous) {
        if (queuer.queue(p_asynchronous, liftArm.isCylce())) {

            liftArm.updateLiftArmStates();
            liftArm.cycleLiftArmToCylce();
        }
    }

    public void raiseLiftArmToOuttake() {
        raiseLiftArmToOuttake(true);
    }

    public void raiseLiftArmToOuttake(boolean p_asynchronous) {
        if (queuer.queue(p_asynchronous, ARM_OUTTAKE.getStatus())) {

            liftArm.updateLiftArmStates();
            liftArm.raiseLiftArmToOuttake();
        }
    }

    public void toggleArmPosition() {
        if (queuer.queue(true, op.getRuntime() > liftArm.liftArmServoLastSwitchTime +
                liftArm.LIFT_ARM_SERVO_SWITCH_TIME)) {

            liftArm.liftArmServoLastSwitchTime = op.getRuntime();
            liftArm.toggleArmPosition();
        }
    }

    //    public void spinAlignerIntake() {
//        if (queuer.queue(true, alignerSensor.getSensorDistance() <
//                aligner.CONE_IN_ALIGNER_DISTANCE)) {
//
//            aligner.spinAlignerIntake();
//        }
//    }
//
//    public void stopAlignerIntake() {
//        if (queuer.queue(true, op.getRuntime() > aligner.alignerMotorLastStopTime +
//                aligner.ALIGNER_MOTOR_STOP_TIME)) {
//
//            aligner.stopAlignerIntake();
//        }
//    }
//
//    public void reverseAlignerIntake() {
//        if (queuer.queue(true, alignerSensor.getSensorDistance() >
//                aligner.CONE_OUT_OF_ALIGNER_DISTANCE)) {
//
//            aligner.reverseAlignerIntake();
//        }
//    }
//    public void reverseAlignerIntake() {
//        if (queuer.queue(true, !aligner.isConeInAligner())){
//
//            aligner.reverseAlignerIntake();
//        }
//    }
    public void liftToTarget() {
        lift.liftToTarget();
    }

    public void setLiftTarget(double p_target) {
        lift.setLiftTarget(p_target);
    }

    public void setLiftVelocity(double velocity) {
        lift.setLiftVelocity(velocity);
    }


    private boolean mecZeroLogged = false;
    private boolean progNameLogged = false;

    public void setPoseEstimate(Pose2d newPose) {
        roadrun.setPoseEstimate(newPose);
        PoseStorage.currentPose = newPose;
    }

    public void iterateConestackUp() {
        if (queuer.queue(true, lift.getLiftTarget() == lift.getStackPos())) {
            lift.iterateConeStackUp();
        }
    }

    public void iterateConestackDown() {
        if (queuer.queue(true, lift.getLiftTarget() == lift.getStackPos())) {
            lift.iterateConeStackDown();
        }
    }

    public int getStackLevel() {
        return lift.getStackLevel();
    }

    public void heartbeatRed() {
        leds.heartbeatred();
    }

    public void darkGreen() {
        leds.darkgreen();
    }

    public void violet() {
        leds.violet();
    }

    public void blue() {
        leds.blue();
    }

    public void rainbowRainbow() {
        leds.rainbowrainbow();
    }

    public void cp1shot() {
        leds.cp1shot();
    }

    public void setStackLevelColor(int level) {
        leds.setStackLevelColor(level);
    }

    public void partycolorwave() {
        leds.red();
    }
    public boolean poleInView(boolean isInView){
        boolean val = isInView;
        if(queuer.queue(false, !roadrun.isBusy())) {
            if (cv.poleInView() && isInView) {
                val=false;
            }
            else if(!cv.poleInView()&&!isInView){
                val=true;
            }
        }
        return val;
    }

    public void teleOp() {
        roadrun.update();
//        if (progNameLogged == false) {
//            logger.log("/RobotLogs/GeneralRobot", "PROGRAM RUN: PwPTeleOp", false);
//            progNameLogged = true;
//        }
//        gp.readGamepad(op.gamepad2.y, "gamepad2_y", "Status");
//        gp.readGamepad(op.gamepad1.x, "gamepad1_x", "Status");
        boolean isY = gp.readGamepad(op.gamepad1.y, "gamepad1_y", "Status");
//        gp.readGamepad(op.gamepad2.a, "gamepad1_a", "Status");
        boolean isX2 = gp.readGamepad(op.gamepad2.x, "gamepad2_x", "Status");
        boolean isA = gp.readGamepad(op.gamepad1.x, "gamepad1_a", "Status");
        boolean isX = gp.readGamepad(op.gamepad2.x, "gamepad2_x", "Status");

        boolean isFlipper = gp.readGamepad(op.gamepad1.left_bumper, "gamepad1_left_bumper", "Status");
//        gp.readGamepad(op.gamepad2.a, "gamepad1_a", "Status");
//        gp.readGamepad(op.gamepad2.b, "gamepad1_b", "Status");
//        gp.readGamepad(op.gamepad1.left_stick_y, "gamepad1_left_stick_y", "Value");
//        gp.readGamepad(op.gamepad1.left_stick_x, "gamepad1_left_stick_x", "Value");
//        gp.readGamepad(op.gamepad1.right_stick_x, "gamepad1_right_stick_x", "Value");
//        gp.readGamepad(op.gamepad2.left_trigger, "gamepad2_left_trigger", "Value");
//        gp.readGamepad(op.gamepad2.right_trigger, "gamepad2_right_trigger", "Value");
//        gp.readGamepad(op.gamepad2.right_bumper, "gamepad2_right_bumper", "Status");
        boolean isBumper2 = gp.readGamepad(op.gamepad2.left_bumper, "gamepad2_left_bumper", "Status");
        op.telemetry.addData("switched:", clawSwitch.isSwitched());
        op.telemetry.addData("coneDist", claw.coneDistance());
        flipper.update();

        if (isA) {
            if (liftArm.ableArmed()) {
                liftArm.disableArm();
            } else {
                liftArm.enableArm();
            }
        }
        if (isY) {
            regularDrive = !regularDrive;
        }
        if (isX2) {
            manualSlides = !manualSlides;
        }
        if (isBumper2) {
            lift.resetEncoder();
        }


        if (op.gamepad1.b) {
            liftArm.flipCone();
        }
        if (isFlipper) {
            if (FLIP_INTAKE.getStatus()) {
                flipper.raiseLiftArmToOuttake();
            }
            if (FLIP_OUTTAKE.getStatus()) {
                flipper.lowerFlippas();
            }
        }
        if(isX2){
            lift.setPeak();
        }

        //omnidirectional movement + turning

        if (op.gamepad2.y) {
            lift.setLiftTarget(LIFT_HIGH_JUNCTION.getValue());
            liftArm.raiseLiftArmToOuttake();
        }
        if (op.gamepad2.b) {
            lift.setLiftTarget(LIFT_MED_JUNCTION.getValue());
            liftArm.raiseLiftArmToOuttake();
        }
        if (op.gamepad2.a) {
            liftArm.lowerLiftArmToIntake();
            lift.setLiftTarget(0);
        }

        if (op.gamepad2.dpad_left) {
            lift.resetEncoder();
        }
        //manual lift up/down
        if (op.gamepad2.dpad_right) {
            lift.setLiftRawPower((op.gamepad2.right_trigger - op.gamepad2.left_trigger) / 3);
        } else if (op.gamepad2.right_trigger > 0.1 || op.gamepad2.left_trigger > 0.1) {
            if (manualSlides) {
                lift.setLiftRawPower(1);
                lift.updateLastManualTime();
            } else {
                lift.setLiftPower((op.gamepad2.right_trigger - op.gamepad2.left_trigger));
                lift.updateLastManualTime();
            }
        } else if (!manualSlides) {
//            lift.setLiftPower(0);
            lift.liftToTarget();
        } else {
            lift.setLiftRawPower(0);
        }


        if (op.gamepad2.dpad_down) {
            lift.iterateConeStackDown();
            if (op.getRuntime() < 90 || op.getRuntime() > 92) {
                setStackLevelColor(getStackLevel());
            }
            liftArm.cycleLiftArmToCylce();
            logger.log("/RobotLogs/GeneralRobot", "Lift,iterateConeStackDown(),Cone Stack Lowered by 1", true);
        }
        if (op.gamepad2.dpad_up) {
            lift.iterateConeStackUp();
            if (op.getRuntime() < 90 || op.getRuntime() > 92) {
                setStackLevelColor(getStackLevel());
            }
            liftArm.cycleLiftArmToCylce();
            logger.log("/RobotLogs/GeneralRobot", "Lift,iterateConeStackUp(),Cone Stack Raised by 1", true);
        }

        //when not manual lifting, automate lifting

//        if (field.lookingAtPole() && op.gamepad1.dpad_up && !roadrun.isBusy()) {
//            field.updateTrajectory();
//            teleAutoAim(field.getTrajectory());
//        } else if (roadrun.isBusy()) {
//            //nothin
//        } else {
        op.telemetry.addData("closestDropPosition", field.closestDropPosition());
        op.telemetry.addData("closestDropPositionValue", field.getDropPosition());
        op.telemetry.addData("closePole", field.getClosePole());
        op.telemetry.addData("seenPolePose", field.calcPolePose(roadrun.getPoseEstimate()));
        op.telemetry.addData("currentDropPosition", field.getDropPose());
        op.telemetry.addData("contourDimensions",cv.getContourDimensions()[0]+","+cv.getContourDimensions()[1]);
        op.telemetry.addData("avoid velo", field.correctionVelo());
        op.telemetry.addData("poleInView", cv.poleInView());
        double[] vals = {op.gamepad1.left_stick_x, op.gamepad1.left_stick_y, op.gamepad1.right_stick_x};
        double[] minBoost = {0.1, 0.1, 0.05};
        boolean boosted = false;
        if (abs(op.gamepad1.left_stick_x) < 0.15) {
            minBoost[0] = 0;
        } else {
            boosted = true;
        }
        if (op.gamepad1.left_stick_x == 0) {
            vals[0] = 0.0001;
        }
        if (abs(op.gamepad1.left_stick_y) < 0.04) {
            minBoost[1] = 0;
        } else {
            boosted = true;
        }
        if (op.gamepad1.left_stick_y == 0) {
            vals[1] = 0.0001;

        }
        if (abs(op.gamepad1.right_stick_x) < 0.03) {
            minBoost[2] = 0;

            //48.8,36.6,
        } else {
            boosted = true;
        }
        if (op.gamepad1.right_stick_x == 0) {
            vals[2] = 0.0001;

        }
        if (!roadrun.isBusy() || boosted) {
            if (roadrun.isBusy()) {
                field.breakAutoTele();
            }
            if (regularDrive) {
                roadrun.setWeightedDrivePower(new Pose2d(

                        abs(vals[1] - 0.0001) / -vals[1] * (minBoost[1] + 0.65 * abs(vals[1]) + 0.15 * pow(abs(vals[1]), 2.4)),
                        abs(vals[0] - 0.0001) / -vals[0] * (minBoost[0] + 0.65 * abs(vals[0]) + 0.15 * pow(abs(vals[0]), 2.4)),
                        abs(vals[2] - 0.0001) / -vals[2] * (minBoost[2] + 0.7 * abs(vals[2])))
                );
            } else {
//                Vector2d input = new Vector2d(abs(vals[1] - 0.0001) / -vals[1] * (minBoost[1] + 0.5 * abs(vals[1]) + 0.15 * pow(abs(vals[1]), 3)),
//                        abs(vals[0] - 0.0001) / -vals[0] * (minBoost[0] + 0.5 * abs(vals[0]) + 0.15 * pow(abs(vals[0]), 3)));
//                input = input.rotated(-roadrun.getPoseEstimate().getHeading() - toRadians(90));
//                roadrun.setWeightedDrivePower(
//                        new Pose2d(-vals[1],
//                                -vals[0],
//                                -vals[2])
//                );
                setTRUEMAXDrivingExperience(abs(vals[1] - 0.0001) / -vals[1] * (  abs(vals[1])),
                        abs(vals[0] - 0.0001) / -vals[0] * (abs(vals[0])),
                        abs(vals[2] - 0.0001) / -vals[2] * (0.7 * abs(vals[2])));
            }
        }
        if ((-op.gamepad1.left_stick_y * 0.7 == -0) && (-op.gamepad1.left_stick_x == -0) && (-op.gamepad1.right_stick_x * 0.8 == -0) && (mecZeroLogged == false)) {
            logger.log("/RobotLogs/GeneralRobot", "Mecanum,setWeightedDriverPower(Pose2d),Mec = 0 | 0 | 0", true);
            mecZeroLogged = true;
        } else if ((-op.gamepad1.left_stick_y * 0.7 == -0) && (-op.gamepad1.left_stick_x == -0) && (-op.gamepad1.right_stick_x * 0.8 == -0) && (mecZeroLogged == true)) {
            //nutting
        } else {
            logger.log("/RobotLogs/GeneralRobot", "Mecanum,setWeightedDriverPower(Pose2d),Mec =  " + -op.gamepad1.left_stick_y * 0.7 + " | " + -op.gamepad1.left_stick_x + " | " + -op.gamepad1.right_stick_x * 0.8, true);
            mecZeroLogged = false;
        }
        //toggle automate lift target to higher junc
        if (op.gamepad2.dpad_up) {
            lift.toggleLiftPosition(1);
        }
        //toggle automate lift target to lower junc
        if (op.gamepad2.dpad_down) {
            lift.toggleLiftPosition(-1);
        }
        //toggle liftArm position
        if (op.gamepad2.right_bumper) {
            if (ARM_OUTTAKE.getStatus()) {
                liftArm.lowerLiftArmToIntake();

            } else {
                if (CLAW_WIDE.getStatus()) {
                    claw.openClaw();
                }
                liftArm.raiseLiftArmToOuttake();
            }
        }
//        field.closestDropPosition(false);
        if (op.gamepad1.right_bumper) {
            if (CLAW_CLOSED.getStatus()) {
                claw.setLastOpenTime(op.getRuntime());
                claw.openClaw();
                if (ARM_OUTTAKE.getStatus() && field.closestDropPosition()) {
                    roadrun.setPoseEstimate(field.getDropPosition());
                }
            } else {
                claw.closeClawRaw();
            }
        }
//        claw.closeClaw();
        if (op.getRuntime() - claw.getLastTime() > .4 && op.getRuntime() - claw.getLastTime() < .7 && CLAW_CLOSED.getStatus() && !prezzed) {
            claw.openClaw();
        }
        if (op.getRuntime() - claw.getLastTime() > 1 && op.getRuntime() - claw.getLastTime() < 1.3 && CLAW_CLOSED.getStatus() && lift.getStackPos() == lift.getLiftTarget()) {
            lift.setLiftTarget(0);
        }
        if (op.getRuntime() - claw.getLastTime() > 0.3 && op.getRuntime() - claw.getLastTime() < 0.7 && CLAW_CLOSED.getStatus()&& clawSwitch.isSwitched() && lift.getStackPos() == lift.getLiftTarget()) {
liftArm.raiseLiftArmToOuttake();
        }
        if (CLAW_OPEN.getStatus() && ARM_INTAKE.getStatus() && !CLAW_WIDE.getStatus()) {
            claw.teleClaw();
        }
        if (op.getRuntime() > 90 && op.getRuntime() < 92) {
            rainbowRainbow();
        } else if ((!CLAW_CLOSED.getStatus() && !ARM_OUTTAKE.getStatus()) && field.lookingAtCone()) {
            leds.pattern29();
            if (op.gamepad1.a) {
                autoTeleCone();
            }
        } else if ((CLAW_CLOSED.getStatus() && ARM_OUTTAKE.getStatus() && field.lookingAtPoleTele())) {
            leds.pattern29();
            if (op.gamepad1.a) {
                autoTelePole();
            }
        } else if (lift.getLiftTarget() == lift.getStackPos()) {
            setStackLevelColor(lift.getStackLevel());
        } else if (CLAW_OPEN.getStatus() || CLAW_WIDE.getStatus()) {
            darkGreen();
        } else {
            partycolorwave();
        }


        //will only close when detect cone
        if (op.getRuntime() - claw.getLastOpenTime() > 0.5) {
            claw.closeClaw(roadrun.getPoseVelocity().vec());
        }
        op.telemetry.addData("stacklevel", lift.getStackLevel());
        if (gp.updateSequence()) {
            field.autoMovement();
        }


        liftArm.updateLiftArmStates();
        claw.updateClawStates();
        lift.updateLiftStates();

//            logger.log("/RobotLogs/GeneralRobot", seq.toString(), false);
        //USE THE RFGAMEPAD FUNCTION CALLED getSequence(), WILL RETURN ARRAYLIST OF INTS:
        //1 = down, 2 = right, 3 = up, 4 = left
//        }
    }
}
