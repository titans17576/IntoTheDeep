package org.firstinspires.ftc.teamcode;

        import com.acmerobotics.roadrunner.geometry.Pose2d;
        import com.acmerobotics.roadrunner.trajectory.Trajectory;
        import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
        import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
        import com.qualcomm.robotcore.hardware.DcMotor;
        import com.qualcomm.robotcore.hardware.Gamepad;
        import org.firstinspires.ftc.teamcode.liftFSM;
        import org.firstinspires.ftc.teamcode.robot;
        import org.firstinspires.ftc.teamcode.trajectorysequence.TrajectorySequence;

        import java.util.concurrent.atomic.AtomicReference;

@Autonomous(name="meet1Auto")
public class meet1Auto extends LinearOpMode {


    @Override
    public void runOpMode() throws InterruptedException {
        robot R = new robot(hardwareMap);


        Gamepad currentGamepad1 = new Gamepad();
        Gamepad previousGamepad1 = new Gamepad();

        R.leftFront.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        R.leftRear.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        R.rightFront.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        R.rightRear.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        //liftFSM LiftFSM = new liftFSM(R, telemetry, currentGamepad1, previousGamepad1);

        //AtomicReference<liftFSM.LiftState> liftState = new AtomicReference<>(liftFSM.LiftState.ZERO);

        Trajectory m = R.trajectoryBuilder(new Pose2d())
                .strafeRight(23)
                .build();

        Trajectory g = R.trajectoryBuilder(new Pose2d())
                .strafeRight(46)
                .build();


        TrajectorySequence path1 = R.trajectorySequenceBuilder(new Pose2d(0, 0,Math.toRadians(0)))
                .forward(36.0)
                .build();


        waitForStart();


        R.followTrajectorySequence(path1);


    }
}
