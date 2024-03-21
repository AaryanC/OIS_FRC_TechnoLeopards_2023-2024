// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import java.util.List;

import org.photonvision.PhotonCamera;
import org.photonvision.targeting.PhotonTrackedTarget;

import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class PhotonSubsystem extends SubsystemBase {
 private PhotonCamera camera;

 /** Creates a new Photonvision. */
 public PhotonSubsystem(NetworkTableInstance nt) {
    camera = new PhotonCamera(nt, "Logi_C310_HD_WebCam");
    camera.setPipelineIndex(0);
 }

 // AprilTags
 public double[] getTagData(int id) {
    var result = camera.getLatestResult();
    boolean hasTargets = result.hasTargets();
    if (hasTargets) {
      PhotonTrackedTarget target = findCorrectTarget(id, result.getTargets());
      if (target.getFiducialId() == id) {
        double[] pack = {target.getBestCameraToTarget().getX(), 
                         target.getBestCameraToTarget().getY(),
                         target.getBestCameraToTarget().getRotation().getAngle()};
        return pack;
      } else {
        double[] mt = {0, 0, 0};
        return mt;
      }
    } else {
      double[] mt = {0, 0, 0};
      return mt;
    }
 }

 public double[] getStageTagData(int[] ids) {
    var result = camera.getLatestResult();
    boolean hasTargets = result.hasTargets();
    if (hasTargets) {
      PhotonTrackedTarget target = findCorrectTarget(ids, result.getTargets());
      if (!(target.getFiducialId() == -1)) {
        double[] pack = {target.getBestCameraToTarget().getX(),
                         target.getBestCameraToTarget().getY(),
                         target.getBestCameraToTarget().getRotation().getAngle()};
        return pack;
      } else {
        double[] mt = {0, 0, 0};
        return mt;
      }
    } else {
      double[] mt = {0, 0, 0};
      return mt;
    }
 }

 public boolean isTarget(int targetID) {
    var result = camera.getLatestResult();
    boolean hasTargets = result.hasTargets();
    if (hasTargets) {
      PhotonTrackedTarget target = result.getBestTarget();
      if (targetID == 0) {
        return true;
      } else {
        if (targetID == target.getFiducialId()) {
          return true;
        } else {
          return false;
        }
      }
    } else {
      return false;
    }
 }

 private PhotonTrackedTarget findCorrectTarget(int id, List<PhotonTrackedTarget> lstTarget) {
    for(int x = 0; x<lstTarget.size(); x++) {
      if(lstTarget.get(x).getFiducialId() == id) {
        return lstTarget.get(x);
      }
    }
    return new PhotonTrackedTarget(0, 0, 0, 0, -1, null, null, 0.0, null, null);
 }

 private PhotonTrackedTarget findCorrectTarget(int[] ids, List<PhotonTrackedTarget> lstTarget) {
    for(int x = 0; x<lstTarget.size(); x++) {
      if(lstTarget.get(x).getFiducialId() == ids[0] || lstTarget.get(x).getFiducialId() == ids[1] || lstTarget.get(x).getFiducialId() == ids[2]) {
        return lstTarget.get(x);
      }
    }
    return new PhotonTrackedTarget(0, 0, 0, 0, -1, null, null, 0.0, null, null);
 }
}
