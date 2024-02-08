package org.firstinspires.ftc.teamcode.Competition.CenterStage.Drivetrains;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.IMU;
import com.qualcomm.robotcore.util.Range;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.YawPitchRollAngles;
import org.firstinspires.ftc.teamcode.Competition.CenterStage.Robots.BlueBot;

public class MecanumDrive {// Instance Variables for Mecanum Motors
    public DcMotor frontLeftMotor;
    public DcMotor frontRightMotor;
    public DcMotor rearLeftMotor;
    public DcMotor rearRightMotor;

    public static final double TICKS_PER_ROTATION = 386.3;

    // Instance Variables for IMU
    public IMU imu = null;
    public double headingTolerance = 0.5;
    public double currentHeading = 0;

    //Instance Variables for Acceleration
    public double currentDistance = frontLeftMotor.getCurrentPosition();
    public double power;

    // Instance Variables for PID Coefficients
    private double integralSum = 0;
    private double lastError = 0;
    PIDController translationPID = new PIDController(0.1, 0.01, 0.1);
    PIDController rotationPID = new PIDController(0.1, 0.01, 0.1);

    // Instance Variable for Linear Op Mode
    public LinearOpMode LinearOp = null;

    // Default Constructor for Mecanum Drive Class
    public MecanumDrive() {}


    //********  Helper Methods for the Class  ************

    // Helper Method for Linear Op
    public void setLinearOp(LinearOpMode LinearOp) {
        this.LinearOp = LinearOp;
    }

    // Helper method to set the run modes for all motors at the same
    public void setMotorRunModes(DcMotor.RunMode mode) {
        frontLeftMotor.setMode(mode);
        frontRightMotor.setMode(mode);
        rearLeftMotor.setMode(mode);
        rearRightMotor.setMode(mode);
    }

    //******  Methods using IMU / Gyro  **************

    // Helper Method to Get Heading using IMU
    public double getHeading() {
        YawPitchRollAngles orientation = imu.getRobotYawPitchRollAngles();
        return orientation.getYaw(AngleUnit.DEGREES);
    }

    // Helper Method to reset the IMU Yaw Heading
    public void resetHeading() {
        imu.resetYaw();
    }

    // Method that corrects the robots original heading.
    // Method assumes the heading to correct to has been set outside of this method
    public void gyroCorrection(double speed, double targetAngle) {
        currentHeading = getHeading();
        if (currentHeading >= targetAngle + headingTolerance && LinearOp.opModeIsActive()) {
            while (currentHeading >= targetAngle + headingTolerance && LinearOp.opModeIsActive()) {
                rotateRight(speed);

                currentHeading = getHeading();
                LinearOp.telemetry.addData("Current Angle: ", currentHeading);
                LinearOp.telemetry.addData("Target Angle: ", targetAngle);
                LinearOp.telemetry.update();
            }
        } else  if (currentHeading <= targetAngle - headingTolerance && LinearOp.opModeIsActive()) ;
        {
            while (currentHeading <= targetAngle - headingTolerance && LinearOp.opModeIsActive()) {
                rotateLeft(speed);

                currentHeading = getHeading();
                LinearOp.telemetry.addData("Current Angle: ", currentHeading);
                LinearOp.telemetry.addData("Target Angle: ", targetAngle);
                LinearOp.telemetry.update();
            }
        }
        stopMotors();
        currentHeading = getHeading();
    }

    // Method allows robot to rotate using the IMU Yaw Heading
    // Method resets the heading so there is a full rotation based on targetAngle
    public void rotateByGyro(double speed, double targetAngle) {
        resetHeading();
        currentHeading = getHeading();
        if (currentHeading >= targetAngle + headingTolerance && LinearOp.opModeIsActive()) {
            while (currentHeading >= targetAngle + headingTolerance && LinearOp.opModeIsActive()) {
                rotateRight(speed);

                currentHeading = getHeading();
                LinearOp.telemetry.addData("Current Angle: ", currentHeading);
                LinearOp.telemetry.addData("Target Angle: ", targetAngle);
                LinearOp.telemetry.update();
            }
        } else if (currentHeading <= targetAngle - headingTolerance && LinearOp.opModeIsActive()) ;
        {
            while (currentHeading <= targetAngle - headingTolerance && LinearOp.opModeIsActive()) {
                rotateLeft(speed);

                currentHeading = getHeading();
                LinearOp.telemetry.addData("Current Angle: ", currentHeading);
                LinearOp.telemetry.addData("Target Angle: ", targetAngle);
                LinearOp.telemetry.update();
            }
        }
        stopMotors();
        currentHeading = getHeading();
    }

    // Method to drive straight either forward or backward using IMU
    public void driveGyroStraight(double rotations, double power, String direction) throws InterruptedException {
        double ticks = rotations * TICKS_PER_ROTATION;


        imu.resetYaw();
        currentHeading = getHeading();

        double target = getHeading();
        double currentPos = 0;
        double leftSideSpeed = 0;
        double rightSideSpeed = 0;

        double startPosition = frontLeftMotor.getCurrentPosition();
        LinearOp.sleep(100);
        while (currentPos < ticks + startPosition && LinearOp.opModeIsActive()) {
            currentHeading = getHeading();
            currentPos = Math.abs(frontLeftMotor.getCurrentPosition());
//VEERING TO LEFT!
            switch (direction) {
                case "FORWARD":
                    leftSideSpeed = power + (currentHeading - target) / 75;            // they need to be different
                    rightSideSpeed = power - (currentHeading - target) / 75;   //100



                    leftSideSpeed = Range.clip(leftSideSpeed, -1, 1);        // helps prevent out of bounds error
                    rightSideSpeed = Range.clip(rightSideSpeed, -1, 1);

                    frontLeftMotor.setPower(leftSideSpeed);
                    rearLeftMotor.setPower(leftSideSpeed);

                    frontRightMotor.setPower(rightSideSpeed);
                    rearRightMotor.setPower(rightSideSpeed);
                    break;
                case "BACK":
                    leftSideSpeed = power - (currentHeading - target) / 75;            // they need to be different
                    rightSideSpeed = power + (currentHeading - target) / 75;

                    leftSideSpeed = Range.clip(leftSideSpeed, -1, 1);        // helps prevent out of bounds error
                    rightSideSpeed = Range.clip(rightSideSpeed, -1, 1);

                    frontLeftMotor.setPower(-leftSideSpeed);
                    rearLeftMotor.setPower(-leftSideSpeed);


                    frontRightMotor.setPower(-rightSideSpeed);
                    rearRightMotor.setPower(-rightSideSpeed);
                    break;
                case "LEFT":
                    leftSideSpeed = power - (currentHeading - target) / 100;            // they need to be different
                    rightSideSpeed = power + (currentHeading - target) / 100;

                    leftSideSpeed = Range.clip(leftSideSpeed, -1, 1);        // helps prevent out of bounds error
                    rightSideSpeed = Range.clip(rightSideSpeed, -1, 1);

                    frontLeftMotor.setPower(leftSideSpeed);
                    rearLeftMotor.setPower(-leftSideSpeed);


                    frontRightMotor.setPower(-rightSideSpeed);
                    rearRightMotor.setPower(rightSideSpeed);
                    break;
                case "RIGHT":
                    leftSideSpeed = power - (currentHeading - target) / 100;            // they need to be different
                    rightSideSpeed = power + (currentHeading - target) / 100;

                    leftSideSpeed = Range.clip(leftSideSpeed, -1, 1);        // helps prevent out of bounds error
                    rightSideSpeed = Range.clip(rightSideSpeed, -1, 1);

                    frontLeftMotor.setPower(-leftSideSpeed);
                    rearLeftMotor.setPower(leftSideSpeed);

                    frontRightMotor.setPower(rightSideSpeed);
                    rearRightMotor.setPower(-rightSideSpeed);
                    break;
            }

            LinearOp.telemetry.addData("Left Speed", leftSideSpeed);
            LinearOp.telemetry.addData("Right Speed", rightSideSpeed);
            LinearOp.telemetry.addData("Distance till destination ", ticks + startPosition - frontLeftMotor.getCurrentPosition());
            LinearOp.telemetry.addData("Current Position", currentPos);
            LinearOp.telemetry.addData("Target Position", target);
            LinearOp.telemetry.addData("Current Heading: ", currentHeading);
            LinearOp.telemetry.update();

            LinearOp.idle();
        }

        frontLeftMotor.setPower(0);
        frontRightMotor.setPower(0);
        rearLeftMotor.setPower(0);
        rearRightMotor.setPower(0);

        LinearOp.idle();

    }


    // ************** Basic Drive Method ***********************

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
        frontLeftMotor.setPower(-speed);
        frontRightMotor.setPower(speed);
        rearLeftMotor.setPower(-speed);
        rearRightMotor.setPower(speed);
    }

    public void strafeRight(double speed) {
        frontLeftMotor.setPower(speed);
        frontRightMotor.setPower(-speed);
        rearLeftMotor.setPower(speed);
        rearRightMotor.setPower(-speed);
    }

    public void rotateLeft(double speed) {
        frontLeftMotor.setPower(-speed);
        frontRightMotor.setPower(speed);
        rearLeftMotor.setPower(speed);
        rearRightMotor.setPower(-speed);
    }

    public void rotateRight(double speed) {
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

    // **********  Drive Method using Encoders *******************

    public void driveForward(double speed, double rotations) {

        double ticks = rotations * TICKS_PER_ROTATION;
        setMotorRunModes(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        setMotorRunModes(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

        while ((Math.abs(frontLeftMotor.getCurrentPosition()) < ticks && LinearOp.opModeIsActive())) {
            driveForward(speed);
            LinearOp.telemetry.addData("FL ticks", frontLeftMotor.getCurrentPosition());
            LinearOp.telemetry.addData("FR ticks", frontRightMotor.getCurrentPosition());
            LinearOp.telemetry.addData("RL ticks", rearLeftMotor.getCurrentPosition());
            LinearOp.telemetry.addData("RR ticks", rearRightMotor.getCurrentPosition());
            LinearOp.telemetry.update();
        }
        stopMotors();
    }

    public void driveBack(double speed, double rotations) {
        double ticks = rotations * TICKS_PER_ROTATION;
        setMotorRunModes(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        setMotorRunModes(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

        while ((Math.abs(frontLeftMotor.getCurrentPosition()) < ticks && LinearOp.opModeIsActive())) {
            driveBack(speed);
            LinearOp.telemetry.addData("FL ticks", frontLeftMotor.getCurrentPosition());
            LinearOp.telemetry.addData("FR ticks", frontRightMotor.getCurrentPosition());
            LinearOp.telemetry.addData("RL ticks", rearLeftMotor.getCurrentPosition());
            LinearOp.telemetry.addData("RR ticks", rearRightMotor.getCurrentPosition());
            LinearOp.telemetry.update();
        }
        stopMotors();

    }

    public void rotateLeft(double speed, double rotations) {
        double ticks = rotations * TICKS_PER_ROTATION;
        setMotorRunModes(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        setMotorRunModes(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

        while ((Math.abs(frontLeftMotor.getCurrentPosition()) < ticks && LinearOp.opModeIsActive())) {
            rotateLeft(speed);
            LinearOp.telemetry.addData("FL ticks", frontLeftMotor.getCurrentPosition());
            LinearOp.telemetry.addData("FR ticks", frontRightMotor.getCurrentPosition());
            LinearOp.telemetry.addData("RL ticks", rearLeftMotor.getCurrentPosition());
            LinearOp.telemetry.addData("RR ticks", rearRightMotor.getCurrentPosition());
            LinearOp.telemetry.update();
        }
        stopMotors();
    }

    public void rotateRight(double speed, double rotations) {
        double ticks = rotations * TICKS_PER_ROTATION;
        setMotorRunModes(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        setMotorRunModes(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

        while ((Math.abs(frontLeftMotor.getCurrentPosition()) < ticks && LinearOp.opModeIsActive())) {
            rotateRight(speed);
            LinearOp.telemetry.addData("FL ticks", frontLeftMotor.getCurrentPosition());
            LinearOp.telemetry.addData("FR ticks", frontRightMotor.getCurrentPosition());
            LinearOp.telemetry.addData("RL ticks", rearLeftMotor.getCurrentPosition());
            LinearOp.telemetry.addData("RR ticks", rearRightMotor.getCurrentPosition());
            LinearOp.telemetry.update();
        }
        stopMotors();

    }

    public void strafeRight(double speed, double rotations) {
        double ticks = rotations * TICKS_PER_ROTATION;
        setMotorRunModes(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        setMotorRunModes(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

        while ((Math.abs(frontLeftMotor.getCurrentPosition()) < ticks) && LinearOp.opModeIsActive()) {
            strafeRight(speed);
            LinearOp.telemetry.addData("FL ticks", frontLeftMotor.getCurrentPosition());
            LinearOp.telemetry.addData("FR ticks", frontRightMotor.getCurrentPosition());
            LinearOp.telemetry.addData("RL ticks", rearLeftMotor.getCurrentPosition());
            LinearOp.telemetry.addData("RR ticks", rearRightMotor.getCurrentPosition());
            LinearOp.telemetry.update();
        }
        stopMotors();
    }

    public void strafeLeft(double speed, double rotations) {
        double ticks = rotations * TICKS_PER_ROTATION;
        setMotorRunModes(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        setMotorRunModes(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

        while ((Math.abs(frontLeftMotor.getCurrentPosition()) < ticks) && LinearOp.opModeIsActive()) {
            strafeLeft(speed);
            LinearOp.telemetry.addData("FL ticks", frontLeftMotor.getCurrentPosition());
            LinearOp.telemetry.addData("FR ticks", frontRightMotor.getCurrentPosition());
            LinearOp.telemetry.addData("RL ticks", rearLeftMotor.getCurrentPosition());
            LinearOp.telemetry.addData("RR ticks", rearRightMotor.getCurrentPosition());
            LinearOp.telemetry.update();
        }
        stopMotors();


    }

    public void diagonalLeftForward(double speed, double rotations) {

        double ticks = rotations * TICKS_PER_ROTATION;
        setMotorRunModes(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        setMotorRunModes(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

        while ((Math.abs(frontLeftMotor.getCurrentPosition()) < ticks && LinearOp.opModeIsActive())) {
            diagonalLeftForward(speed);
        }
        stopMotors();
    }

    public void diagonalRightForward(double speed, double rotations) {

        double ticks = rotations * TICKS_PER_ROTATION;
        setMotorRunModes(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        setMotorRunModes(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

        while ((Math.abs(frontLeftMotor.getCurrentPosition()) < ticks && LinearOp.opModeIsActive())) {
            diagonalRightForward(speed);
        }
        stopMotors();
    }

    public void diagonalLeftBack(double speed, double rotations) {

        double ticks = rotations * TICKS_PER_ROTATION;
        setMotorRunModes(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        setMotorRunModes(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

        while ((Math.abs(frontLeftMotor.getCurrentPosition()) < ticks && LinearOp.opModeIsActive())) {
            diagonalLeftBack(speed);
        }
        stopMotors();
    }

    public void diagonalRightBack(double speed, double rotations) {

        double ticks = rotations * TICKS_PER_ROTATION;
        setMotorRunModes(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        setMotorRunModes(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

        while ((Math.abs(frontLeftMotor.getCurrentPosition()) < ticks && LinearOp.opModeIsActive())) {
            diagonalRightBack(speed);
        }
        stopMotors();
    }


    // Drive Using to Run To Position
    public void driveForwardToPosition(double speed, double rotations) {

        int targetPosition = (int) (rotations * TICKS_PER_ROTATION);


        // Reset Encoder Counts.
        frontLeftMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        frontRightMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        rearLeftMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        rearRightMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        // Set motor run modes to RUN_TO_POSITION
        frontLeftMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        frontRightMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        rearLeftMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        rearRightMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        // Set target positions for all motors
        frontLeftMotor.setTargetPosition(targetPosition);
        frontRightMotor.setTargetPosition(targetPosition);
        rearLeftMotor.setTargetPosition(targetPosition);
        rearRightMotor.setTargetPosition(targetPosition);

        // Set motor power to move forward
        double power = speed; // Example power level, adjust as needed
        frontLeftMotor.setPower(power);
        frontRightMotor.setPower(power);
        rearLeftMotor.setPower(power);
        rearRightMotor.setPower(power);

        // Loop until one of the motors reach their target positions
        while (LinearOp.opModeIsActive() && frontLeftMotor.isBusy() && frontRightMotor.isBusy()
                && rearLeftMotor.isBusy() && rearRightMotor.isBusy()) {
            // You can add additional logic here if needed
            LinearOp.telemetry.addData("Status", "Driving to position");
            LinearOp.telemetry.update();
        }

        // Stop all motors
        frontLeftMotor.setPower(0);
        frontRightMotor.setPower(0);
        rearLeftMotor.setPower(0);
        rearRightMotor.setPower(0);

        // Set motor run modes back to RUN_USING_ENCODER
        frontLeftMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        frontRightMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        rearLeftMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        rearRightMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

        // Display a message when the movement is complete
        LinearOp.telemetry.addData("Status", "Movement complete");
        LinearOp.telemetry.update();
    }


//    // Method to drive robot using PID control
//    public void drivePIDForward(double targetDistance, double power, double p, double i, double d) {
//        resetEncoders();
//
//        double error;
//        double derivative;
//        double output;
//        double distance = getEncoderAvgDistance();
//
//        while (Math.abs(targetDistance - distance) > 100 && LinearOp.opModeIsActive()) { // 1 is the tolerance, you can adjust it
//            LinearOp.telemetry.addData("targetDistance", targetDistance);
//            LinearOp.telemetry.addData("distance", distance);
//            distance = getEncoderAvgDistance();
//            error = targetDistance - distance;
//
//            integralSum += error;
//            derivative = error - lastError;
//
//            output = (p * error) + (i * integralSum) + (d * derivative);
//
//            driveForward(output * power);
//
//            lastError = error;
//
//            // Add a small delay to avoid hogging CPU cycles
//            try {
//                Thread.sleep(10);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//            getMotorTelemetry();
//            LinearOp.telemetry.update();
//
//        }
//        stopMotors();
//    }

    // Method to move robot forward/backward or strafe based on desired trajectory
    public void moveRobot(double drive, double strafe, double yaw) {

        // Calculates the power for each wheel / motor
        double frontLeftPower = drive - strafe - yaw;
        double frontRightPower = drive + strafe + yaw;
        double rearLeftPower = drive + strafe - yaw;
        double rearRightPower = drive - strafe + yaw;

        // Normalize wheel powers to ensure values are within -1.0 to 1.0
        double max = Math.max(Math.max(Math.abs(frontLeftPower), Math.abs(frontRightPower)),
                Math.max(Math.abs(rearLeftPower), Math.abs(rearRightPower)));

        if (max > 1.0) {
            frontLeftPower /= max;
            frontRightPower /= max;
            rearLeftPower /= max;
            rearRightPower /= max;
        }

        // Send powers to the wheels
        frontLeftMotor.setPower(frontLeftPower);
        frontRightMotor.setPower(frontRightPower);
        rearLeftMotor.setPower(rearLeftPower);
        rearRightMotor.setPower(rearRightPower);
    }



    // *********  Helper methods for Encoders******************

    // Helper Method to reset encoders
    public void resetEncoders() {
        frontLeftMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        frontRightMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        rearLeftMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        rearRightMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        frontLeftMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        frontRightMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        rearLeftMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        rearRightMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
    }

    // Helper Method that averages all the encoder counts using getPosition
    public double getEncoderAvgDistance() {
        double average = (frontLeftMotor.getCurrentPosition() + frontRightMotor.getCurrentPosition() + rearLeftMotor.getCurrentPosition() + rearRightMotor.getCurrentPosition()) / 4.0;
        return Math.abs(average);
    }

    // Helper Method to get Motor Telemetry
    public void getMotorTelemetry() {
        LinearOp.telemetry.addData("FLM", frontLeftMotor.getCurrentPosition());
        LinearOp.telemetry.addData("FRM", frontRightMotor.getCurrentPosition());
        LinearOp.telemetry.addData("RLM", rearLeftMotor.getCurrentPosition());
        LinearOp.telemetry.addData("RRM", rearRightMotor.getCurrentPosition());
    }

    //***************** Holonomic PID Control **************************


    // Helper Method for Distance Conversion
    public double inchesToTicks (double inches) {
        final double TICKS_PER_REV = 537.7;
        final double GEAR_RATIO = 1.0;
        final double WHEEL_DIAMETER_INCHES = 3.77953;
        final double INCHES_PER_REV = Math.PI * WHEEL_DIAMETER_INCHES * GEAR_RATIO;
        return (inches / INCHES_PER_REV) * TICKS_PER_REV;
    }

    // Helper Method for Distance Conversion
    public double ticksToInches(double ticks) {
        final double TICKS_PER_REV = 537.7;
        final double GEAR_RATIO = 1.0;
        final double WHEEL_CIRCUMFERENCE = 3.77953;      // 96mm
        return (ticks / TICKS_PER_REV) * WHEEL_CIRCUMFERENCE * GEAR_RATIO;
    }

    // Helper Method for Mecanum wheel formula for X movement
    public double getCurrentX() {
        double flDistance = frontLeftMotor.getCurrentPosition();
        double frDistance = frontRightMotor.getCurrentPosition();
        double blDistance = rearLeftMotor.getCurrentPosition();
        double brDistance = rearRightMotor.getCurrentPosition();
        return (flDistance + frDistance + blDistance + brDistance) / 4;
    }

    // Helper Method for Mecanum wheel formula for Y movement
    public double getCurrentY() {
        double flDistance = frontLeftMotor.getCurrentPosition();
        double frDistance = frontRightMotor.getCurrentPosition();
        double blDistance = rearLeftMotor.getCurrentPosition();
        double brDistance = rearRightMotor.getCurrentPosition();
        return (-flDistance + frDistance + blDistance - brDistance) / 4;
    }

    public void speedAcceleration(double targetDistance, double maxPower) {
        double accelerationDistance = targetDistance * 0.2;
        double decelerationDistance = targetDistance * 0.1;

        //Inside While Loop
        if (currentDistance < accelerationDistance) {
            power = maxPower * (currentDistance / accelerationDistance);
        } else if (currentDistance > targetDistance - decelerationDistance) {
            power = maxPower * ((targetDistance - currentDistance) / decelerationDistance);
        } else {
            power = maxPower;
        }
        frontLeftMotor.setPower(power);
        frontRightMotor.setPower(power);
        rearLeftMotor.setPower(power);
        rearRightMotor.setPower(power);
        try {
            Thread.sleep(10);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();//re-interrupt the thread
        }
    }

}
