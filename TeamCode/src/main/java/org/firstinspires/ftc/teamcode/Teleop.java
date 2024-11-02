package org.firstinspires.ftc.teamcode;

import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.exception.RobotCoreException;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.usb.RobotArmingStateNotifier;
import com.qualcomm.robotcore.util.Range;

import java.util.HashMap;

@TeleOp(name="Teleop")
public class Teleop extends LinearOpMode {
    @Override
    public void runOpMode(){
        robot R = new robot(hardwareMap);

        Gamepad currentGamepad1 = new Gamepad();
        Gamepad previousGamepad1 = new Gamepad();
        liftFSM LiftFSM = new liftFSM(R, telemetry, currentGamepad1,previousGamepad1);

        driveControls driveControls = new driveControls(R, currentGamepad1,previousGamepad1);

        waitForStart();
        LiftFSM.initialize();

        driveControls.initialize();

        while(opModeIsActive()) {
            // Previous gamepad implementation code
            previousGamepad1.copy(currentGamepad1);
            currentGamepad1.copy(gamepad1);
            LiftFSM.testUpdate();

            // Drive control update

            driveControls.drive();


            if (gamepad1.dpad_left && !previousGamepad1.dpad_left) {
                R.claw.setPosition(0);
            } else if (gamepad1.dpad_down && !previousGamepad1.dpad_down) {
                R.claw.setPosition(0.47);
            } else if (gamepad1.dpad_up && !previousGamepad1.dpad_up) {
                R.claw.setPosition(0.55);
            }

            if (gamepad1.a && !previousGamepad1.a) {
                R.arm.setPosition(0.3);
            } else if (gamepad1.b && !previousGamepad1.b) {
                R.arm.setPosition(0.5);
            }


            // Update telemetry data
            telemetry.update();
        }
    }
}
;