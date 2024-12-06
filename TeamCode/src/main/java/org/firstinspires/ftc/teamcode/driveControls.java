package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.util.Range;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.IMU;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.hardware.rev.RevHubOrientationOnRobot;
import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
/**
 * Drive Controls
 * <p>
 * Drive Controls class that powers mechanum drivetrain
 *
 *
 *
 //* @param an absolute URL giving the base location of the image
 //* @param name the location of the image, relative to the url argument
 * @return  the image at the specified URL
 * @see driveControls
 */
public class driveControls {
    robot R;

    Gamepad gamepad1;
    Gamepad previousGamepad1;
    IMU imu;

    double slow; // Slow multiplier for robot
    double regSpeed;
    double slowSpeed;

    public driveControls(robot Robot, Gamepad gp1,Gamepad pgp1) { this(0.7,0.25, Robot, gp1,pgp1); }

    public driveControls(double regularSpeed, double slowedSpeed, robot Robot, Gamepad gp1, Gamepad pgp1){
        regSpeed = regularSpeed;
        slowSpeed = slowedSpeed;
        R = Robot;
        gamepad1 = gp1;
        previousGamepad1 = pgp1;
    }

    public void initialize(){
        R.leftFront.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        R.leftRear.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        R.rightFront.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        R.rightRear.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        slow = regSpeed;
    }

    public void drive() {
        if (gamepad1.x && !previousGamepad1.x) {
            slow = (slow == regSpeed)? slowSpeed : regSpeed;
        }

        // Drive inputs
        double drive = -gamepad1.left_stick_y;//y
        double strafe = gamepad1.left_stick_x * 1.1;//x, smoother strafing
        double turn = gamepad1.right_stick_x;//rx

        // Drive calculations
        R.leftFront.setPower(calcPower(drive + turn + strafe, slow));
        R.leftRear.setPower(calcPower(drive - strafe + turn, slow));
        R.rightFront.setPower(calcPower(drive - turn - strafe, slow));
        R.rightRear.setPower(calcPower(drive + strafe - turn, slow));

    }
    //no strafing, left and right joysticks used to control power to either side
    public void tankDrive() {
        if (gamepad1.left_trigger >= 0.5 && previousGamepad1.left_trigger < 0.5) {
            slow = (slow == regSpeed)? slowSpeed : regSpeed;
        }
        double left = -gamepad1.left_stick_y;
        double right = -gamepad1.right_stick_y;

        R.leftRear.setPower(calcPower(left, slow));
        R.leftFront.setPower(calcPower(left, slow));
        R.rightRear.setPower(calcPower(right, slow));
        R.rightFront.setPower(calcPower(right, slow));

    }

    static double calcPower(double power, double slow) {
        //Returns the power^2 in the same direction as power bounded & multiplied by slow
        return Range.clip(power * power * Math.signum(power) * slow,-slow,slow);
    }
}