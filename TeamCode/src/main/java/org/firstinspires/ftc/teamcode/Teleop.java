package org.firstinspires.ftc.teamcode;

import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.exception.RobotCoreException;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.IMU;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.usb.RobotArmingStateNotifier;
import com.qualcomm.robotcore.util.Range;

import java.util.HashMap;

@TeleOp(name="Teleop")
public class Teleop extends LinearOpMode {
    @Override
    public void runOpMode(){
        IMU imu = hardwareMap.get(IMU.class, "imu");
        robot R = new robot(hardwareMap);

        Gamepad currentGamepad1 = new Gamepad();
        Gamepad previousGamepad1 = new Gamepad();
        Gamepad currentGamepad2 = new Gamepad();
        Gamepad previousGamepad2 = new Gamepad();
        liftFSM LiftFSM = new liftFSM(R, telemetry, currentGamepad1,previousGamepad1);

        driveControls driveControls = new driveControls(R, currentGamepad1,previousGamepad1);

        waitForStart();
        LiftFSM.initialize();

        driveControls.initialize();

        while(opModeIsActive()) {
            // Previous gamepad implementation code
            previousGamepad1.copy(currentGamepad1);
            currentGamepad1.copy(gamepad1);
            previousGamepad2.copy(currentGamepad2);
            currentGamepad2.copy(gamepad2);
            LiftFSM.testUpdate();

            // Drive control update

            driveControls.drive();


            if (gamepad1.a && !previousGamepad1.a) {
                R.claw.setPosition(0.2);
            } else if (gamepad1.b && !previousGamepad1.b) {
                R.claw.setPosition(0.41);
            }

            if (gamepad1.dpad_left && !previousGamepad1.dpad_left) {
                R.arm.setPosition(0.37);
            } else if (gamepad1.dpad_up && !previousGamepad1.dpad_up) {
                R.arm.setPosition(0.934);
            } else if (gamepad1.dpad_down && !previousGamepad1.dpad_down) {
                R.arm.setPosition(0.3);
            }

            if (gamepad2.left_bumper && !previousGamepad2.left_bumper) {
                R.extendo.setPosition(0.63);
            } else if (gamepad2.right_bumper && !previousGamepad2.right_bumper) {
                R.extendo.setPosition(0.5);
            }


            if (gamepad2.x && !previousGamepad2.x) {
                R.intakeWrist.setPosition(0.0);
            } else if (gamepad2.y && !previousGamepad2.y) {
                R.intakeWrist.setPosition(0.9);
            } else if (gamepad2.a && !previousGamepad2.a) {
                R.intakeWrist.setPosition(0.5);
            }

            if (gamepad2.dpad_left && !previousGamepad2.dpad_left) {
                R.intakeClaw.setPosition(0.8);
            } else if (gamepad2.dpad_up && !previousGamepad2.dpad_up) {
                R.intakeClaw.setPosition(0.5);
            } else if (gamepad2.dpad_down && !previousGamepad2.dpad_down) {
                R.intakeClaw.setPosition(1);
            }

            // Update telemetry data
            telemetry.update();
        }
    }
}
