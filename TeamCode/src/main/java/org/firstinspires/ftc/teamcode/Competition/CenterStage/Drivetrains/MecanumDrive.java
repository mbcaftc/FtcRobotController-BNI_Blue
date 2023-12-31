package org.firstinspires.ftc.teamcode.Competition.CenterStage.Drivetrains;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;

import org.firstinspires.ftc.teamcode.Competition.CenterStage.Robots.BlueBot;

public class MecanumDrive extends OpMode {
    public DcMotor frontLeftMotor;
    public DcMotor frontRightMotor;
    public DcMotor rearLeftMotor;
    public DcMotor rearRightMotor;


    public LinearOpMode LinearOp = null;

    public static final double TICKS_PER_ROTATION = 386.3;

    public MecanumDrive() {

    }

    @Override
    public void init() {

    }

    @Override
    public void loop() {

    }

    public void setLinearOp(LinearOpMode LinearOp) {this.LinearOp = LinearOp;}

    public void setMotorRunModes(DcMotor.RunMode mode) {

        frontLeftMotor.setMode(mode);
        frontRightMotor.setMode(mode);
        rearLeftMotor.setMode(mode);
        rearRightMotor.setMode(mode);
    }

    public void stopMotors() {
        frontLeftMotor.setPower(0);
        frontRightMotor.setPower(0);
        rearRightMotor.setPower(0);
        rearLeftMotor.setPower(0);
    }

    public void driveForward(double speed) {
        frontLeftMotor.setPower(speed);
        frontRightMotor.setPower(speed);
        rearLeftMotor.setPower(speed);
        rearRightMotor.setPower(speed);
    }

    public void driveBack(double speed) {
        frontLeftMotor.setPower(-speed);
        frontRightMotor.setPower(-speed);
        rearLeftMotor.setPower(-speed);
        rearRightMotor.setPower(-speed);
    }

    public void strafeLeft(double speed) {
       //old strafe left
//        frontLeftMotor.setPower(-speed);
//        frontRightMotor.setPower(speed);
//        rearLeftMotor.setPower(speed);
//        rearRightMotor.setPower(-speed);
        //new strafe left
        frontLeftMotor.setPower(-speed);
        frontRightMotor.setPower(speed);
        rearLeftMotor.setPower(-speed);
        rearRightMotor.setPower(speed);
    }

    public void strafeRight(double speed) {
        //old strafe right
//        frontLeftMotor.setPower(speed);
//        frontRightMotor.setPower(-speed);
//        rearLeftMotor.setPower(-speed);
//        rearRightMotor.setPower(speed);
        //new strafe right
        frontLeftMotor.setPower(speed);
        frontRightMotor.setPower(-speed);
        rearLeftMotor.setPower(speed);
        rearRightMotor.setPower(-speed);
    }

    public void rotateLeft(double speed) {
        //old rotate left
//        frontLeftMotor.setPower(-speed);
//        frontRightMotor.setPower(speed);
//        rearLeftMotor.setPower(-speed);
//        rearRightMotor.setPower(speed);
        //new rotate left
        frontLeftMotor.setPower(-speed);
        frontRightMotor.setPower(speed);
        rearLeftMotor.setPower(speed);
        rearRightMotor.setPower(-speed);
    }

    public void rotateRight(double speed) {
        //old rotate right
//        frontLeftMotor.setPower(speed);
//        frontRightMotor.setPower(-speed);
//        rearLeftMotor.setPower(speed);
//        rearRightMotor.setPower(-speed);
        //new rotate right
        frontLeftMotor.setPower(speed);
        frontRightMotor.setPower(-speed);
        rearLeftMotor.setPower(-speed);
        rearRightMotor.setPower(speed);
    }

    public void diagonalLeftForward(double speed) {
        frontRightMotor.setPower(speed);
        rearLeftMotor.setPower(speed);
    }

    public void diagonalRightForward(double speed) {
        frontLeftMotor.setPower(speed);
        rearRightMotor.setPower(speed);
    }
    public void diagonalLeftBack(double speed) {
        frontLeftMotor.setPower(-speed);
        rearRightMotor.setPower(-speed);
    }

    public void diagonalRightBack(double speed) {
        frontRightMotor.setPower(-speed);
        rearLeftMotor.setPower(-speed);
    }



    public void driveForward(double speed, double rotations) {

        double ticks = rotations  * TICKS_PER_ROTATION;
        setMotorRunModes(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        setMotorRunModes(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

        while ((Math.abs(frontLeftMotor.getCurrentPosition() ) < ticks && LinearOp.opModeIsActive()) ) {
            driveForward(speed);
            LinearOp.telemetry.addData("FL tickes", frontLeftMotor.getCurrentPosition());
            LinearOp.telemetry.addData("FR tickes", frontRightMotor.getCurrentPosition());
            LinearOp.telemetry.addData("RL tickes", rearLeftMotor.getCurrentPosition());
            LinearOp.telemetry.addData("RR tickes", rearRightMotor.getCurrentPosition());
            LinearOp.telemetry.update();
        }
        stopMotors();
    }

    public void driveBack (double speed, double rotations) {
        double ticks = rotations  * TICKS_PER_ROTATION;
        setMotorRunModes(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        setMotorRunModes(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

        while ((Math.abs(frontLeftMotor.getCurrentPosition() ) < ticks && LinearOp.opModeIsActive() ) ){
            driveBack(speed);
            LinearOp.telemetry.addData("FL tickes", frontLeftMotor.getCurrentPosition());
            LinearOp.telemetry.addData("FR tickes", frontRightMotor.getCurrentPosition());
            LinearOp.telemetry.addData("RL tickes", rearLeftMotor.getCurrentPosition());
            LinearOp.telemetry.addData("RR tickes", rearRightMotor.getCurrentPosition());
            LinearOp.telemetry.update();
        }
        stopMotors();

    }

    public void rotateLeft(double speed, double rotations) {
        double ticks = rotations * TICKS_PER_ROTATION;
        setMotorRunModes(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        setMotorRunModes(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

        while ((Math.abs(frontLeftMotor.getCurrentPosition() ) < ticks && LinearOp.opModeIsActive()) ){
            rotateLeft(speed);
            LinearOp.telemetry.addData("FL tickes", frontLeftMotor.getCurrentPosition());
            LinearOp.telemetry.addData("FR tickes", frontRightMotor.getCurrentPosition());
            LinearOp.telemetry.addData("RL tickes", rearLeftMotor.getCurrentPosition());
            LinearOp.telemetry.addData("RR tickes", rearRightMotor.getCurrentPosition());
            LinearOp.telemetry.update();
        }
        stopMotors();
    }

    public void rotateRight(double speed, double rotations) {
        double ticks = rotations * TICKS_PER_ROTATION;
        setMotorRunModes(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        setMotorRunModes(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

        while ((Math.abs(frontLeftMotor.getCurrentPosition() ) < ticks && LinearOp.opModeIsActive()) ) {
            rotateRight(speed);
            LinearOp.telemetry.addData("FL tickes", frontLeftMotor.getCurrentPosition());
            LinearOp.telemetry.addData("FR tickes", frontRightMotor.getCurrentPosition());
            LinearOp.telemetry.addData("RL tickes", rearLeftMotor.getCurrentPosition());
            LinearOp.telemetry.addData("RR tickes", rearRightMotor.getCurrentPosition());
            LinearOp.telemetry.update();
        }
        stopMotors();

    }

    public void strafeRight(double speed, double rotations) {
        double ticks = rotations * TICKS_PER_ROTATION;
        setMotorRunModes(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        setMotorRunModes(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

        while ((Math.abs(frontLeftMotor.getCurrentPosition() ) < ticks) && LinearOp.opModeIsActive()) {
            strafeRight(speed);
            LinearOp.telemetry.addData("FL tickes", frontLeftMotor.getCurrentPosition());
            LinearOp.telemetry.addData("FR tickes", frontRightMotor.getCurrentPosition());
            LinearOp.telemetry.addData("RL tickes", rearLeftMotor.getCurrentPosition());
            LinearOp.telemetry.addData("RR tickes", rearRightMotor.getCurrentPosition());
            LinearOp.telemetry.update();
        }
        stopMotors();
    }

    public void strafeLeft(double speed, double rotations) {
        double ticks = rotations * TICKS_PER_ROTATION;
        setMotorRunModes(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        setMotorRunModes(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

        while ((Math.abs(frontLeftMotor.getCurrentPosition() ) < ticks ) && LinearOp.opModeIsActive()) {
            strafeLeft(speed);
            LinearOp.telemetry.addData("FL tickes", frontLeftMotor.getCurrentPosition());
            LinearOp.telemetry.addData("FR tickes", frontRightMotor.getCurrentPosition());
            LinearOp.telemetry.addData("RL tickes", rearLeftMotor.getCurrentPosition());
            LinearOp.telemetry.addData("RR tickes", rearRightMotor.getCurrentPosition());
            LinearOp.telemetry.update();
        }
        stopMotors();


    }




    public void diagonalLeftForward(double speed, double rotations) {

        double ticks = rotations  * TICKS_PER_ROTATION;
        setMotorRunModes(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        setMotorRunModes(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

        while ((Math.abs(frontLeftMotor.getCurrentPosition() ) < ticks && LinearOp.opModeIsActive()) ) {
            diagonalLeftForward(speed);
        }
        stopMotors();
    }

    public void diagonalRightForward(double speed, double rotations) {

        double ticks = rotations  * TICKS_PER_ROTATION;
        setMotorRunModes(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        setMotorRunModes(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

        while ((Math.abs(frontLeftMotor.getCurrentPosition() ) < ticks && LinearOp.opModeIsActive()) ) {
            diagonalRightForward(speed);
        }
        stopMotors();
    }

    public void diagonalLeftBack(double speed, double rotations) {

        double ticks = rotations  * TICKS_PER_ROTATION;
        setMotorRunModes(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        setMotorRunModes(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

        while ((Math.abs(frontLeftMotor.getCurrentPosition() ) < ticks && LinearOp.opModeIsActive()) ) {
            diagonalLeftBack(speed);
        }
        stopMotors();
    }

    public void diagonalRightBack (double speed, double rotations) {

        double ticks = rotations  * TICKS_PER_ROTATION;
        setMotorRunModes(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        setMotorRunModes(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

        while ((Math.abs(frontLeftMotor.getCurrentPosition() ) < ticks && LinearOp.opModeIsActive()) ) {
            diagonalRightBack(speed);
        }
        stopMotors();
    }




}
