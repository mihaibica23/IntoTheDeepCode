package org.firstinspires.ftc.teamcode.roadrunner.drive;

import static org.firstinspires.ftc.teamcode.Robots.BasicRobot.packet;
import static org.firstinspires.ftc.teamcode.roadrunner.drive.DriveConstants.MAX_ACCEL;
import static org.firstinspires.ftc.teamcode.roadrunner.drive.DriveConstants.MAX_VEL;
import static org.firstinspires.ftc.teamcode.roadrunner.drive.PoseStorage.currentPose;
import static org.firstinspires.ftc.teamcode.roadrunner.drive.PoseStorage.currentVelocity;
import static java.lang.Double.NaN;
import static java.lang.Double.max;
import static java.lang.Double.min;
import static java.lang.Math.abs;
import static java.lang.Math.atan2;
import static java.lang.Math.pow;
import static java.lang.Math.sqrt;
import static java.lang.Math.toRadians;

import com.acmerobotics.dashboard.config.Config;
import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.acmerobotics.roadrunner.geometry.Vector2d;

import java.util.ArrayList;

@Config
public class CatmulRomInterpolater {
    ArrayList<Pose2d> points;
    boolean isEndpoint = false;
    RFWaypoint ref;
    double offset = 0;
    //turning radius
    double CURVING_DISTANCE = 10;
    public static double ALPHA = 5, TENSION = 0;

    public CatmulRomInterpolater(ArrayList<RFSegment> p_segments, int index) {
        points = new ArrayList();
        //generate first three points for interpolation, fill in if there are none
        if (index > 1) {
            points.add(p_segments.get(index - 2).getWaypoint().getTarget());
            points.add(p_segments.get(index - 1).getWaypoint().getTarget());
            points.add(p_segments.get(index).getWaypoint().getTarget());
        } else if (index == 1) {
            points.add(currentPose);
            points.add(p_segments.get(0).getWaypoint().getTarget());
            points.add(p_segments.get(1).getWaypoint().getTarget());
        } else {
            points.add(currentPose.minus(currentVelocity));
            points.add(currentPose);
            points.add(p_segments.get(0).getWaypoint().getTarget());
        }
        //generate last point for interpolation, fill in if there are none
        if (index + 1 < p_segments.size()) {
            points.add(p_segments.get(index + 1).getWaypoint().getTarget());
        } else {
            isEndpoint = true;
            points.add(points.get(2).minus(points.get(1)).plus(points.get(2)));
        }
        ref = p_segments.get(index).getWaypoint();
        offset = p_segments.get(index).getTangentOffset();
    }

    public RFWaypoint CRerpWaypoint() {
        if (ref.getDefinedness() < 3) {
            Vector2d endVel = new Vector2d(terminalXVelocity(), terminalYVelocity());
            Vector2d curVel = currentVelocity.vec();
            double curTangent;
            if (Double.isNaN(curVel.angle())) {
                curTangent = currentPose.getHeading();
            } else {
                curTangent = curVel.angle();
            }
            double segTangent = points.get(2).minus(points.get(1)).vec().angle();
            double scaleFactor = Math.cos(endVel.angle() - curTangent - segTangent);
            packet.put("curTangent", curTangent);
            packet.put("segTangent", segTangent);

            if (Double.isNaN(scaleFactor)) {
                scaleFactor = 1;
            } else if (scaleFactor < 0) {
                scaleFactor = 0;
            }

            packet.put("scaleFac", scaleFactor);

            double distance = points.get(2).vec().distTo(points.get(1).vec());
            double curveFactor = CURVING_DISTANCE / distance;
            double targetVelocity = MAX_VEL * pow(scaleFactor, curveFactor * curveFactor * curveFactor);
            targetVelocity = min(targetVelocity, sqrt((points.get(2).vec()
                    .distTo(points.get(1).vec())) * 0.5 * MAX_ACCEL));
            packet.put("targetENdVelocity", targetVelocity);

            if (isEndpoint) {
                targetVelocity = 0;
            }
            if (ref.getDefinedness() < 2) {
                double endTangent = endVel.angle();
                packet.put("endTangent", endTangent);
                if (ref.getDefinedness() == 0) {
                    return new RFWaypoint(new Pose2d(ref.getTarget().getX(), ref.getTarget().getY(), endTangent + offset), endTangent
                            , targetVelocity, ref.getDefinedness());
                }
                if (ref.getDefinedness() == 1) {
                    return new RFWaypoint(ref.getTarget(), ref.getTarget().getHeading() + offset, targetVelocity, ref.getDefinedness());
                }
            } else {
                return new RFWaypoint(ref.getTarget(), ref.getEndTangent(), targetVelocity, ref.getDefinedness());
            }
        } else {
            return ref;
        }
        return ref;
    }

    public double distBetween(double angle1, double angle2) {
        double dist = angle1 - angle2;
        while (abs(dist) > toRadians(180)) {
            if (dist > toRadians(180)) {
                dist -= toRadians(180);
            } else if (dist < toRadians(-180)) {
                dist += toRadians(180);
            }
        }
        return abs(dist);
    }

    public double getEndTangent() {

        packet.put("terminalXVelo", terminalXVelocity());
        packet.put("terminalYVelo", terminalYVelocity());

        return atan2(terminalYVelocity(), terminalXVelocity());
    }

    public double terminalXVelocity() {
        double p0 = points.get(0).getX();
        double p1 = points.get(1).getX();
        double p2 = points.get(2).getX();
        double p3 = points.get(3).getX();
        double t01 = max(1, abs(p0 - p1));
        double t12 = max(1, abs(p1 - p2));
        double t23 = max(1, abs(p2 - p3));
        double m1 = ALPHA * (p2 - p1 + t12 * ((p1 - p0) / t01 - (p2 - p0) / (t01 + t12)));
        double m2 = ALPHA * (p2 - p1 + t12 * ((p3 - p2) / t23 - (p3 - p1) / (t12 + t23)));
        double a = 2.0 * (p1 - p2) + m1 + m2;
        double b = -3.0 * (p1 - p2) - m1 - m1 - m2;
        return (3 * a + 2 * b + m1);
    }

    public double terminalYVelocity() {
        double p0 = points.get(0).getY();
        double p1 = points.get(1).getY();
        double p2 = points.get(2).getY();
        double p3 = points.get(3).getY();
        double t01 = max(1, abs(p0 - p1));
        double t12 = max(1, abs(p1 - p2));
        double t23 = max(1, abs(p2 - p3));
        double m1 = ALPHA * (p2 - p1 + t12 * ((p1 - p0) / t01 - (p2 - p0) / (t01 + t12)));
        double m2 = ALPHA * (p2 - p1 + t12 * ((p3 - p2) / t23 - (p3 - p1) / (t12 + t23)));
        double a = 2.0 * (p1 - p2) + m1 + m2;
        double b = -3.0 * (p1 - p2) - m1 - m1 - m2;
        return (3 * a + 2 * b + m1);
    }
}
