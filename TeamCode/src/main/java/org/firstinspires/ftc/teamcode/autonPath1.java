package org.firstinspires.ftc.teamcode;

import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.acmerobotics.roadrunner.geometry.Vector2d;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Gamepad;


import org.firstinspires.ftc.teamcode.trajectorysequence.TrajectorySequence;


import java.util.concurrent.atomic.AtomicReference;

@Autonomous(name="auton1")
public class autonPath1 extends LinearOpMode {

    @Override
    public void runOpMode() throws InterruptedException {
        robot R = new robot(hardwareMap);

        Gamepad currentGamepad1 = new Gamepad();
        Gamepad previousGamepad1 = new Gamepad();

        R.leftFront.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        R.leftRear.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        R.rightFront.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        R.rightRear.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        liftFSM LiftFSM = new liftFSM(R, telemetry, currentGamepad1, previousGamepad1);

        AtomicReference<liftFSM.LiftState> liftState = new AtomicReference<>(liftFSM.LiftState.ZERO);

        TrajectorySequence path1 = R.trajectorySequenceBuilder(new Pose2d(0, 0,Math.toRadians(0)))
            .turn(Math.toRadians(90))
            .splineTo(new Vector2d(-60.0, -60.0), Math.toRadians(180))
            .addTemporalMarker(0, () -> {
                while (true) {

                }
            })
            .build();

        TrajectorySequence path2 = R.trajectorySequenceBuilder(new Pose2d(0, 0,Math.toRadians(0)))
            .turn(Math.toRadians(-90))
            .forward(120.0)
            .addTemporalMarker(0, () -> {
                while (true) {

                }
            })
            .build();

        waitForStart();

        R.followTrajectorySequence(path1);

        int count = 0;
        while(count < 4){
            R.followTrajectorySequence(path2);
            count++;
        }
    }
}
