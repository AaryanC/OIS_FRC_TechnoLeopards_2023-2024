package frc.robot;

public class Functions{
    public static double degreeChangeClosest(double targetDegree, double currentAngle){
        //Positive is clockwise negative is anti-clockwise
        double degreeDifference = targetDegree - currentAngle;

        if (degreeDifference == 0){
            return 0;
        }

        if (degreeDifference <= 180 && degreeDifference >= -180){
            return degreeDifference;
        } else {
            if (degreeDifference > 180){
                return (degreeDifference - 180) * -1;
            } else{
                return (degreeDifference + 180) * -1;
            }
        }
    }
    //Same as above but with integers
    public static int degreeChangeClosest(int targetDegree, int currentAngle){
        //Possitive is clockwise negative is anti-clockwise
        int degreeDifference = targetDegree - currentAngle;

        if (degreeDifference == 0){
            return 0;
        }

        if (degreeDifference <= 180 && degreeDifference >= -180){
            return degreeDifference;
        } else {
            if (degreeDifference > 180){
                return (degreeDifference - 180) * -1;
            } else{
                return (degreeDifference + 180) * -1;
            }
        }
    }
}