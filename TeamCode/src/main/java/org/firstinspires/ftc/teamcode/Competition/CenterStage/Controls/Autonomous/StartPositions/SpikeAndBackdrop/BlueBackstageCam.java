package org.firstinspires.ftc.teamcode.Competition.CenterStage.Controls.Autonomous.StartPositions.SpikeAndBackdrop;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.firstinspires.ftc.teamcode.Competition.CenterStage.Controls.Autonomous.AutoBlueAlliance;
import org.firstinspires.ftc.teamcode.Competition.CenterStage.Controls.TeamPropPosition;

@Autonomous (name = "Blue:Backstage:Start:Cam")
public class BlueBackstageCam extends AutoBlueAlliance {
    @Override
    public void runOpMode() throws InterruptedException {

//        Bot.initRobot(hardwareMap);
        Bot.initRobot(hardwareMap);
//        initCamera();
        initCamera();
        Bot.setLinearOp(this);
//        Bot.setLinearOp(this);
//        startPipeline(pipeline);
        startPipeline(pipeline);

        waitForStart();
        Bot.resetHeading();

        while (opModeIsActive()) {

//            CameraDetection();
            CameraDetection();
            //propPosition = TeamPropPosition.THREE;
            positionToDropBlueBack();


            requestOpModeStop();

        }
        idle();
    }
}
