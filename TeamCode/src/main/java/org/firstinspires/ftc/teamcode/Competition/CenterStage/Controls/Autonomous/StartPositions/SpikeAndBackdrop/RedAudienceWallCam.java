package org.firstinspires.ftc.teamcode.Competition.CenterStage.Controls.Autonomous.StartPositions.SpikeAndBackdrop;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.firstinspires.ftc.teamcode.Competition.CenterStage.Controls.Autonomous.AutoRedAlliance;
import org.firstinspires.ftc.teamcode.Competition.CenterStage.Controls.TeamPropPosition;


@Autonomous(name = "Red:Audience:Wall:Cam")
public class RedAudienceWallCam extends AutoRedAlliance {
    @Override
    public void runOpMode() throws InterruptedException {
        Bot.initRobot(hardwareMap);
        initCamera();
        Bot.setLinearOp(this);
        startPipeline(pipeline);

        waitForStart();
        Bot.resetHeading();

        while(opModeIsActive()){
            CameraDetection();
            //propPosition = TeamPropPosition.FOUR;
            positionToDropRedAudWall();

            requestOpModeStop();
        }
        idle();
    }
}
