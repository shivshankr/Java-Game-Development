package dev.ssgames.SSTrilogy;


public class UserGamedata implements java.io.Serializable {
    String   username;
    int    points1,moves;
    double time1;
    int    points2,eaten;
    double time2;
    int vpcent,mpcent;

    public UserGamedata(String username,int points1,int moves, double time1, int points2,int eaten, double time2, int vpcent,int mpcent){

        this.username=username;
        this.points1=points1;
        this.moves=moves;
        this.time1=time1;
        this.points2=points2;
        this.eaten=eaten;
        this.time2=time2;
        this.vpcent=vpcent;
        this.mpcent=mpcent;

    }
}
